package org.benedetto.androidtesting;

import android.app.Application;

import org.benedetto.androidtesting.tests.Collections;
import org.benedetto.androidtesting.tests.hx.ProtocolProcessor;
import org.benedetto.androidtesting.util.UtilFunctionsKt;

public class App extends Application {
    private final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        UtilFunctionsKt.log(TAG, "App started");
        //Collections test
        new Collections().begin();

        //Example of raw response (hex string)
        String rawResponse1 = "010203020002cfff";
        ProtocolProcessor.INSTANCE.processResponse(rawResponse1);
    }
}
