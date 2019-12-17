package com.example.reactiveprogrammingusingrxjava2._operators.concurrentcalls;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CustomerInfoBackend {
    @GET("/profile-info")
    Observable<Object> getProfileInfo();

    @GET("/wallet-info")
    Observable<Object> getWalletInfo();
}
