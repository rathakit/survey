package com.nimbl3.survey.apis;

import com.google.gson.Gson;
import com.nimbl3.survey.utilities.AppSettings;
import com.nimbl3.survey.utilities.Util;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rathakit.nop on 9/22/2016 AD.
 * APIConnector - the abstract class that allows subclasses connect directly to the server APIs.
 */
public abstract class APIConnector {

    // Retrofit
    protected Retrofit retrofit;

    // The server APIs
    protected SurveyServiceAPI serviceAPI;

    // The call object
    protected Call call;

    // TODO Abstract Methods
    public abstract void execute(APIExecutor executor);
    protected abstract Gson getGsonConverter();

    // TODO Constructor
    public APIConnector() {
        // Initializes the Retrofit object.
        retrofit = new Retrofit.Builder()
                .baseUrl(AppSettings.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGsonConverter()))
                .build();

        // Creates the ready-to-use APIs from interface.
        serviceAPI = retrofit.create(SurveyServiceAPI.class);
    }

    /**
     * TODO Public Methods
     * Validates the API.
      */
    public Throwable validate() {
        Throwable t = null;
        if (!Util.isNetworkConnected()) {
            t = new ConnectException();
        }
        return t;
    }

    /**
     * Cancel the ongoing connection.
     */
    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }
}