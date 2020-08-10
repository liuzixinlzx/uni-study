package com.lzx.uniserver.interceptor;

public class AuthorizationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
