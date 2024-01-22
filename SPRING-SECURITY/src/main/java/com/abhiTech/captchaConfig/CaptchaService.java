package com.abhiTech.captchaConfig;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;

@Service
public class CaptchaService {

    private CaptchaWrapper captchaWrapper;

    public CaptchaService() {
        // Initialize the captcha wrapper with a dummy captcha and current timestamp
        this.captchaWrapper = new CaptchaWrapper(new Captcha.Builder(1, 1).build(), LocalDateTime.now());
    }

    public Captcha generateCaptcha() {
        this.captchaWrapper = new CaptchaWrapper(buildCaptcha(), LocalDateTime.now());
        return captchaWrapper.getCaptcha();
    }

    private Captcha buildCaptcha() {
        // Implement your captcha building logic here
        // You can use the same logic as before
        return new Captcha.Builder(300, 60)
                .addText(new DefaultTextProducer())
                .addNoise()
                .addBorder()
                .addBackground(new GradiatedBackgroundProducer())
                .gimp(new FishEyeGimpyRenderer())
                .build();
    }

    public boolean verifyCaptcha(String userInput) {
        if (isCaptchaExpired()) {
            // If the captcha has expired, consider it as not verified
            return false;
        }

        // Implement your captcha verification logic here
        boolean isVerified = captchaWrapper.getCaptcha().isCorrect(userInput);
        
        if (isVerified) {
            captchaWrapper.setVerified(true);
        }

        return isVerified;
    }

    private boolean isCaptchaExpired() {
        // Check if the captcha has expired (e.g., after 2 minutes)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = captchaWrapper.getCreationTime().plus(1, ChronoUnit.MINUTES);

        return now.isAfter(expirationTime);
    }
}

