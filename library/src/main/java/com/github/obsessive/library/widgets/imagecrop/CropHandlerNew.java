package com.github.obsessive.library.widgets.imagecrop;

import android.app.Activity;
import android.net.Uri;

/**
 * Created by young on 2015/9/10.
 */
public interface CropHandlerNew {

    void onPhotoCropped(Uri uri);

    void onCropCancel();

    void onCropFailed(String message);

    CropParamsNew getCropParams();

    Activity getContext();
}
