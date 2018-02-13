package uk.org.ssvc.email.integration.encryption.service;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Singleton
public class EncryptionService {

    private final TextEncryptor encryptor;

    @Inject
    public EncryptionService(@Named("encryption.key") String key,
                             @Named("encryption.password") String password) {
        encryptor = Encryptors.text(password, key);
    }

    public String encrypt(String val) {
        if (isBlank(val)) {
            return val;
        }

        return encryptor.encrypt(val);
    }

    public String decrypt(String val) {
        if (isBlank(val)) {
            return val;
        }

        return encryptor.decrypt(val);
    }

}
