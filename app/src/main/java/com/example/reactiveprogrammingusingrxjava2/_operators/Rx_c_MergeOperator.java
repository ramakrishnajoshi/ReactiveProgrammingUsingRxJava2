package com.example.reactiveprogrammingusingrxjava2._operators;


import com.example.reactiveprogrammingusingrxjava2.BaseActivity;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class Rx_c_MergeOperator extends BaseActivity {

    @Override
    protected void onResume() {
        super.onResume();

        Observable numberObservable = Observable
                .interval(0, 1, TimeUnit.SECONDS);
        Observable textObservable = Observable
                .interval(1,3, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return "a" + aLong;
                    }
                });

        Observable
                .merge(numberObservable, textObservable)
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logMessage(d.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        logMessage(o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        logMessage(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        logMessage("oncomplete");
                    }
                });
    }
}
