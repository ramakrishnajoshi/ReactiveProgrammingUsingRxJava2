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
        //Emit 0,1,2,3... after 2 seconds from calling this function and with a gap of 3s in-between emissions.
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
                .subscribeOn(Schedulers.io()) //from here code is run on a new io thread(non-ui thread).
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                       logMessage(aLong.toString());
                       return aLong /*+ 1*/;
                    }
                })
                //.map(new TimeConverter<Long, Long>())
//                .filter(new Predicate<Object>() {
//                    @Override
//                    public boolean test(Object o) throws Exception {
//                        if ((Long)o % 2 == 0)
//                            return true;
//                        else
//                            return false;
//                    }
//                })
               // .filter((Predicate<Object>) o -> (Long) o % 2 == 0) //same thing as above but using lambda
               // .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread()) //from here the control is switched to ui-thread and code is run on ui thread.
                .map(x -> x/* * x*/)
                .skipLast(5)
                .subscribe(observer);
    }

    /*
    * This class and it's function does nothing. IT has been written just to demonstrate various
    * ways in which value can be transformed and returned back.
    * */
    class TimeConverter implements Function<Long, Long> {

        @Override
        public Long apply(Long aLong)  {
            logMessage("map operator .apply ThreadId : " + Thread.currentThread().getId());
            return  aLong;
        }
    }

    void logMessage(String message){
        Log.e(this.getPackageName(), message);
    }
}
