package com.example.reactiveprogrammingusingrxjava2.e_flatmap.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.ApiComment;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.model.ApiPost;
import com.example.reactiveprogrammingusingrxjava2.e_flatmap.repository.UserPostsRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UserPostsViewModel extends ViewModel {

    CompositeDisposable disposable = new CompositeDisposable();

    UserPostsRepository repository = new UserPostsRepository();

    public MutableLiveData postsLiveData = new MutableLiveData<List<ApiPost>>();
    public MutableLiveData commentLiveData = new MutableLiveData<List<ApiComment>>();

    public void getPostsList() {
        getPosts()
            .flatMap(new Function<ApiPost, ObservableSource<ApiPost>>() {
                @Override
                public ObservableSource<ApiPost> apply(@NonNull ApiPost apiPost) throws Exception {
                    return getPostComments(apiPost.getPostId(), apiPost);
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<ApiPost>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ApiPost apiPost) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private Observable<ApiPost> getPosts() {
        return repository
            .getPosts()
            .flatMap(new Function<List<ApiPost>, ObservableSource<ApiPost>>() {
                @Override
                public ObservableSource<ApiPost> apply(@NonNull List<ApiPost> apiPosts) throws Exception {
                    postsLiveData.postValue(apiPosts);

                    Observable<ApiPost> apiPostObservable =
                        Observable
                            .fromIterable(apiPosts)
                            .subscribeOn(Schedulers.io());
                    return apiPostObservable;
                }
            });
    }

    //Observable<ApiComment> getPostComments(int postid) {
    Observable<ApiPost> getPostComments(int postid, ApiPost post) {
        return repository
            .getPostComments(postid)
            .subscribeOn(Schedulers.io())
            .map(new Function<List<ApiComment>, ApiPost>() {
                @Override
                public ApiPost apply(@NonNull List<ApiComment> apiComments) throws Exception {
                    commentLiveData.postValue(apiComments);
                    post.setCommentList(apiComments);
                    return post;
                }
            })
            .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
}
