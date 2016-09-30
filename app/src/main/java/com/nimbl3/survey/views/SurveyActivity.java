package com.nimbl3.survey.views;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nimbl3.survey.R;
import com.nimbl3.survey.models.Survey;
import com.nimbl3.survey.views.presenter.SurveyPresenter;
import com.nimbl3.survey.views.presenter.SurveyViewConnector;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurveyActivity extends BasedActivity implements SurveyViewConnector {

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

    // The bullets
    private List<View> bullets;

    // The presenter
    private SurveyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // ButterKnife Binding
        ButterKnife.bind(this);

        // Presenter
        presenter = new SurveyPresenter(this);

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
        bullets = new ArrayList<>();

        // Start fetching!
        getAllFreshSurveys();
    }

    /**
     * Creates the bullets.
     * @param count - the number of bullets
     */
    @Override
    public void createBulletView(int count) {
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
     * Displays the survey details.
     * @param survey
     */
    @Override
    public void displaySurvey(Survey survey) {
        // Name
        nameTextView.setText(survey.getName());

        // Description
        descTextView.setText(survey.getDescription());

        // Background
        Picasso.with(this)
                .load(survey.getHighResolutionImageUrl())
                .into(backgroundImageView);

        // Shows the survey button.
        surveyButton.setVisibility(View.VISIBLE);
    }

    /**
     * Marks the current survey.
     * @param fromIndex
     * @param toIndex
     */
    @Override
    public void markSurveyBullet(int fromIndex, int toIndex) {
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

    /**
     * TODO UI's Methods
     * Called when the survey button clicked.
     */
    @OnClick(R.id.survey_button)
    public void onSurvey(View v) {
        presenter.displayNextSurvey();
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
     * Gets all fresh surveys
     */
    private void getAllFreshSurveys() {
        int page = 0;
        int perPage = 0;
        presenter.fetchSurvey(page, perPage);
    }
}
