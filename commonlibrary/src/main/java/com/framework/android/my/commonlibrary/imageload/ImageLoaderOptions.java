package com.framework.android.my.commonlibrary.imageload;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.bumptech.glide.request.target.BaseTarget;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public final class ImageLoaderOptions {
    private View viewContainer;  // 图片容器
    private String url;  // 图片地址
    private Integer resource;  // 图片地址
    private int placeHolderDrawable;  // 设置展位图
    private ImageSize imageSize;  //设置图片的大小
    private int errorDrawable;  //是否展示加载错误的图片
    private boolean asGif = false;   //是否作为gif展示
    private boolean isCrossFade = true; //是否渐变平滑的显示图片,默认为true
    private boolean isSkipMemoryCache = false; //是否跳过内存缓存
    private DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.DEFAULT; //磁盘缓存策略
    private boolean blurImage; //是否使用高斯模糊
    private BaseTarget target; //target
    private GlideRoundTransform mGlideRoundTransform;  //圆角图片
    private GlideCircleTransform mGlideCircleTransform;//圆形图片

    public ImageLoaderOptions(Builder builder) {
        this.asGif = builder.asGif;
        this.errorDrawable = builder.errorDrawable;
        this.placeHolderDrawable = builder.placeHolderDrawable;
        this.imageSize = builder.mImageSize;
        this.isCrossFade = builder.isCrossFade;
        this.isSkipMemoryCache = builder.isSkipMemoryCache;
        this.mDiskCacheStrategy = builder.mDiskCacheStrategy;
        this.url = builder.url;
        this.resource = builder.resource;
        this.viewContainer = builder.mViewContainer;
        this.blurImage = builder.blurImage;
        this.target = builder.target;
        this.mGlideRoundTransform = builder.mGlideRoundTransform;
        this.mGlideCircleTransform = builder.mGlideCircleTransform;
    }

    public GlideRoundTransform getGlideRoundTransform() {
        return mGlideRoundTransform;
    }

    public GlideCircleTransform getGlideCircleTransform() {
        return mGlideCircleTransform;
    }

    public BaseTarget getTarget() {
        return target;
    }

    public Integer getResource() {
        return resource;
    }

    public boolean isBlurImage() {
        return blurImage;
    }

    public View getViewContainer() {
        return viewContainer;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolderDrawable() {
        return placeHolderDrawable;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public int getErrorDrawable() {
        return errorDrawable;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return mDiskCacheStrategy;
    }

    public final static class Builder {
        private int placeHolderDrawable = -1;  // 设置展位图
        private View mViewContainer;  // 图片容器
        private String url;  // 图片地址
        private Integer resource;  // 图片地址
        private ImageSize mImageSize;  //设置图片的大小
        private int errorDrawable = -1;  //是否展示加载错误的图片
        private boolean asGif = false;   //是否作为gif展示
        private boolean isCrossFade = false; //是否渐变平滑的显示图片
        private boolean isSkipMemoryCache = false; //是否跳过内存缓存
        private boolean blurImage = false; //是否使用高斯模糊
        private DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.DEFAULT; //磁盘缓存策略
        private BaseTarget target; //target
        private GlideRoundTransform mGlideRoundTransform;       //圆角图片
        private GlideCircleTransform mGlideCircleTransform;     //圆形图片

        public Builder setGlideRoundTransform(GlideRoundTransform mGlideRoundTransform) {
            this.mGlideRoundTransform = mGlideRoundTransform;
            return this;
        }

        public Builder setGlideCircleTransform(GlideCircleTransform mGlideCircleTransform) {
            this.mGlideCircleTransform = mGlideCircleTransform;
            return this;
        }

        public Builder(@NonNull View v, @NonNull String url) {
//            if (TextUtil.isEmpty(url))
//                throw new NullPointerException("The image url can not be empty.");
            this.url = url;
            this.mViewContainer = v;
        }

        public Builder(@NonNull View v, @NonNull Integer resource) {
            this.resource = resource;
            this.mViewContainer = v;
        }

        public Builder placeholder(@DrawableRes int placeHolderDrawable) {
            this.placeHolderDrawable = placeHolderDrawable;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder blurImage(boolean blurImage) {
            this.blurImage = blurImage;
            return this;
        }

        public Builder isSkipMemoryCache(boolean isSkipMemoryCache) {
            this.isSkipMemoryCache = isSkipMemoryCache;
            return this;

        }

        public Builder override(int width, int height) {
            this.mImageSize = new ImageSize(width, height);
            return this;
        }

        public Builder asGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public Builder error(@DrawableRes int errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder target(BaseTarget target) {
            this.target = target;
            return this;
        }

        public Builder diskCacheStrategy(DiskCacheStrategy mDiskCacheStrategy) {
            this.mDiskCacheStrategy = mDiskCacheStrategy;
            return this;
        }

        public ImageLoaderOptions build() {
            return new ImageLoaderOptions(this);
        }
    }

    //对应重写图片size
    public final static class ImageSize {
        private int width = 0;
        private int height = 0;

        public ImageSize(int width, int heigh) {
            this.width = width;
            this.height = heigh;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    //对应磁盘缓存策略
    public enum DiskCacheStrategy {
        All, NONE, SOURCE, RESULT, DEFAULT
    }
}