package com.example.moviemvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviemvvm.data.api.TMDBInterface
import com.example.moviemvvm.data.value_object.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: TMDBInterface, private val compositeDisposable: CompositeDisposable): DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource

    }

}