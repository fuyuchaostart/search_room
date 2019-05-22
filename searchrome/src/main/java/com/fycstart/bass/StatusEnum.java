package com.fycstart.bass;

/**
 * @author fyc
 * @description: 基本的响应值
 * @date 2019/4/30上午 9:52
 */
public enum StatusEnum {
    SUCCESS(200L, "成功"),
    BAD_REQUEST(400L, "请求错误"),
    NOT_FOUND(404L, "找不到页面"),
    INTERNAL_SERVER_ERROR(500L, "Unknown Internal Error"),
    NOT_VALID_PARAM(40005L, "Not valid Params"),
    NOT_SUPPORTED_OPERATION(40006L, "Operation not supported"),
    NOT_LOGIN(50000L, "Not Login");

    private Long status;
    private String message;

    StatusEnum(Long status, String message) {
        this.status = status;
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
