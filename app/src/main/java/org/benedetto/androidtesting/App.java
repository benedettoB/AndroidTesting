package org.benedetto.androidtesting;

import android.app.Application;

import org.benedetto.androidtesting.tests.Collections;
import org.benedetto.androidtesting.util.UtilFunctionsKt;

public class App extends Application {
    private final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        UtilFunctionsKt.log(TAG, "App started");
        //Collections test
        new Collections().begin();
    }
}
