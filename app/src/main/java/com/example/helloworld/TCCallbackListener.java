package com.example.helloworld;

import android.net.Uri;

import java.io.File;

public interface TCCallbackListener {

    void jump(Uri uri, File file, int requestCode);
}
