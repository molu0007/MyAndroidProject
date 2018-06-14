package com.framework.android.my.commonlibrary.views.empty;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.framework.android.my.commonlibrary.R;

/**
 * @author dengwg
 * @date 2018/6/13
 */
public class EmptyLayout extends FrameLayout implements View.OnClickListener {
    /**
     * 多状态父布局
     */
    private RelativeLayout errorLayout,emptyLayout,loadingLayout;
    private ImageView loadingImage;
    /**
     * 加载中
     */
    public static final int STATUS_LOADING = 0;
    /**
     * 无网络
     */
    public static final int STATUS_NO_NET = 2;
    /**
     * 无数据
     */
    public static final int STATUS_NO_DATA = 3;
    public static final int STATUS_HIDE = 4;
    private int currentStatus = STATUS_HIDE;

    public EmptyLayout(@NonNull Context context) {
        this(context, null);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.empty_layout);
        View view = View.inflate(context, R.layout.empty_layout, this);
        errorLayout = (RelativeLayout) view.findViewById(R.id.rl_empty_layout_error);
        emptyLayout= (RelativeLayout) view.findViewById(R.id.rl_empty_layout_empty);
        loadingLayout = (RelativeLayout) view.findViewById(R.id.rl_empty_layout_loading);
        loadingImage= (ImageView) view.findViewById(R.id.iv_empty_loading_image);
        view.findViewById(R.id.rl_empty_layout_error).setOnClickListener(this);
        view.findViewById(R.id.rl_empty_layout_empty).setOnClickListener(this);
        typedArray.recycle();
        updateViewVisible();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }
    public int getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
        updateViewVisible();
    }
    private void updateViewVisible() {
        if (currentStatus != STATUS_HIDE) {
            if (getVisibility() == GONE) {
                setVisibility(VISIBLE);
            }
        } else {
            if (getVisibility() == VISIBLE) {
                setVisibility(GONE);
            }
            return;
        }
        switch (currentStatus) {
            case STATUS_HIDE:
//                container.setVisibility(GONE);
                break;
            case STATUS_NO_DATA:
//                container.setVisibility(VISIBLE);
                errorLayout.setVisibility(GONE);
                loadingLayout.setVisibility(GONE);
                emptyLayout.setVisibility(VISIBLE);
            case STATUS_NO_NET:
//                container.setVisibility(VISIBLE);
                errorLayout.setVisibility(VISIBLE);
                loadingLayout.setVisibility(GONE);
                emptyLayout.setVisibility(GONE);
                break;
            case STATUS_LOADING:
//                container.setVisibility(VISIBLE);
                Glide.with(getContext()).load(R.drawable.loading_animation)
                        .into(loadingImage);
                errorLayout.setVisibility(GONE);
                emptyLayout.setVisibility(GONE);
                loadingLayout.setVisibility(VISIBLE);
                break;
            default:
                break;

        }
    }

    @Override
    public void onClick(View view) {
        if (mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    public interface OnRetryListener {
        void onRetry();
    }


    private OnRetryListener mOnRetryListener;


    public void setOnRetryListener(OnRetryListener onRetryListener) {
        mOnRetryListener = onRetryListener;
    }

}
