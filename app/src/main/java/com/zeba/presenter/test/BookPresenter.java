package com.zeba.presenter.test;

import com.zeba.presenter.BasePresenter;

public class BookPresenter extends BasePresenter<MainActivity>{

    public String getBookName(){
        return view().getBookName();
    }
}
