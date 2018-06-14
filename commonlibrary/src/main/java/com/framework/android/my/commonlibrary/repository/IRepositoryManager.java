package com.framework.android.my.commonlibrary.repository;


import org.greenrobot.greendao.AbstractDaoSession;

/**
 * Created by COOTEK on 2017/7/31.
 */

public interface IRepositoryManager<D extends AbstractDaoSession> {
    <T> T getApi(Class<T> retrofitClass);


    <T> void clearApi(Class<T> retrofitClass);



    D getDaoSession();




    void clearAllCache();


//    DaoSession getDaoSession();



}
