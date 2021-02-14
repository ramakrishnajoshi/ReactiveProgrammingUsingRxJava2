package com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls;

import com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls.models.ProfileInfo;
import com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls.models.WalletInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CustomerInfoBackend {
    @GET("/profile-info")
    Observable<ProfileInfo> getProfileInfo();

    @GET("/wallet-info")
    Observable<WalletInfo> getWalletInfo();
}
