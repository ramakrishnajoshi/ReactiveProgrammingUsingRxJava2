package com.example.reactiveprogrammingusingrxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxConcept1Just {

    public static void main(String[] args) {

        justOperatorWorking();

        //rangeOperatorWorking();
    }

    private static void rangeOperatorWorking() {
        Observable<Integer> observable = Observable.range(5,3);
        Observer observer = new Observer() {

            Disposable disposable = new Disposable() {
                @Override
                public void dispose() {
                }

                @Override
                public boolean isDisposed() {
                    return false;
                }
            };

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                System.out.println("RxConcept1Just.onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("RxConcept1Just.onNext");
                System.out.println("o = [" + o + "]");
                System.out.println("onNext: d.isDisposed() :" + disposable.isDisposed()); //returns false
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                System.out.println("RxConcept1Just.onComplete");
                System.out.println("d.isDisposed() :" + disposable.isDisposed()); //returns true
            }
        };

        observable.subscribe(observer);
    }

    private static void justOperatorWorking() {
        Observable<Integer> observable = Observable.just(1,2,3,4);

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Object o) {
                System.out.println("o = [" + o + "]");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        observable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
