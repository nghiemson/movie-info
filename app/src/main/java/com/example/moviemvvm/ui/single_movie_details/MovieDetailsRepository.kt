package com.example.moviemvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.example.moviemvvm.data.api.TMDBInterface
import com.example.moviemvvm.data.repository.MovieDetailsNetworkDataSource
import com.example.moviemvvm.data.repository.NetworkState
import com.example.moviemvvm.data.value_object.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TMDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource
    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}