package com.nimbl3.survey.views.presenter;

import com.nimbl3.survey.apis.APIConnector;
import com.nimbl3.survey.apis.APIExecuteListener;
import com.nimbl3.survey.apis.SurveyAPIExecutor;
import com.nimbl3.survey.models.Survey;
import com.nimbl3.survey.models.SurveyParam;
import com.nimbl3.survey.utilities.Constant;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class SurveyPresenter implements APIExecuteListener<List<Survey>> {

    // The SurveyViewConnector
    private SurveyViewConnector view;

    // The SurveyAPIExecutor
    private SurveyAPIExecutor surveyAPI;

    // The list of surveys
    private List<Survey> surveys;

    // The index of surveys
    private int index;

    /**
     * TODO Constructor
     * @param view
     */
    public SurveyPresenter(SurveyViewConnector view) {
        surveys = new ArrayList<>();
        this.view = view;
    }

    /**
     * TODO Public Methods
     * Fetches the survey from server.
     */
    public void fetchSurvey(int page, int perPage) {
        SurveyParam param = new SurveyParam(Constant.ACCESS_TOKEN, page, perPage);
        if (surveyAPI == null) {
            surveyAPI = new SurveyAPIExecutor(this, param);
        } else {
            surveyAPI.setParam(param);
        }
        surveyAPI.execute();
    }

    /**
     * Displays a next survey.
     */
    public void displayNextSurvey() {
        // Keeps the previous index.
        int fromIndex = index;

        // Moves next as recursive!
        index = index + 1 < surveys.size() ? ++index : 0;

        if (view != null) {
            // Displays the survey.
            view.displaySurvey(surveys.get(index));

            // Marks the current bullet.
            view.markSurveyBullet(fromIndex, index);
        }
    }

    /**
     * TODO APIExecuteListener
     * @param connector
     * @param obj
     */
    @Override
    public void onSuccess(APIConnector connector, List<Survey> obj) {
        //Cleans all.
        surveys.clear();

        // Adds all new.
        surveys.addAll(obj);

        // Callback to view.
        if (view != null) {
            // Resets the starting index.
            index = 0;

            // Creates the bullet view.
            view.createBulletView(surveys.size());

            // Marks the current bullet.
            view.markSurveyBullet(index, index);

            // Display the first.
            view.displaySurvey(surveys.get(index));
        }
    }

    @Override
    public void onFailure(APIConnector connector, Throwable t) {
        // Timeout? Gets only the first ten items.
        if (t instanceof SocketTimeoutException) {
            int page = 1;
            int perPage = 10;
            fetchSurvey(page, perPage);
        }

        // Shows error message.
        if (view != null) view.showMessage(t.getMessage());
    }
}
