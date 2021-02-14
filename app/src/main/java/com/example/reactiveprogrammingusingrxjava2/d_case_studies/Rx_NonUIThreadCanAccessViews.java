package com.example.reactiveprogrammingusingrxjava2.d_case_studies;

import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.Nullable;
import android.widget.TextView;
import com.example.reactiveprogrammingusingrxjava2.BaseActivity;
import com.example.reactiveprogrammingusingrxjava2.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

 /*
  * Android recommends not to update/access views from non-ui thread but that does not mean we can't access views from non-ui thread.
  * Jake Wharton : `All view code does not throw if accessed off the main thread. Most calls silently fail`.
  * More here : https://github.com/ReactiveX/RxJava/issues/3605
  * */
public class Rx_NonUIThreadCanAccessViews extends BaseActivity {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall);
    }

    @Override
    protected void onResume() {
        super.onResume();

        logMessage("onCreate thread id " + Thread.currentThread().getId());

        textView = findViewById(R.id.textView2);

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onNext(7);
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer integer) {
                logMessage(integer.toString());
                logMessage("onNext thread id " + Thread.currentThread().getId());
                textView.setText(integer.toString()); //As this observer is running in non-ui thread how non-ui thread is able to touch/update textview?
                //Android recommends(not restricts) to touch views on Main Thread.

                if (Looper.myLooper() == Looper.getMainLooper()) {
                    textView.setText("In Main looper: " + Thread.currentThread().getName());
                } else {
                    textView.setText("Not in Main looper: " + Thread.currentThread().getName());
                }
            }

            @Override
            public void onError(Throwable e) {
                logMessage(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                logMessage("onComplete");
            }
        };

        observable
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread()) -> without switching control to main thread, we are able to update views from non-ui thread.
                .subscribe(observer);
    }
}
