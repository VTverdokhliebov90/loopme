package test.interview.loopme.campaign.controller.dto;

import java.util.Arrays;

public enum AdPlatform {
    UNDEFINED(-1), WEB(0), ANDROID(1), IOS(2);

    private final int code;

    AdPlatform(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AdPlatform forCode(int code) {
        return Arrays.stream(values()).filter(platform -> platform.code == code).findAny().orElse(UNDEFINED);
    }
}
