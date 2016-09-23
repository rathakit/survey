package com.nimbl3.survey;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nimbl3.survey.apis.APIConnector;
import com.nimbl3.survey.apis.APIExecuteListener;
import com.nimbl3.survey.apis.SurveyAPIExecutor;
import com.nimbl3.survey.models.Survey;
import com.nimbl3.survey.utilities.Constant;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.nimbl3.survey", appContext.getPackageName());
    }

    @Test
    public void testSurveyAPISuccess() throws Exception {
        int count = 1;
        final CountDownLatch signal = new CountDownLatch(count);
        SurveyAPIExecutor surveyAPIExecutor = new SurveyAPIExecutor(new APIExecuteListener<List<Survey>>() {
            @Override
            public void onSuccess(APIConnector connector, List<Survey> obj) {
                // Checks APIConnector
                assertTrue(connector instanceof SurveyAPIExecutor);

                // Checks response's object.
                assertTrue(obj instanceof List);

                // Countdown by one.
                signal.countDown();
            }

            @Override
            public void onFailure(APIConnector connector, Throwable t) {
                // Countdown by one.
                signal.countDown();
            }
        }, Constant.ACCESS_TOKEN);

        // Execute!
        surveyAPIExecutor.execute();

        // Wait the request for 30 seconds.
        int _30secs = 30;
        signal.await(_30secs, TimeUnit.SECONDS);
    }

    @Test
    public void testSurveyAPIFailed() throws Exception {
        int count = 1;
        final CountDownLatch signal = new CountDownLatch(count);
        SurveyAPIExecutor surveyAPIExecutor = new SurveyAPIExecutor(new APIExecuteListener<List<Survey>>() {
            @Override
            public void onSuccess(APIConnector connector, List<Survey> obj) {
                // Countdown by one.
                signal.countDown();
            }

            @Override
            public void onFailure(APIConnector connector, Throwable t) {
                // Checks APIConnector
                assertTrue(connector instanceof SurveyAPIExecutor);

                // Check exception.
                assertTrue(t instanceof IllegalArgumentException);

                // Check error message.
                Context appContext = InstrumentationRegistry.getTargetContext();
                assertSame(appContext.getString(R.string.api_access_token_not_set), t.getMessage());

                // Countdown by one.
                signal.countDown();
            }
        }, "");

        // Execute!
        surveyAPIExecutor.execute();

        // Wait the request for 30 seconds.
        int _30secs = 30;
        signal.await(_30secs, TimeUnit.SECONDS);
    }
}
