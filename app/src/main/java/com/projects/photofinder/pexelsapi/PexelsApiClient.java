package com.projects.photofinder.pexelsapi;

import com.projects.photofinder.datamodels.Result;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PexelsApiClient {

    @Headers("Authorization: 563492ad6f917000010000014b951a5008404c1e9e5dcf4dceb5af3f")
    @GET("search?")
    Flowable<Result> getResults(@Query("query") String search, @Query("per_page") int per_page, @Query("page") int page);
}
