package com.framework.android.my.commonlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.framework.android.my.commonlibrary.mvp.presenter.BasePresenter;
import com.framework.android.my.commonlibrary.mvp.view.IView;
import com.framework.android.my.commonlibrary.views.empty.EmptyLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * @author dengwg
 * @date 2018/6/13
 */
public  abstract class BaseActivity<T, P extends BasePresenter> extends RxAppCompatActivity implements IView<T> {
    /**
     * 加载状态布局
     */
    private EmptyLayout mEmptyLayout;
    @Nullable
    @Inject
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNeedTitleLayout()){

        }
    }

    /**
     * 是否需要标题栏
     * @return
     */
    protected abstract boolean isNeedTitleLayout();
    /**
     * 是否需要标题栏
     * @return
     */
    protected abstract boolean isNeedEmptyLayout();

    protected abstract int getContentLayout();

    protected abstract void initView();

    protected abstract void initData();

}
