package com.example.reactiveprogrammingusingrxjava2._operators.concurrentcalls;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.reactiveprogrammingusingrxjava2.R;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ConcurrentAPICallActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        ConcurrentAPIViewModel viewModel =
                ViewModelProviders.of(this).get(ConcurrentAPIViewModel.class);

         button = findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getCustomerInformation(getCustomerInfoBackend());
            }
        });

        viewModel.userWalletInfo.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                viewModel.getCustomerInformation(getCustomerInfoBackend());
            }
        });
    }

    private CustomerInfoBackend getCustomerInfoBackend() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(httpLoggingInterceptor);

        GsonBuilder gson = new GsonBuilder();
        gson.setLenient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://customerdata.free.beeceptor.com")
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                //.addConverterFactory(new GsonPConverterFactory(gson.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CustomerInfoBackend customerInfoBackend = retrofit.create(CustomerInfoBackend.class);
        return customerInfoBackend;
    }
}
