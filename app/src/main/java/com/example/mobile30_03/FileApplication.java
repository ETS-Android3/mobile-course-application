package com.example.mobile30_03;

import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FileApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Method m = null;
        try {
            m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
