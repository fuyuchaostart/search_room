package com.fycstart.bass;

/**
 * @author fyc
 * @description: api响应格式
 * @date 2019/4/30上午 9:49
 */
public class ApiResponse {

    private Long code;
    private String message;
    private Object data;
    private boolean more;


    public static ApiResponse ofMessage(Long code, String message) {
        return new ApiResponse(code, message, null);

    }

    public static ApiResponse ofSuccess(Object data) {
        return new ApiResponse(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getMessage(), data);
    }


    public static ApiResponse ofError() {
        return new ApiResponse(StatusEnum.INTERNAL_SERVER_ERROR.getStatus(), StatusEnum.INTERNAL_SERVER_ERROR.getMessage(), null);
    }

    public static ApiResponse ofStatus(StatusEnum status) {
        return new ApiResponse(status.getStatus(), status.getMessage(), null);
    }

    public ApiResponse() {
    }

    public ApiResponse(Long code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;

    }


    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }
}
