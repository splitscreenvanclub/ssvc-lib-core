package uk.org.ssvc.email.domain.service;

import lombok.Getter;
import uk.org.ssvc.email.domain.environment.EnvironmentRunType;

import javax.inject.Inject;
import javax.inject.Singleton;

import static uk.org.ssvc.email.domain.environment.EnvironmentRunType.DRY_RUN;

@Singleton
@Getter
public class SsvcRegistry {

    private static SsvcRegistry instance;

    private final EmailService emailService;
    private final SmsService smsService;
    private final EnvironmentRunType runType;

    @Inject
    public SsvcRegistry(EmailService emailService,
                        SmsService smsService,
                        EnvironmentRunType runType) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.runType = runType;
    }

    public void initialise() {
        instance = this;
    }

    public static EmailService emailService() {
        return registry().getEmailService();
    }

    public static SmsService smsService() {
        return registry().getSmsService();
    }

    public static boolean isDryRun() {
        return registry().getRunType() == DRY_RUN;
    }

    public static SsvcRegistry registry() {
        if (instance == null) {
            throw new IllegalStateException("SSVC registry not initialised");
        }

        return instance;
    }

}
