package com.halodoc.testproject.problems.news.presenter;

import android.util.Log;

import com.halodoc.testproject.problems.news.model.Hit;
import com.halodoc.testproject.problems.news.model.NewsResponse;
import com.halodoc.testproject.problems.news.network.NewsDataSource;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hendry on 27/01/19.
 */
public class NewsPresenter {

    private CompositeDisposable composite = new CompositeDisposable();

    private View view;

    public interface View {
        void onSuccessGetNews(List<Hit> articleList);

        void onErrorGetNews(Throwable throwable);
    }

    public NewsPresenter(NewsPresenter.View view) {
        this.view = view;
    }

    public void getEverything(String keyword) {
        Log.i("keyword",keyword);

        NewsDataSource.getService().getNews(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        composite.add(d);
                    }

                    @Override
                    public void onNext(NewsResponse newsResult) {

                        view.onSuccessGetNews(newsResult.getHits());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onErrorGetNews(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void unsubscribe() {
        composite.dispose();
    }

}
