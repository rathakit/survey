package com.nimbl3.survey.models;

/**
 * Created by rathakit.nop on 9/23/2016 AD.
 */

import com.nimbl3.survey.utilities.Constant;

/**
 * The BasedParam Class
 */
public abstract class BasedParam {

    // The access token
    private String accessToken = Constant.EMPTY_STRING;

    // TODO Constructors
    public BasedParam() {}

    public BasedParam(String accessToken) {
        this.accessToken = accessToken;
    }

    // TODO GETTER/SETTER
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
