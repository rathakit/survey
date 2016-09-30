package com.nimbl3.survey.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nimbl3.survey.views.presenter.ViewConnector;

public abstract class BasedActivity extends AppCompatActivity implements ViewConnector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Displays the message for a few seconds.
     * @param message
     */
    @Override
    public void showMessage(String message) {
        // Shows error message.
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
