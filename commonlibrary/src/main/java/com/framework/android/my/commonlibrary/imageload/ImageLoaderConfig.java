package com.framework.android.my.commonlibrary.imageload;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by Shyky on 2017/7/18.
 */
public final class ImageLoaderConfig {
    Context context;
    ImageLoaderStrategy imageLoaderStrategy;
    String baseUrl;
    boolean skipMemoryCache;
    boolean skipDiskCache;
    int placeholder;
    int error;

    public ImageLoaderConfig(Context context) {
        this.context = context;
    }

    public ImageLoaderConfig(@NonNull ImageLoaderConfig config) {
        if (config == null)
            throw new NullPointerException("The config can not be null.");
        imageLoaderStrategy = config.imageLoaderStrategy;
        baseUrl = config.baseUrl;
        skipMemoryCache = config.skipMemoryCache;
        skipDiskCache = config.skipDiskCache;
    }

    public ImageLoaderConfig imageLoader(@NonNull ImageLoaderStrategy imageLoaderStrategy) {
        this.imageLoaderStrategy = imageLoaderStrategy;
        return this;
    }

    public ImageLoaderConfig baseUrl(@NonNull String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ImageLoaderConfig skipMemoryCache(boolean skipMemoryCache) {
        this.skipMemoryCache = skipMemoryCache;
        return this;
    }

    public ImageLoaderConfig skipDiskCache(boolean skipDiskCache) {
        this.skipDiskCache = skipDiskCache;
        return this;
    }

    public ImageLoaderConfig placeholder(@DrawableRes int resourceId) {
        placeholder = resourceId;
        return this;
    }

    public ImageLoaderConfig error(@DrawableRes int resourceId) {
        error = resourceId;
        return this;
    }
}