package com.example.reactiveprogrammingusingrxjava2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logMessage("MainActivity.onResume threadId : " + Thread.currentThread().getId());
        justOperatorWorking();
    }

    private void justOperatorWorking() {
        Observable<Long> observable = Observable.interval(2, 3, TimeUnit.SECONDS);

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                logMessage("MainActivity.onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                logMessage("onNext threadId : " + Thread.currentThread().getId() + " item:" + o.toString());
                Toast.makeText(MainActivity.this, " onNext" + o.toString() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                logMessage("onError = [" + e + "]");
                Toast.makeText(MainActivity.this, " onError" + e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
                logMessage("MainActivity.onComplete");
            }
        };

        observable
                .subscribeOn(Schedulers.io())//whatever is executed downstream would be run on thread where observable is run (non-ui thread)
                //.map(x -> x+1) //using lambda
                .map(new Function<Long, Object>() {
                    @Override
                    public Object apply(Long aLong) {
                        logMessage("First map operator .apply ThreadId : " + Thread.currentThread().getId());
                        return aLong + 1;
                    }
                })
               // .map(new TimeConverter<Long, Object>())
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        if ((Long)o % 2 == 0)
                            return true;
                        else
                            return false;
                    }
                })
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread()) //whatever is executed downstream would be run on ui thread where observer is run.
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) {
                        logMessage("Second map operator2 .apply ThreadId : " + Thread.currentThread().getId());
                        return o;
                    }
                })
                .subscribe(observer);
    }

    class TimeConverter<Long, Object> implements Function<Long, Object> {

        @Override
        public Object apply(Long aLong)  {
            logMessage("map operator .apply ThreadId : " + Thread.currentThread().getId());
            return (Object) aLong;
        }
    }

    void logMessage(String message){
        Log.e(this.getPackageName(), message);
    }
}
