package com.mavolas.dianpin.common;

/**
 * Author by Andy
 * Date on 2019-04-17.
 */
public enum ResponseCode {
    normal,
    authenFailed,
    invalidInput,
    systemerror,
    undefined;

    private ResponseCode() {
    }
}
