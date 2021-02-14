package com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;
import com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls.models.ProfileInfo;
import com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls.models.WalletInfo;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class ConcurrentAPIViewModel extends ViewModel {

    private MutableLiveData<ProfileInfo> userProfileInfo = new MutableLiveData<>();
    private MutableLiveData<WalletInfo> userWalletInfo = new MutableLiveData<>();

    MutableLiveData<ProfileInfo> getUserProfileInfo(){
        return userProfileInfo;
    }

    MutableLiveData<WalletInfo> getUserWalletInfo(){
        return userWalletInfo;
    }

    @SuppressLint("CheckResult")
    void getCustomerInformation(CustomerInfoBackend customerInfoBackend){
        customerInfoBackend
                .getProfileInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<ProfileInfo, ObservableSource<WalletInfo>>() {
                    @Override
                    public ObservableSource<WalletInfo> apply(ProfileInfo profileInfo) throws Exception {
                        Log.e("in flatMap : ", profileInfo.toString());
                        userProfileInfo.postValue(profileInfo);
                        return customerInfoBackend.getWalletInfo();
                    }
                })
                .subscribe(new Observer<WalletInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(WalletInfo walletInfo) {
                        Log.e("onNext : ", walletInfo.toString());
                        userWalletInfo.postValue(walletInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError : ", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

/*    @SuppressLint("CheckResult")
    void zipOperator(CustomerInfoBackend customerInfoBackend){
        customerInfoBackend
                .getProfileInfo()
                .zipWith(customerInfoBackend.getWalletInfo(),
                        (o1, o2) -> {
                            Log.e("object o1->", o1.toString());
                            Log.e("object o2->", o2.toString());

                            return "zip return";
                        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        int i =10;
                        Log.e("onNext : ", s.toString());
                        userWalletInfo.postValue(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("onError : ", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete : ", "");
                    }
                });
    }*/
}


