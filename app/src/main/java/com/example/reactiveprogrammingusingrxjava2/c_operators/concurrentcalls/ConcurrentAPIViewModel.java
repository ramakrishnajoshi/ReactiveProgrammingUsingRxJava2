package com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls;
import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class ConcurrentAPIViewModel extends ViewModel {

    private MutableLiveData<Object> userProfileInfo = new MutableLiveData<>();
    public MutableLiveData<Object> userWalletInfo = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    void getCustomerInformation(CustomerInfoBackend customerInfoBackend){
        customerInfoBackend
                .getProfileInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Object, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(Object o) throws Exception {
                        Log.e("in flatMap : ", o.toString());
                        return customerInfoBackend.getWalletInfo();
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e("onNext : ", o.toString());
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

    @SuppressLint("CheckResult")
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


        /*Observable.zip(customerInfoBackend.getWalletInfo(),
                customerInfoBackend.getProfileInfo(), new BiFunction<Object, Object, String>() {
                    @Override
                    public String apply(Object o1, Object o2) throws Exception {
                        if (o1 != null){
                            Log.e("Object o1", o1.toString());
                        }else {
                            Log.e("Object o1", null);
                        }
                        if (o2 != null){
                            Log.e("Object o2", o2.toString());
                        }else {
                            Log.e("Object o2", null);
                        }

                        return "zip return";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("onNext : ", s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete : ", "");
                    }
                });*/
    }
}


