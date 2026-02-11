package com.example.family_ording.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CaptchaUtil {

    private static final String CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 4;
    private final ConcurrentHashMap<String, String> captchaCache = new ConcurrentHashMap<>();
    private static final long CAPTCHA_EXPIRE_TIME = 5 * 60 * 1000;

    public String generateCaptcha(String key) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        String captcha = code.toString();
        captchaCache.put(key, captcha);
        return captcha;
    }

    public boolean verifyCaptcha(String key, String inputCaptcha) {
        String storedCaptcha = captchaCache.get(key);
        if (storedCaptcha == null) {
            return false;
        }
        return storedCaptcha.equalsIgnoreCase(inputCaptcha);
    }

    public void removeCaptcha(String key) {
        captchaCache.remove(key);
    }

}
