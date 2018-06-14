package com.framework.android.my.commonlibrary;

import com.framework.android.my.commonlibrary.mvp.presenter.BasePresenter;
import com.framework.android.my.commonlibrary.mvp.view.IView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author dengwg
 * @date 2018/6/13
 */
public  abstract class BaseActivity<T, P extends BasePresenter> extends RxAppCompatActivity implements IView<T> {

}
