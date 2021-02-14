package com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.reactiveprogrammingusingrxjava2.R;
import com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls.models.ProfileInfo;
import com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls.models.WalletInfo;
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
        setContentView(R.layout.activity_concurrent_api_call);

        ConcurrentAPIViewModel viewModel = ViewModelProviders.of(this).get(ConcurrentAPIViewModel.class);

        button = findViewById(R.id.button_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getCustomerInformation(getCustomerInfoBackend());
            }
        });

        viewModel.getUserProfileInfo().observe(this, new Observer<ProfileInfo>() {
            @Override
            public void onChanged(@Nullable ProfileInfo profileInfo) {
                ((TextView)findViewById(R.id.customer_profile_info)).setText(profileInfo.toString());
            }
        });

        viewModel.getUserWalletInfo().observe(this, new Observer<WalletInfo>() {
            @Override
            public void onChanged(@Nullable WalletInfo walletInfo) {
                ((TextView)findViewById(R.id.customer_wallet_info)).setText(walletInfo.toString());
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
                .baseUrl("https://testserver.free.beeceptor.com")
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CustomerInfoBackend customerInfoBackend = retrofit.create(CustomerInfoBackend.class);
        return customerInfoBackend;
    }
}
