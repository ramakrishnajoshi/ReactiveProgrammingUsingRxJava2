package com.example.reactiveprogrammingusingrxjava2.c_operators;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import com.example.reactiveprogrammingusingrxjava2.BaseActivity;
import com.example.reactiveprogrammingusingrxjava2.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Rx_a_CreateOperator extends BaseActivity {

    Observable<Integer> observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operator);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //A new observer would be immediately notified of the latest data from the LiveData.
                observable
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getObserver());
            }
        });

        getObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()/*, true*/) //you can use observeOn(scheduler, true) to ensure onNext isn't skipped if there is a call to onError
                .subscribe(getObserver());
    }

    /**
     * Expected Behavior -> Calls to emitter.onNext(1) , emitter.onNext(2), emitter.onError(new Throwable()), emitter.onNext(3), emitter.onComplete() would result
     * in the Observable emitting 1, 2, 3, error_message and onComplete. But actual behavior is when onError gets emitted, the remaining items like
     * onNext(3) etc are ignored/not emitted.
     * More here : https://github.com/ReactiveX/RxJava/issues/2887 and here https://github.com/ReactiveX/RxJava/issues/2887#issuecomment-345299685
     *
     * Note to future self because I know I'm going to forget this (again): you can use `.observeOn(Scheduler scheduler, boolean delayError)` to
     * ensure onNext isn't skipped by onError.
     */
    @Override
    public Observable<Integer> getObservable() {
        observable =  Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Throwable()); //you can use .observeOn(scheduler, true) to ensure that onNext isn't skipped if there is a call to onError
                emitter.onNext(3);
                emitter.onComplete();

                // emitter.onError(new Throwable()); would give below error as onComplete has already been called before calling onError
                // The exception could not be delivered to the consumer because it has already canceled/disposed the flow or the exception has nowhere
                // to go to begin with. Further reading: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling | java.lang.Throwable
            }
        });
        return observable;
    }

    @Override
    public Observer<Integer> getObserver() {
        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                logMessage("onSubscribe");
            }

            @Override
            public void onNext(Integer o) {
                logMessage("onNext" + o);
            }

            @Override
            public void onError(Throwable e) {
                logMessage("onError");
            }

            @Override
            public void onComplete() {
                logMessage("onComplete");
            }
        };
        return observer;
    }
}
