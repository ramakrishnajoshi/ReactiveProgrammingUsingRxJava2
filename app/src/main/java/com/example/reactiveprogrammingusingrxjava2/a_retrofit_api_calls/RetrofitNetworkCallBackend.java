package com.example.reactiveprogrammingusingrxjava2.a_retrofit_api_calls;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitNetworkCallBackend {

    @GET("5c9894882f000073009f30a9")  // http://www.mocky.io/v2/5c9894882f000073009f30a9
    Call<Object> getDetails();  //retrofit
}
