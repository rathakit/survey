package com.nimbl3.survey.apis;

import com.nimbl3.survey.models.Survey;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rathakit.nop on 9/22/2016 AD.
 */

public interface SurveyServiceAPI {

    // Gets the list of surveys
    @GET("app/surveys.json")
    Call<List<Survey>> getSurveyListAPI(@Query("access_token") String accessToken);
}
