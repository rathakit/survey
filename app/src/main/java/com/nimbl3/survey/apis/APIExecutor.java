package com.nimbl3.survey.apis;

import com.nimbl3.survey.App;
import com.nimbl3.survey.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rathakit.nop on 9/22/2016 AD.
 *
 * APIExecutor - the class to perform the API request (APIConnector).
 */
public class APIExecutor<T> implements Callback<T> {

    // The target APIConnector
    private APIConnector connector;

    // The listener
    private APIExecuteListener<T> listener;

    /**
     * TODO Overload Constructor
     * Creates the APIExecutor with APIConnector and APIExecuteListener.
     * @param connector
     * @param listener
     */
    public APIExecutor(APIConnector connector, APIExecuteListener<T> listener) {
        this.connector = connector;
        this.listener = listener;
    }

    /**
     * Default constructor.
     */
    public APIExecutor() {
    }

    /**
     * TODO Public Methods
     * Starts execution on the target APIConnector.
     */
    public void execute(APIExecuteListener<T> listener) {
        this.listener = listener;
        if (connector != null) {
            // Validates the request first.
            Throwable t = connector.validate();
            if (t == null) {
                // Make the execution if validation has been passed!
                connector.execute(this);
            } else if (listener != null) {
                listener.onFailure(connector, t);
            }
        } else {
            // The connector has not been set.
            listener.onFailure(connector, new IllegalArgumentException(App.getContext().getString(R.string.api_connector_not_set)));
        }
    }

    /**
     * Sets the APIConnector.
     * @param connector
     */
    public void setAPIConnector(APIConnector connector) {
        this.connector = connector;
    }

    /**
     * Gets the APIConnector.
     * @return the connector
     */
    public APIConnector getAPIConnector() {
        return connector;
    }

    /**
     * Sets the APIExecuteListener.
     * @param listener
     */
    public void setAPIExecuteListener(APIExecuteListener<T> listener) {
        this.listener = listener;
    }

    /**
     * Cancels the ongoing request.
     */
    public void abort() {
        if (connector != null) {
            listener = null;
            connector.cancel();
        }
    }

    /**
     * TODO Callback
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (listener != null) {
            T obj = response.body();
            listener.onSuccess(connector, obj);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (listener != null) {
            listener.onFailure(connector, t);
        }
    }
}
