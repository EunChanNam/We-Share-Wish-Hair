package com.example.wishhair;

import android.util.Log;

import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

public class CustomRetryPolicy implements RetryPolicy {
    private static final int MAX_RETRY_COUNT = 3;
    private static final int INITIAL_TIMEOUT_MS = 10000;

    private int retryCount = 0;
    private int timeoutMs = INITIAL_TIMEOUT_MS;
    private static final String TAG = "custom retry policy";

    @Override
    public int getCurrentTimeout() {
        return timeoutMs;
    }

    @Override
    public int getCurrentRetryCount() {
        return retryCount;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {
        if (++retryCount <= MAX_RETRY_COUNT) {
            // 재시도 대기 시간 조절
            timeoutMs = 20000; // 20초

            Log.d(TAG, "Retry #" + retryCount + " with timeout " + timeoutMs + "ms");
        } else {
            throw error;
        }
    }
}

