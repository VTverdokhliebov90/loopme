package test.interview.loopme.campaign.controller.dto;

import java.util.Arrays;

public enum Status {
    UNDEFINED(-1), PLANNED(0), ACTIVE(1), PAUSED(2), FINISHED(3);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Status forCode(int code) {
        return Arrays.stream(values()).filter(status -> status.code == code).findAny().orElse(UNDEFINED);
    }
}
