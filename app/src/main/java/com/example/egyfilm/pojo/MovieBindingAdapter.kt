package com.example.egyfilm.pojo

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.egyfilm.pojo.classes.Movie
import com.example.egyfilm.pojo.classes.MovieFullData
import com.example.egyfilm.pojo.classes.Movies


@BindingAdapter("setMovieTitleFromTMDBForLargeItem")
fun TextView.setMovieTitleFromTMDBForLargeItem(movie: MovieFullData?) {
    text = movie?.title
}

@BindingAdapter("setMovieTitleFromTMDBForSmallItem")
fun TextView.setMovieTitleFromTMDBForSmallItem(movie: Movie?) {
    text = movie?.title
}

@BindingAdapter("setMovieRatingFromTMDBForLargeItem")
fun TextView.setMovieRatingFromTMDBForLargeItem(movie: Movie?) {
    text = "IMDB ${movie?.voteAverage}"
}

@BindingAdapter("setMovieRatingFromTMDBForSmallItem")
fun TextView.setMovieRatingFromTMDBForSmallItem(movie: Movie?) {
    text = movie?.voteAverage.toString()
}


@BindingAdapter("setMovieImageFromTMDBForLargeItem")
fun ImageView.setImageFromTMDBForLargeItem(movie: MovieFullData?) {
    Glide.with(context).load(Constants.IMG_BASE_URL + movie?.posterPath).into(this)
}


@BindingAdapter("setMovieImageFromTMDBForSmallItem")
fun ImageView.setImageFromTMDBForSmall(movie: Movie?) {
    Glide.with(context).load(Constants.IMG_BASE_URL + movie?.posterPath).into(this)
}

@BindingAdapter("setMoviesRecyclerViewAdapterItems")
fun RecyclerView.setMoviesRecyclerViewAdapterItems(movies: HashMap<String, Movies>?) {
    (adapter as MoviesAdapter).submitList(movies?.get("upcoming")?.results)
}

@BindingAdapter(value=["itemsMap","itemType"],requireAll = false)
fun RecyclerView.itemsMap(movies: Movies?, itemType : Int?){
    adapter = MoviesAdapter()
    (adapter as MoviesAdapter).type = itemType
    (adapter as MoviesAdapter).submitList(movies?.results)
}

@BindingAdapter("setRecyclerViewTitle")
fun TextView.setRecyclerViewTitle(recName : String?){
    text = recName
}


@BindingAdapter("setMainRecyclerViewItems")
fun RecyclerView.setMainRecyclerViewItems(items : HashMap<String,Movies>?){
    val lst  = items?.toList()
    (adapter as RecAdapter).submitList(lst)
}