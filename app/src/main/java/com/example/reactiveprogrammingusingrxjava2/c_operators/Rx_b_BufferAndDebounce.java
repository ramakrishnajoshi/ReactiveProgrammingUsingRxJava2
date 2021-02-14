package com.example.reactiveprogrammingusingrxjava2.c_operators;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.reactiveprogrammingusingrxjava2.BaseActivity;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Rx_b_BufferAndDebounce extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * Buffer gathers items emitted by an Observable into batches and emit the batch instead of emitting one item at a time.
        * */
        getObservable()
                .subscribeOn(Schedulers.io())
                .buffer(6, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    @Override
    public Observable<Integer> getObservable() {
        return Observable.range(1, 99);
//        return Observable
//                .interval(0, 3, TimeUnit.SECONDS);
                //.cast(Integer.class); gives error as Long can't be converted/casted to Integer class.
    }

    @Override
    public Observer<List<Integer>> getObserver() {
        return new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {
                logMessage("onSubscribe");
            }

            @Override
            public void onNext(List<Integer> o) {
                logMessage(this.getClass().getEnclosingMethod().getName() + o.toString());
            }

            /*
            * Control reaches onError when there is an error.
            * Ex: When Observable emits Long Integers and you try to cast to normal Integer class by using cast() operator then
            * there is an error as Long can't be converted to Integer
            * */
            @Override
            public void onError(Throwable e) {
                logMessage("onError" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                logMessage("onComplete");
            }
        };
    }
}
