package com.framework.android.my.commonlibrary.imageload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Administrator on 2017/7/13 0022.
 */
public final class ImageLoaderManager {
    private static ImageLoaderStrategy imageLoaderStrategy;

    /**
     * 静态内部类实现单例模式，解决线程安全又避免了同步带来的性能影响
     */
    private static final class Holder {
        private static final ImageLoaderManager INSTANCE = new ImageLoaderManager();
    }

    private ImageLoaderManager() {

    }

    public static ImageLoaderManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 可创建默认的Options设置，假如不需要使用ImageView ，
     * 请自行new一个Imageview传入即可内部只需要获取Context
     *
     * @param container
     * @param url
     * @return
     */
    public static ImageLoaderOptions getDefaultOptions(@NonNull View container, @NonNull String url) {
        return new ImageLoaderOptions.Builder(container, url).isCrossFade(true).build();
    }

    public void showImage(@NonNull ImageLoaderOptions options) {
        if (imageLoaderStrategy != null) {
            imageLoaderStrategy.showImage(options);
        }
    }

    public void cleanMemory(Context context) {
        if (imageLoaderStrategy != null)
            imageLoaderStrategy.cleanMemory(context);
    }

    /**
     * 初始化，建议在Application的onCreate方法中进行
     *
     * @param config 加载配置
     */
    public static void init(@NonNull ImageLoaderConfig config) {
        if (config == null)
            throw new NullPointerException("The config can not be null.");
        if (config.imageLoaderStrategy == null) {
            // 默认使用Glide框架加载图片，如需要更换可以通过config.imageLoader(new GlideImageLoader())更换
            imageLoaderStrategy = new GlideImageLoader();
            imageLoaderStrategy.init(config);
        } else {
            imageLoaderStrategy = config.imageLoaderStrategy;
            imageLoaderStrategy.init(config);
        }
    }
}