package com.nimbl3.survey.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nimbl3.survey.App;
import com.nimbl3.survey.R;
import com.nimbl3.survey.models.Survey;
import com.nimbl3.survey.utilities.Constant;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by rathakit.nop on 9/22/2016 AD.
 * SurveyAPIExecutor - The class connects directly to the "app/surveys.json" API.
 */
public class SurveyAPIExecutor extends APIConnector<List<Survey>> {

    // The access token
    private String accessToken = Constant.EMPTY_STRING;

    /** TODO Constructor
     * Creates a survey API with access token provided.
     * @param accessToken
     */
    public SurveyAPIExecutor(APIExecuteListener listener, String accessToken) {
        super(listener);
        this.accessToken = accessToken;
    }

    /**
     * TODO Override Methods
     * Executes the API to communicate with server.
     */
    @Override
    public void execute() {
        // Ignored if being executed.
        if (call != null && call.isExecuted()) {
            return;
        }

        // Validates the request first.
        Throwable t = validate();
        if (t == null) {
            // Make the execution if validation has been passed!
            call = serviceAPI.getSurveyListAPI(accessToken);
            call.enqueue(this);
        } else if (listener != null) {
            listener.onFailure(this, t);
        }
    }

    @Override
    protected Gson getGsonConverter() {
        // Gson
        Type surveyListType = new TypeToken<List<Survey>>(){}.getType();
        return new GsonBuilder()
                .registerTypeAdapter(surveyListType, new SurveyJsonParser<List<Survey>>())
                .create();
    }

    /**
     * Validates the API.
     */
    @Override
    public Throwable validate() {
        Throwable t = super.validate();
        if (t == null) {
            if (accessToken == null || accessToken.trim().length() == 0) {
                t = new IllegalArgumentException(App.getContext().getString(R.string.api_access_token_not_set));
            }
        }
        return t;
    }

    /**
     * TODO Callback
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
        // Sets the call as null.
        this.call = null;
        if (listener != null) {
            List<Survey> obj = response.body();
            listener.onSuccess(this, obj);
        }
    }

    @Override
    public void onFailure(Call<List<Survey>> call, Throwable t) {
        // Sets the call as null.
        this.call = null;
        if (listener != null) {
            listener.onFailure(this, t);
        }
    }
}
