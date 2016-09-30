package com.nimbl3.survey.views.presenter;

import com.nimbl3.survey.models.Survey;

public interface SurveyViewConnector extends ViewConnector {

    /**
     * Displays the survey.
     * */
    void displaySurvey(Survey survey);

    /**
     * Marks the active bullet.
     */
    void markSurveyBullet(int fromIndex, int index);

    /**
     * Creates the bullet view.
     * @param count
     */
    void createBulletView(int count);
}
