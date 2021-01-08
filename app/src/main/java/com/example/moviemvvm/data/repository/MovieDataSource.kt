package com.example.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviemvvm.data.api.FIRST_PAGE
import com.example.moviemvvm.data.api.TMDBInterface
import com.example.moviemvvm.data.value_object.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDataSource(private val apiService: TMDBInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
                apiService.getPopularMovie(page)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    callback.onResult(it.movieList, null, page + 1)
                                    networkState.postValue(NetworkState.LOADED)
                                },
                                {
                                    networkState.postValue(NetworkState.ERROR)
                                    it.message?.let { it1 -> Log.e("MovieDataSource", it1) }
                                }
                        )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
                apiService.getPopularMovie(params.key)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                   if(it.totalPages >= params.key) {
                                       callback.onResult(it.movieList, params.key+1)
                                       networkState.postValue(NetworkState.LOADED)
                                   }
                                    else {
                                        networkState.postValue(NetworkState.ENDOFLIST)
                                   }
                                },
                                {
                                    networkState.postValue(NetworkState.ERROR)
                                    it.message?.let { it1 -> Log.e("MovieDataSource", it1) }
                                }
                        )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}