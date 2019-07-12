package com.halodoc.testproject.problems.news.network;

import com.halodoc.testproject.problems.news.model.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("search")
    Observable<NewsResponse> getNews(@Query("query") String query);
}
