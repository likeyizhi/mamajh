package com.github.obsessive.library.widgets.imagecrop;

import android.app.Activity;
import android.net.Uri;

/**
 * Created by young on 2015/9/10.
 */
public interface CropHandler {

    void onPhotoCropped(Uri uri);

    void onCropCancel();

    void onCropFailed(String message);

    CropParams getCropParams();

    Activity getContext();
}
