package com.nimbl3.survey.apis;

/**
 * Created by rathakit.nop on 9/22/2016 AD.
 */

public interface APIExecuteListener<T> {
    // The callback method when success.
    public void onSuccess(APIConnector connector, T obj);

    // The callback method when failed.
    public void onFailure(APIConnector connector, Throwable t);
}
