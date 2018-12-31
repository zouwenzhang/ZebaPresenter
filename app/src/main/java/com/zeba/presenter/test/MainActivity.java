package com.zeba.presenter.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zeba.presenter.ZebaPresenter;
import com.zeba.presenter.annotation.Presenter;

public class MainActivity extends AppCompatActivity {

    @Presenter
    BookPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ZebaPresenter.inject(this);
        String name=presenter.getBookName();
        Toast.makeText(this,"name="+name,Toast.LENGTH_SHORT).show();
    }

    public String getBookName(){
        return "1212";
    }
}
