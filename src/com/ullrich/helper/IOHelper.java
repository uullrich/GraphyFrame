package com.ullrich.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

public class IOHelper {
    AssetManager assets;

    public IOHelper(Context context) {
        this.assets = context.getAssets();
    }

    public InputStream readAsset(String fileName) throws IOException {
        return assets.open(fileName);
    }
}
