package com.example.reactiveprogrammingusingrxjava2.a_retrofit_api_calls;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.example.reactiveprogrammingusingrxjava2.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("SetTextI18n")
public class RetrofitAPICallActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apicall);

        textView = findViewById(R.id.textView2);

        getRetrofit();
    }

    void getRetrofit(){

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

        Retrofit retrofit = new Retrofit.Builder()
                                .addConverterFactory(GsonConverterFactory.create()) //From Retrofit 2.0 converter is must
                                .client(httpClient.build())     // For intercepting requests/responses
                                .baseUrl("http://www.mocky.io/v2/")
                                .build();

        RetrofitNetworkCallBackend networkCallBackend = retrofit.create(RetrofitNetworkCallBackend.class);
        networkCallBackend.getDetails().enqueue(new Callback<Object>() {
            /*
             * Invoked for a received HTTP response.
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             * */
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    textView.setText(
                            "response.body() : " + response.body() + " \n \n" +
                            "response.headers() : " + response.headers());
                } else {
                        textView.setText(
                                "response.errorBody() : " + response.errorBody().toString() + " \n \n" +
                                        "response.headers() : " + response.headers());

                    Toast.makeText(RetrofitAPICallActivity.this, "Retrying on error" , Toast.LENGTH_SHORT).show();
                    //You can use Call.clone() method to clone request and enqueue the request.
                    // There should be a kill-switch like max 2 retries otherwise this clones indefinitely if error retains
                    call.clone().enqueue(this);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable throwable) {
                textView.setText("Error : " + throwable.getLocalizedMessage());
                Toast.makeText(RetrofitAPICallActivity.this, "Failure" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
