package com.lzx.uniserver.tool;

public class Result {
    private int code;
    private String message;
    private Object data;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    // getter、setter方法
    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    public Result success(Object data) {
        this.setCode(ResultCode.SUCCESS);
        this.setData(data);
        return this;
    }

    public Result fail(String message) {
        this.setCode(ResultCode.FAIL);
        this.message = message;
        return this;
    }
}
