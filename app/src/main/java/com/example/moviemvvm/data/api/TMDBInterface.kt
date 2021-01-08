package com.example.moviemvvm.data.api

import com.example.moviemvvm.data.value_object.MovieDetails
import com.example.moviemvvm.data.value_object.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBInterface {
    //https://api.themoviedb.org/3/movie/464052?api_key=4fe6f6c23e47c228527826ae7f7e0da2
    //https://api.themoviedb.org/3/movie/popular?api_key=4fe6f6c23e47c228527826ae7f7e0da2

    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id: Int): Single<MovieDetails>
}