package com.framework.android.my.commonlibrary.imageload;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public interface ImageLoaderStrategy {
    void showImage(@NonNull ImageLoaderOptions options);

    void cleanMemory(Context context);

    void init(@NonNull ImageLoaderConfig config);
}