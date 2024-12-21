package uit.carbon_shop.service;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.model.PasswordResetCompleteRequest;
import uit.carbon_shop.model.PasswordResetRequest;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.util.WebUtils;


@Service
@Slf4j
public class PasswordResetService {

    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    public PasswordResetService(final MailService mailService,
            final PasswordEncoder passwordEncoder, final AppUserRepository appUserRepository) {
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
    }

    private boolean hasValidRequest(final AppUser appUser) {
        return appUser != null && appUser.getResetPasswordUid() != null && 
                appUser.getResetPasswordStart().plusWeeks(1).isAfter(OffsetDateTime.now());
    }

    public void startProcess(final PasswordResetRequest passwordResetRequest) {
        log.info("received password reset request for {}", passwordResetRequest.getEmail());

        final AppUser appUser = appUserRepository.findByEmailIgnoreCase(passwordResetRequest.getEmail());
        if (appUser == null) {
            log.warn("user {} not found", passwordResetRequest.getEmail());
            return;
        }

        // keep existing uid if still valid
        if (!hasValidRequest(appUser)) {
            appUser.setResetPasswordUid(UUID.randomUUID().toString());
        }
        appUser.setResetPasswordStart(OffsetDateTime.now());
        appUserRepository.save(appUser);

        mailService.sendMail(passwordResetRequest.getEmail(), WebUtils.getMessage("passwordReset.mail.subject"),
                WebUtils.renderTemplate("/mails/passwordReset", Collections.singletonMap("passwordResetUid", appUser.getResetPasswordUid())));
    }

    public boolean isValidPasswordResetUid(final String passwordResetUid) {
        final AppUser appUser = appUserRepository.findByResetPasswordUid(passwordResetUid);
        if (hasValidRequest(appUser)) {
            return true;
        }
        log.warn("invalid password reset uid {}", passwordResetUid);
        return false;
    }

    public void completeProcess(final PasswordResetCompleteRequest passwordResetCompleteRequest) {
        final AppUser appUser = appUserRepository.findByResetPasswordUid(passwordResetCompleteRequest.getUid());
        Assert.isTrue(hasValidRequest(appUser), "invalid update password request");

        log.warn("updating password for user {}", appUser.getEmail());

        appUser.setPassword(passwordEncoder.encode(passwordResetCompleteRequest.getNewPassword()));
        appUser.setResetPasswordUid(null);
        appUser.setResetPasswordStart(null);
        appUserRepository.save(appUser);
    }

}
