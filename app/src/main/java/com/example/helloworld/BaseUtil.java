package com.example.helloworld;

import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BaseUtil {

    private static final String TAG = "tigercheng";

    public static void initActivity(View view, ActionBar actionBar, String title) {
//        ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

//        Intent intent = getIntent();


    }

//    public static void

    //信息是否完善 完善返回true
    public static boolean isCompleted(int characterFlags, int character) {
        boolean result = false;
//        characterFlags = pref.getInt("characterFlags", 0b000000);
        Log.d(TAG, "the characterFlags: " + Integer.toBinaryString(characterFlags));
        switch (character) {
            case 1:
                result = characterFlags >>> 5 == 0b000001;
//                Log.d(TAG, "" + Integer.toBinaryString(characterFlags >>> 5));
                Log.d(TAG, "characterFlags = 1 completed ? " + result);
                break;
            case 2:
//                Log.d(TAG, "first: " + Integer.toBinaryString(characterFlags));
//                Log.d(TAG, "second: " + Integer.toBinaryString(0b010000));
//                result = (characterFlags & 0b010000) >>> 4 == 0b000001;
                result = (characterFlags & 0b010000) > 0;
//                Log.d(TAG, "characterFlags 2 and: " + Integer.toBinaryString(characterFlags & 0b010000));
                Log.d(TAG, "characterFlags = 2 completed ? " + result);
                break;
            case 3:
//                result = (characterFlags & 0b001000) >>> 3 == 0b000001;
                result = (characterFlags & 0b001000) > 0;
                Log.d(TAG, "characterFlags = 3 completed ? " + result);
                break;
            case 4:
//                result = (characterFlags & 0b000100) >>> 2 == 0b000001;
                result = (characterFlags & 0b000100) > 0;
                Log.d(TAG, "characterFlags = 4 completed ? " + result);
                break;
            case 5:
//                result = (characterFlags & 0b000010) >>> 1 == 0b000001;
                result = (characterFlags & 0b000010) > 0;
                Log.d(TAG, "characterFlags = 5 completed ? " + result);
                break;
        }
        Log.d(TAG, "isCompleted: " + result);
        return result;
    }

}
