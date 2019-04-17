package com.mavolas.dianpin.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by Andy
 * Date on 2019-04-17.
 */
public class ResponseCls<T> {

    private boolean status;
    private Integer code;
    private List<String> messages = new ArrayList();
    private T data;

    public ResponseCls() {
    }

    public ResponseCls(Boolean hasWebServerError) {
        if (hasWebServerError) {
            this.status = true;
        } else {
            this.status = false;
        }

    }

    public Boolean HasWebServerError() {
        return !this.status;
    }

    public ResponseCls(boolean requestStatus, Integer code, List<String> messages, T data) {
        this.status = requestStatus;
    }

    public Boolean getRequestStatus() {
        return this.status;
    }

    public ResponseCode getCode() {
        if (this.code == 0) {
            return ResponseCode.normal;
        } else if (this.code == 1) {
            return ResponseCode.authenFailed;
        } else if (this.code == 2) {
            return ResponseCode.invalidInput;
        } else {
            return this.code == 9 ? ResponseCode.systemerror : ResponseCode.undefined;
        }
    }

    public int getCodeValue() {
        return this.code;
    }

    public List<String> getErrorMessage() {
        return this.messages;
    }

    public T getData() {
        return this.data;
    }
}
