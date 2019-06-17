package com.example.reactiveprogrammingusingrxjava2;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity /*implements RxBasics*/{
    public void logMessage(String message){
        Log.e(this.getPackageName(), message);
    }

    /*@Override
    public Observable getObservable() {
        return null;
    }

    @Override
    public Observer getObserver() {
        return null;
    }*/
}


interface RxBasics<T>{
    Observable<T> getObservable();
    Observer<T> getObserver();
}