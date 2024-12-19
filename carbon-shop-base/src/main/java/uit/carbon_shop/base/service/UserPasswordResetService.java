package uit.carbon_shop.base.service;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uit.carbon_shop.base.domain.User;
import uit.carbon_shop.base.model.UserPasswordResetCompleteRequest;
import uit.carbon_shop.base.model.UserPasswordResetRequest;
import uit.carbon_shop.base.repos.UserRepository;
import uit.carbon_shop.base.util.WebUtils;


@Service
@Slf4j
public class UserPasswordResetService {

    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserPasswordResetService(final MailService mailService,
            final PasswordEncoder passwordEncoder, final UserRepository userRepository) {
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    private boolean hasValidRequest(final User user) {
        return user != null && user.getResetPasswordUid() != null && 
                user.getResetPasswordStart().plusWeeks(1).isAfter(OffsetDateTime.now());
    }

    public void startProcess(final UserPasswordResetRequest passwordResetRequest) {
        log.info("received password reset request for {}", passwordResetRequest.getEmail());

        final User user = userRepository.findByPasswordIgnoreCase(passwordResetRequest.getEmail());
        if (user == null) {
            log.warn("user {} not found", passwordResetRequest.getEmail());
            return;
        }

        // keep existing uid if still valid
        if (!hasValidRequest(user)) {
            user.setResetPasswordUid(UUID.randomUUID().toString());
        }
        user.setResetPasswordStart(OffsetDateTime.now());
        userRepository.save(user);

        mailService.sendMail(passwordResetRequest.getEmail(), WebUtils.getMessage("passwordReset.mail.subject"),
                WebUtils.renderTemplate("/mails/userPasswordReset", Collections.singletonMap("passwordResetUid", user.getResetPasswordUid())));
    }

    public boolean isValidPasswordResetUid(final String passwordResetUid) {
        final User user = userRepository.findByResetPasswordUid(passwordResetUid);
        if (hasValidRequest(user)) {
            return true;
        }
        log.warn("invalid password reset uid {}", passwordResetUid);
        return false;
    }

    public void completeProcess(
            final UserPasswordResetCompleteRequest userPasswordResetCompleteRequest) {
        final User user = userRepository.findByResetPasswordUid(userPasswordResetCompleteRequest.getUid());
        Assert.isTrue(hasValidRequest(user), "invalid update password request");

        log.warn("updating password for user {}", user.getPassword());

        user.setPasswordSalt(passwordEncoder.encode(userPasswordResetCompleteRequest.getNewPassword()));
        user.setResetPasswordUid(null);
        user.setResetPasswordStart(null);
        userRepository.save(user);
    }

}
