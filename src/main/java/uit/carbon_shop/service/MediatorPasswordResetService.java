package uit.carbon_shop.service;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uit.carbon_shop.domain.Mediator;
import uit.carbon_shop.model.MediatorPasswordResetCompleteRequest;
import uit.carbon_shop.model.MediatorPasswordResetRequest;
import uit.carbon_shop.repos.MediatorRepository;
import uit.carbon_shop.util.WebUtils;


@Service
@Slf4j
public class MediatorPasswordResetService {

    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final MediatorRepository mediatorRepository;

    public MediatorPasswordResetService(final MailService mailService,
            final PasswordEncoder passwordEncoder, final MediatorRepository mediatorRepository) {
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.mediatorRepository = mediatorRepository;
    }

    private boolean hasValidRequest(final Mediator mediator) {
        return mediator != null && mediator.getResetPasswordUid() != null && 
                mediator.getResetPasswordStart().plusWeeks(1).isAfter(OffsetDateTime.now());
    }

    public void startProcess(final MediatorPasswordResetRequest passwordResetRequest) {
        log.info("received password reset request for {}", passwordResetRequest.getEmail());

        final Mediator mediator = mediatorRepository.findByEmailIgnoreCase(passwordResetRequest.getEmail());
        if (mediator == null) {
            log.warn("user {} not found", passwordResetRequest.getEmail());
            return;
        }

        // keep existing uid if still valid
        if (!hasValidRequest(mediator)) {
            mediator.setResetPasswordUid(UUID.randomUUID().toString());
        }
        mediator.setResetPasswordStart(OffsetDateTime.now());
        mediatorRepository.save(mediator);

        mailService.sendMail(passwordResetRequest.getEmail(), WebUtils.getMessage("passwordReset.mail.subject"),
                WebUtils.renderTemplate("/mails/mediatorPasswordReset", Collections.singletonMap("passwordResetUid", mediator.getResetPasswordUid())));
    }

    public boolean isValidPasswordResetUid(final String passwordResetUid) {
        final Mediator mediator = mediatorRepository.findByResetPasswordUid(passwordResetUid);
        if (hasValidRequest(mediator)) {
            return true;
        }
        log.warn("invalid password reset uid {}", passwordResetUid);
        return false;
    }

    public void completeProcess(
            final MediatorPasswordResetCompleteRequest mediatorPasswordResetCompleteRequest) {
        final Mediator mediator = mediatorRepository.findByResetPasswordUid(mediatorPasswordResetCompleteRequest.getUid());
        Assert.isTrue(hasValidRequest(mediator), "invalid update password request");

        log.warn("updating password for user {}", mediator.getEmail());

        mediator.setPassword(passwordEncoder.encode(mediatorPasswordResetCompleteRequest.getNewPassword()));
        mediator.setResetPasswordUid(null);
        mediator.setResetPasswordStart(null);
        mediatorRepository.save(mediator);
    }

}
