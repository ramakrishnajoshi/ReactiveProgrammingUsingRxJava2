package com.example.reactiveprogrammingusingrxjava2.c_operators;

import android.annotation.SuppressLint;

import com.example.reactiveprogrammingusingrxjava2.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class Rx_d_ConcatOperator extends BaseActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();

        Observable aSeriesObservable = Observable.just("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10");
        Observable bSeriesObservable = Observable.just("B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8");

        Observable
                .concat(bSeriesObservable, aSeriesObservable)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        logMessage(o.toString()); //First gets all items from first observable passed to concat and then second observable items
                    }
                });
    }
}
