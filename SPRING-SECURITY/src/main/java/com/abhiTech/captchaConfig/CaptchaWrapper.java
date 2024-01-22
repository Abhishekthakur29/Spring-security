package com.abhiTech.captchaConfig;

import cn.apiclub.captcha.Captcha;

import java.time.LocalDateTime;

public class CaptchaWrapper {

    private Captcha captcha;
    private boolean verified;
    private LocalDateTime creationTime;

    public CaptchaWrapper(Captcha captcha, LocalDateTime creationTime) {
        this.captcha = captcha;
        this.verified = false;
        this.creationTime = creationTime;
    }

    public Captcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
