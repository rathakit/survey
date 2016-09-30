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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nimbl3.survey.R;
import com.nimbl3.survey.apis.APIConnector;
import com.nimbl3.survey.apis.APIExecuteListener;
import com.nimbl3.survey.apis.SurveyAPIExecutor;
import com.nimbl3.survey.models.Survey;
import com.nimbl3.survey.models.SurveyParam;
import com.nimbl3.survey.utilities.Constant;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurveyActivity extends AppCompatActivity implements APIExecuteListener<List<Survey>> {

    // The name text view
    @BindView(R.id.name_text_view) TextView nameTextView;

    // The description text view
    @BindView(R.id.desc_text_view) TextView descTextView;

    // The survey button
    @BindView(R.id.survey_button) TextView surveyButton;

    // The background image view
    @BindView(R.id.bg_image_view) ImageView backgroundImageView;

    // The bullet scroll view.
    @BindView(R.id.bullet_scroll_view) ScrollView bulletScrollView;

    // The bullets layout
    @BindView(R.id.bullets_layout) LinearLayout bulletsLayout;

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

        // ButterKnife Binding
        ButterKnife.bind(this);

        // ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        // Initializes related objects.
        surveys = new ArrayList<>();
        bullets = new ArrayList<>();

        // Start fetching!
        getAllFreshSurveys();
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
        // Timeout? Gets only the first ten items.
        if (t instanceof SocketTimeoutException) {
            int page = 1;
            int perPage = 10;
            fetchSurvey(page, perPage);
        }

        // Shows error message.
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * TODO UI's Methods
     * Called when the survey button clicked.
     */
    @OnClick(R.id.survey_button)
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
    @OnClick(R.id.refresh_button)
    public void onRefresh(View v) {
        getAllFreshSurveys();
    }

    /**
     * TODO Private Methods
     * Fetches the survey from server.
     */
    private void fetchSurvey(int page, int perPage) {
        SurveyParam param = new SurveyParam(Constant.ACCESS_TOKEN, page, perPage);
        if (surveyAPI == null) {
            surveyAPI = new SurveyAPIExecutor(this, param);
        } else {
            surveyAPI.setParam(param);
        }
        surveyAPI.execute();
    }

    /**
     * Gets all fresh surveys
     */
    private void getAllFreshSurveys() {
        int page = 0;
        int perPage = 0;
        fetchSurvey(page, perPage);
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
                .load(survey.getHighResolutionImageUrl())
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

        // Scroll to the position marked!
        int dimension = 2;
        int loc[] = new int[dimension];

        // Retrieves the position x, y of the marked view.
        toView.getLocationOnScreen(loc);
        int x = 0;
        int y = toIndex == 0 ? 0 : loc[1];

        // Scroll to the position.
        bulletScrollView.smoothScrollTo(x, y);
    }
}
