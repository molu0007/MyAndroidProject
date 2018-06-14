package com.framework.android.my.commonlibrary.mvp.model;


public interface IModel<R> {
    void onDestroy();

    R getRepositoryManager();
}
