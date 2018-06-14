package com.framework.android.my.commonlibrary.imageload;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.framework.android.my.commonlibrary.utils.LogUtil;
import com.framework.android.my.commonlibrary.utils.TextUtil;


/**
 * Created by Administrator on 2017/3/21 0021.
 */
public final class GlideImageLoader implements ImageLoaderStrategy {
    private String baseUrl;
    private boolean skipMemoryCache;
    private boolean skipDiskCache;
    private int placeholder;
    private int error;

    @Override
    public void showImage(ImageLoaderOptions options) {
        GenericRequestBuilder mGenericRequestBuilder = init(options);
        if (mGenericRequestBuilder != null) {
            showImageLast(mGenericRequestBuilder, options);
        }
    }

    @Override
    public void cleanMemory(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(context).clearMemory();
        }
    }

    @Override
    public void init(@NonNull ImageLoaderConfig config) {
        // 需要非空判断
        if (config != null) {
            baseUrl = config.baseUrl;
            skipMemoryCache = config.skipMemoryCache;
            skipDiskCache = config.skipDiskCache;
            placeholder = config.placeholder;
            error = config.error;
        }
    }

    public DrawableTypeRequest getGenericRequestBuilder(RequestManager manager, ImageLoaderOptions options) {
        final String url = options.getUrl();
        if (!TextUtils.isEmpty(url)) {
            if (TextUtil.notEmpty(baseUrl) && (!url.startsWith("http://") || !url.startsWith("https://"))) {
                return manager.load(baseUrl + url);
            } else{
                return manager.load(url);
            }
        }
        return manager.load(options.getResource());
    }

    public RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    public GenericRequestBuilder init(ImageLoaderOptions options) {
        View v = options.getViewContainer();

        //存在问题
        // java.lang.IllegalArgumentException You cannot start a load for a destroyed activity
        //RequestManager manager=getRequestManager(v.getContext());
        RequestManager manager = getRequestManager(v.getContext());
        if (v instanceof ImageView) {
            GenericRequestBuilder mDrawableTypeRequest = getGenericRequestBuilder(manager, options).asBitmap();
            if (options.isAsGif()) {
                mDrawableTypeRequest = getGenericRequestBuilder(manager, options);
            }
            //装载参数
            mDrawableTypeRequest = loadGenericParams(mDrawableTypeRequest, options);
            return mDrawableTypeRequest;
        }
        return null;
    }

    private GenericRequestBuilder loadGenericParams(GenericRequestBuilder mGenericRequestBuilder, final ImageLoaderOptions options) {
        final View view = options.getViewContainer();
        GenericRequestBuilder builder = mGenericRequestBuilder;
        if (mGenericRequestBuilder instanceof DrawableTypeRequest) {
            if (options.isCrossFade()) {
                ((DrawableTypeRequest) mGenericRequestBuilder).crossFade();
            }
            if (options.isAsGif()) {
                builder = ((DrawableTypeRequest) mGenericRequestBuilder).asGif();
            }
        }
        /**
         * 优先局部设置
         */
        if (options.isSkipMemoryCache()) {
            builder.skipMemoryCache(options.isSkipMemoryCache());
        } else {
            builder.skipMemoryCache(skipMemoryCache);
        }

        if (options.getImageSize() != null) {
            int width = getSize(options.getImageSize().getWidth(), view);
            int height = getSize(options.getImageSize().getHeight(), view);
            LogUtil.i("load params " + options.getImageSize().getWidth() + "  : " + options.getImageSize().getHeight());
            builder.override(width, height);
        }
        if (options.getPlaceHolderDrawable() != -1) {
            builder.placeholder(options.getPlaceHolderDrawable());
        } else {
            if (placeholder > 0) {
                builder.placeholder(placeholder);
            }
        }
        if (options.getErrorDrawable() != -1) {
            builder.error(options.getErrorDrawable());
        } else {
            if (error > 0) {
                builder.placeholder(error);
            }
        }

        if (options.getGlideCircleTransform() != null) {
            builder.transform(options.getGlideCircleTransform());
        }

        if (options.getGlideRoundTransform() != null) {
            builder.transform(options.getGlideRoundTransform());
        }
        if (options.getDiskCacheStrategy() != ImageLoaderOptions.DiskCacheStrategy.DEFAULT) {
            switch (options.getDiskCacheStrategy()) {
                case NONE:
                    builder.diskCacheStrategy(DiskCacheStrategy.NONE);
                    break;
                case All:
                    builder.diskCacheStrategy(DiskCacheStrategy.ALL);
                    break;
                case SOURCE:
                    builder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                    break;
                case RESULT:
                    builder.diskCacheStrategy(DiskCacheStrategy.RESULT);
                    break;
                default:
                    break;
            }
        } else if (skipDiskCache) {
            builder.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        return builder;
    }

    private void showImageLast(GenericRequestBuilder mDrawableTypeRequest, ImageLoaderOptions options) {
        final ImageView img = (ImageView) options.getViewContainer();

        // 是否使用高斯模糊
        if (options.isBlurImage()) {
            // 具体的高斯模糊这里就不实现了，直接展示图片
            mDrawableTypeRequest.into(img);
            return;
        }
        // 是否展示一个gif
        if (options.isAsGif()) {
            ((GifRequestBuilder) mDrawableTypeRequest).dontAnimate().into(new SimpleTarget<GifDrawable>() {
                @Override
                public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
                    img.setImageDrawable(resource);
                    resource.start();
                }
            });
            return;
        }
        if (options.getTarget() != null) {
            mDrawableTypeRequest.into(options.getTarget());
            return;
        }
        mDrawableTypeRequest.into(img);
    }

    /**
     * 获取资源尺寸
     *
     * @param resSize
     * @return 默认返回原始尺寸
     */
    private int getSize(int resSize, View container) {
        if (resSize <= 0) {
            return SimpleTarget.SIZE_ORIGINAL;
        } else {
            try {
                return container.getContext().getResources().getDimensionPixelOffset(resSize);

            } catch (Resources.NotFoundException e) {
                LogUtil.e(e.getMessage());
                return resSize;
            }
        }
    }
}