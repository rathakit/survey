package com.nimbl3.survey.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nimbl3.survey.R;
import com.nimbl3.survey.apis.APIConnector;
import com.nimbl3.survey.apis.APIExecuteListener;
import com.nimbl3.survey.apis.APIExecutor;
import com.nimbl3.survey.apis.SurveyAPI;
import com.nimbl3.survey.models.Survey;
import com.nimbl3.survey.utilities.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends AppCompatActivity implements APIExecuteListener<List<Survey>> {

    // The name text view
    private TextView nameTextView;

    // The description text view
    private TextView descTextView;

    // The survey button
    private TextView surveyButton;

    // The background image view
    private ImageView backgroundImageView;

    // The list of surveys
    private List<Survey> surveys;

    // The index of surveys
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // Initializes the view's components.
        nameTextView = (TextView) findViewById(R.id.name_text_view);
        descTextView = (TextView) findViewById(R.id.desc_text_view);
        surveyButton = (TextView) findViewById(R.id.survey_button);
        backgroundImageView = (ImageView) findViewById(R.id.bg_image_view);

        // Initializes related objects.
        surveys = new ArrayList<>();

        // Start fetching the survey!
        fetchSurvey();
    }

    /**
     * TODO APIExecuteListener
     * @param connector
     * @param obj
     */
    @Override
    public void onSuccess(APIConnector connector, List<Survey> obj) {
        // Clears the previous.
        surveys.clear();

        // Resets the index.
        index = 0;

        // Displays the survey button.
        surveyButton.setVisibility(View.VISIBLE);

        // Adds all new.
        surveys.addAll(obj);

        // Display the first.
        displaySurvey(surveys.get(index));
    }

    @Override
    public void onFailure(APIConnector connector, Throwable t) {
        // Hides the survey button.
        surveyButton.setVisibility(View.GONE);
    }

    /**
     * TODO UI's Methods
     * Called when the survey button clicked.
     */
    public void onSurvey(View v) {
        // Moves next!
        index = index + 1 < surveys.size() ? ++index : index;

        // Displays the first.
        displaySurvey(surveys.get(index));
    }

    /**
     * TODO Private Methods
     * Fetches the survey from server.
     */
    private void fetchSurvey() {
        SurveyAPI surveyAPI = new SurveyAPI(Constant.ACCESS_TOKEN);
        APIExecutor executor = new APIExecutor();
        executor.setAPIConnector(surveyAPI);
        executor.execute(this);
    }

    /**
     * Displays the survey details.
     * @param survey
     */
    private void displaySurvey(Survey survey) {
        // Name
        nameTextView.setText(survey.getName());

        // Description
        descTextView.setText(survey.getDescription());

        // Background
        Picasso.with(this)
                .load(survey.getCoverImageUrl())
                .into(backgroundImageView);
    }
}
