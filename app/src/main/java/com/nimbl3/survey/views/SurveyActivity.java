package com.nimbl3.survey.views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nimbl3.survey.R;
import com.nimbl3.survey.apis.APIConnector;
import com.nimbl3.survey.apis.APIExecuteListener;
import com.nimbl3.survey.apis.SurveyAPIExecutor;
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

    // The bullets layout
    private LinearLayout bulletsLayout;

    // The list of surveys
    private List<Survey> surveys;

    // The bullets
    private List<View> bullets;

    // The SurveyAPIExecutor
    private SurveyAPIExecutor surveyAPI;

    // The index of surveys
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        // Initializes the view's components.
        nameTextView = (TextView) findViewById(R.id.name_text_view);
        descTextView = (TextView) findViewById(R.id.desc_text_view);
        surveyButton = (TextView) findViewById(R.id.survey_button);
        bulletsLayout = (LinearLayout) findViewById(R.id.bullets_layout);
        backgroundImageView = (ImageView) findViewById(R.id.bg_image_view);

        // Initializes related objects.
        surveys = new ArrayList<>();
        bullets = new ArrayList<>();

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

        // Creates the bullet view.
        createBulletView(surveys.size());

        // Marks the current bullet.
        markSurveyBullet(index, index);

        // Display the first.
        displaySurvey(surveys.get(index));
    }

    @Override
    public void onFailure(APIConnector connector, Throwable t) {
        // Hides the survey button.
        surveyButton.setVisibility(View.GONE);

        // Shows error message.
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * TODO UI's Methods
     * Called when the survey button clicked.
     */
    public void onSurvey(View v) {
        int fromIndex = index;

        // Moves next as recursive!
        index = index + 1 < surveys.size() ? ++index : 0;

        // Marks the current bullet.
        markSurveyBullet(fromIndex, index);

        // Displays the first.
        displaySurvey(surveys.get(index));
    }

    /**
     * Called when the refresh button clicked.
     */
    public void onRefresh(View v) {
        fetchSurvey();
    }

    /**
     * TODO Private Methods
     * Fetches the survey from server.
     */
    private void fetchSurvey() {
        if (surveyAPI == null)
            surveyAPI = new SurveyAPIExecutor(this, Constant.ACCESS_TOKEN);
        surveyAPI.execute();
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

    /**
     * Creates the bullets.
     * @param count - the number of bullets
     */
    private void createBulletView(int count) {
        // Clears all bullets
        bullets.clear();
        bulletsLayout.removeAllViews();

        // Adds the bullets to the layout and puts them to array.
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < count; i++) {
            // Gets the main bullet view.
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.bullet_view, null);

            // Retrieves only the bullet view.
            View bulletView = v.findViewById(R.id.bullet_view);

            // Makes the child's view orphan.
            v.removeAllViews();

            // Adds to layout.
            bulletsLayout.addView(bulletView);

            // Puts to array.
            bullets.add(bulletView);
        }
    }

    /**
     * Marks the current survey.
     * @param fromIndex
     * @param toIndex
     */
    private void markSurveyBullet(int fromIndex, int toIndex) {
        View fromView = bullets.get(fromIndex);
        View toView = bullets.get(toIndex);
        fromView.setBackgroundResource(R.drawable.unselected_white_bullet);
        toView.setBackgroundResource(R.drawable.selected_white_bullet);
    }
}
