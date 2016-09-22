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

/**
 * Created by rathakit.nop on 9/22/2016 AD.
 * SurveyAPI - The class connects directly to the "app/surveys.json" API.
 */
public class SurveyAPI extends APIConnector {

    // The access token
    private String accessToken = Constant.EMPTY_STRING;

    /** TODO Constructor
     * Creates a survey API with access token provided.
     * @param accessToken
     */
    public SurveyAPI(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * TODO Override Methods
     * Executes the API to communicate with server.
     */
    @Override
    public void execute(APIExecutor executor) {
        call = serviceAPI.getSurveyListAPI(accessToken);
        call.enqueue(executor);
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
}
