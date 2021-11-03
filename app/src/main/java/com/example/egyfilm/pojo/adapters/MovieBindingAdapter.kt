package com.example.egyfilm.pojo.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.egyfilm.pojo.Constants
import com.example.egyfilm.pojo.classes.*
import java.math.RoundingMode


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
    text = movie?.voteAverage?.toBigDecimal()?.setScale(1,RoundingMode.UP)?.toDouble().toString()
}


@BindingAdapter("setMovieImageFromTMDB")
fun ImageView.setImageFromTMDB(movie: MovieFullData?) {
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

@BindingAdapter(value = ["itemsMap", "itemType", "listener"], requireAll = false)
fun RecyclerView.itemsMap(
    movies: Movies?,
    itemType: Int?,
    listener: MoviesAdapter.OnMovieItemClickListener
) {
    adapter = MoviesAdapter(listener)
    (adapter as MoviesAdapter).type = itemType
    (adapter as MoviesAdapter).submitList(movies?.results)
}

@BindingAdapter("setRecyclerViewTitle")
fun TextView.setRecyclerViewTitle(recName: String?) {
    text = recName
}


@BindingAdapter("setMainRecyclerViewItems")
fun RecyclerView.setMainRecyclerViewItems(items: HashMap<String, Movies>?) {
    val lst = items?.toList()
    (adapter as RecAdapter).submitList(lst)
}


@BindingAdapter("setMovieRatingColor")
fun FrameLayout.setMovieRatingColor(movie: Movie?) {
    backgroundTintList = when {
        movie?.voteAverage!! >= 7.5 -> ColorStateList.valueOf(Color.GREEN)
        movie.voteAverage >= 5 -> ColorStateList.valueOf(Color.YELLOW)
        else -> ColorStateList.valueOf(Color.RED)
    }
}

@BindingAdapter("setMovieBackImageFromTMDB")
fun ImageView.setMovieBackImageFromTMDB(movie: MovieFullData?) {
    if (movie?.backdropPath != null)
        Glide.with(context).load(Constants.IMG_BASE_URL + movie.backdropPath).into(this)
    else
        Glide.with(context).load(Constants.IMG_BASE_URL + movie?.posterPath).into(this)
}

@BindingAdapter("setMovieDescription")
fun TextView.setMovieDescription(movie: MovieFullData?) {
    text = movie?.overview
}

@BindingAdapter("setMovieTitle")
fun TextView.setMovieTitle(movie: MovieFullData?) {
    text = movie?.title
}

@BindingAdapter("setRelativeRecyclerViewItems")
fun RecyclerView.setRelativeRecyclerViewItems(relative: MovieRelative?) {
    if (relative?.results != null)
        (adapter as MoviesAdapter).submitList(relative.results)
}

@BindingAdapter("setActorsRecyclerViewItems")
fun RecyclerView.setActorsRecyclerViewItems(actors: MovieActors?){
    if (actors?.cast != null)
        (adapter as ActorsAdapter).submitList(actors.cast)
}

@BindingAdapter("setActorImage")
fun ImageView.setActorImage(actor: Actor?){
    Glide.with(this).load(Constants.IMG_BASE_URL + actor?.profilePath).into(this)
}

@BindingAdapter("setActorName")
fun TextView.setActorName(actor: Actor?){
     text = actor?.character
}

@BindingAdapter("setActorRealName")
fun TextView.setActorRealName(actor: Actor?){
    text = actor?.originalName
}