package com.robby.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.robby.moviecatalogue.R
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.databinding.ActivityDetailBinding
import com.robby.moviecatalogue.utils.ContentType
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUERY = "extra_query"
        const val EXTRA_CONTENT_ID = "extra_content_id"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Content Detail"

        val contentQuery = intent.getStringExtra(EXTRA_QUERY)
        val contentId = intent.getIntExtra(EXTRA_CONTENT_ID, 0)
        val contentType = intent.getSerializableExtra(EXTRA_TYPE) as ContentType

        val viewModel: DetailViewModel by inject()
        viewModel.setSelectedContentID(contentId)

        binding.progressBar.visibility = View.VISIBLE
        binding.detailContent.visibility = View.INVISIBLE

        val getDetail: LiveData<ContentEntity> =
            if (contentQuery != null && contentQuery.isNotEmpty()) {
                if (contentType == ContentType.MOVIE) viewModel.getMovieDetailWithQuery() else viewModel.getTvDetailWithQuery()
            } else {
                if (contentType == ContentType.MOVIE) viewModel.getMovieDetail() else viewModel.getTvDetail()
            }

        getDetail.observe(this, {
            binding.progressBar.visibility = View.GONE
            binding.detailContent.visibility = View.VISIBLE
            populateContent(it)
        })
    }

    private fun populateContent(contentEntity: ContentEntity) {
        with(binding) {

            tvTitle.text = contentEntity.title
            tvGenre.text = contentEntity.genre
            tvRate.text = contentEntity.voteAverage.toString()
            tvDate.text = contentEntity.releaseDate
            tvLanguage.text = contentEntity.originalLanguage
            tvOverview.text = contentEntity.overview

            val circularProgress = CircularProgressDrawable(this@DetailActivity)
            circularProgress.strokeWidth = 16f
            circularProgress.centerRadius = 64f
            circularProgress.start()

            Glide.with(this@DetailActivity)
                .load(contentEntity.posterPath)
                .transform(RoundedCorners(25))
                .apply(RequestOptions.placeholderOf(circularProgress))
                .error(R.drawable.ic_error)
                .into(imgPoster)

            Glide.with(this@DetailActivity)
                .load(contentEntity.backdropPath)
                .apply(RequestOptions.placeholderOf(circularProgress))
                .error(R.drawable.ic_error)
                .into(imgBackdrop)
        }
    }
}