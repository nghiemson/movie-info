package com.example.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviemvvm.data.api.TMDBInterface
import com.example.moviemvvm.data.value_object.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailsNetworkDataSource(private val apiService: TMDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieDetailsResponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                    apiService.getMovieDetails(movieId)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        _downloadedMovieDetailsResponse.postValue(it)
                                        _networkState.postValue(NetworkState.LOADED)
                                    },
                                    {
                                        _networkState.postValue(NetworkState.ERROR)
                                        it.message?.let { it1 -> Log.e(MovieDetailsNetworkDataSource::class.simpleName, it1) }
                                    }
                            )
            )
        }
        catch (e: Exception) {
            e.message?.let { e1 -> Log.e(MovieDetailsNetworkDataSource::class.simpleName, e1) }
        }
    }
}