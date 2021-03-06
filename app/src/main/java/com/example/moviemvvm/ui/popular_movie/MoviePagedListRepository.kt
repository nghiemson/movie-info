package com.example.moviemvvm.ui.popular_movie

import android.graphics.pdf.PdfDocument
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviemvvm.data.api.POST_PER_PAGE
import com.example.moviemvvm.data.api.TMDBInterface
import com.example.moviemvvm.data.repository.MovieDataSource
import com.example.moviemvvm.data.repository.MovieDataSourceFactory
import com.example.moviemvvm.data.repository.NetworkState
import com.example.moviemvvm.data.value_object.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: TMDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config= PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(POST_PER_PAGE)
                .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
                movieDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}