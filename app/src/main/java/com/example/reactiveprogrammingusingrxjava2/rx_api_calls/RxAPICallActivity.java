package com.example.reactiveprogrammingusingrxjava2.rx_api_calls;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.example.reactiveprogrammingusingrxjava2.R;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("SetTextI18n")
public class RxAPICallActivity extends AppCompatActivity {

    TextView textView;
    Observer observer;
    String TAG = "RxAPICallActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall);

        textView = findViewById(R.id.textView2);

        observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG, "onNext");
                textView.setText(
                        "response.body() : " + o.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
                textView.setText(
                        "response.body() : " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };

        Retrofit retrofit = getRetrofit();

        RxNetworkCallBackend networkCallBackend = retrofit.create(RxNetworkCallBackend.class);
        networkCallBackend.getDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Retrofit getRetrofit(){
        /**
         * While developing your app and for debugging purposes it’s nice to have a log feature
         * integrated to show request and response information. Since logging isn’t integrated by
         * default anymore in Retrofit 2, we need to add a logging interceptor for OkHttp. Luckily
         * OkHttp already ships with this interceptor and you only need to activate it for your
         * OkHttpClient.*/
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        Retrofit retrofit =
                new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create()) //From Retrofit 2.0 converter is a must
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //An Adapter for Retrofit adapting RxJava 2.x types like Observable.
                        .client(httpClient.build())     // For intercepting requests/responses
                        .baseUrl("http://www.mocky.io/v2/")
                        .build();
        return retrofit;
    }
}
