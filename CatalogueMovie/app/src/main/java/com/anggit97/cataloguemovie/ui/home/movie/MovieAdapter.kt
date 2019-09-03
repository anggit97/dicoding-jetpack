package com.anggit97.cataloguemovie.ui.home.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.cataloguemovie.BuildConfig
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.ui.detail.DetailMovieActivity
import com.anggit97.cataloguemovie.ui.detail.EXTRA_ID
import com.anggit97.cataloguemovie.ui.detail.EXTRA_TYPE
import com.anggit97.cataloguemovie.utils.showImageRounded
import kotlinx.android.synthetic.main.row_item_movie.view.*
import org.jetbrains.anko.startActivity

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movieList: MutableList<ResultMovieEntity> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(model: ResultMovieEntity) {
            with(itemView) {
                tv_title_movies.text = model.title
                tv_subtitle_movies.text = model.genre
                tv_subtitle_imdb_scroe.text = model.voteAverage.toString()
                model.backdropPath?.let {
                    iv_movies.showImageRounded(
                        itemView.context,
                        BuildConfig.BASE_URL_IMAGE.plus(it)
                    )
                }
            }

            itemView.setOnClickListener {
                itemView.context.startActivity<DetailMovieActivity>(
                    EXTRA_ID to model.id,
                    EXTRA_TYPE to 1
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(movieList[position])
    }

    fun setMovieList(movieList: MutableList<ResultMovieEntity>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }
}