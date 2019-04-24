// ICustomAidlInterface.aidl
package com.sjl.exercise;

// Declare any non-default types here with import statements
import com.sjl.exercise.bean.UserBean;

interface ICustomAidlInterface {
    String getCurrentTime();

    void insertUser(in UserBean userBean);

    List<UserBean> getUsers();

    void clearUser();
}
