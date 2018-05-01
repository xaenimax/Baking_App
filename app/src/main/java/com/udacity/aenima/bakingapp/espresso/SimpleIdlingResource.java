package com.udacity.aenima.bakingapp.espresso;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private ResourceCallback mResourceCallback;

    private AtomicBoolean isIdle = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;

    }

    public void setIdleState(boolean idleState){
        isIdle.set(idleState);
        if(isIdle.get() && mResourceCallback != null){
            mResourceCallback.onTransitionToIdle();
        }
    }
}
