package com.test.cesar.amkprueba.utils;

import android.support.annotation.Nullable;

/**
 * Created by Cesar on 26/08/2017.
 */

public interface ICommunication<T> {
    void onResponse(String callId, @Nullable T data);
}
