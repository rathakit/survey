package com.nimbl3.survey.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nimbl3.survey.R;
import com.nimbl3.survey.apis.APIConnector;
import com.nimbl3.survey.apis.APIExecuteListener;
import com.nimbl3.survey.apis.APIExecutor;
import com.nimbl3.survey.apis.SurveyAPI;
import com.nimbl3.survey.models.Survey;
import com.nimbl3.survey.utilities.Constant;

import java.util.List;

public class SurveyActivity extends AppCompatActivity implements APIExecuteListener<List<Survey>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        fetchSurvey();
    }

    public void fetchSurvey() {
        SurveyAPI surveyAPI = new SurveyAPI(Constant.ACCESS_TOKEN);
        APIExecutor executor = new APIExecutor();
        executor.setAPIConnector(surveyAPI);
        executor.execute(this);
    }

    /**
     * TODO APIExecuteListener
     * @param connector
     * @param obj
     */
    @Override
    public void onSuccess(APIConnector connector, List<Survey> obj) {
        for (Survey s : obj) {
            Log.d("survey", "" + s);
        }
    }

    @Override
    public void onFailure(APIConnector connector, Throwable t) {
        Log.d("survey", "Failed: " + connector + ", Exception: " + t);
    }
}
