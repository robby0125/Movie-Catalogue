package com.robby.moviecatalogue.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.robby.moviecatalogue.R
import com.robby.moviecatalogue.data.Content
import com.robby.moviecatalogue.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CONTENT_ID = "extra_content_id"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Content Detail"

        val contentId = intent.getStringExtra(EXTRA_CONTENT_ID)
        if (contentId != null) {
            val viewModel: DetailViewModel by viewModels()
            viewModel.setSelectedContentID(contentId)

            populateContent(viewModel.getContent())
        }
    }

    private fun populateContent(content: Content) {
        with(binding) {
            tvTitle.text = content.title
            tvGenre.text = content.genres
            tvRate.text = content.rate
            tvDate.text = content.release_date
            tvStatus.text = content.status
            tvOverview.text = content.overview

            val circularProgress = CircularProgressDrawable(this@DetailActivity)
            circularProgress.strokeWidth = 16f
            circularProgress.centerRadius = 64f
            circularProgress.start()

            Glide.with(this@DetailActivity)
                .load(content.poster_path)
                .transform(RoundedCorners(25))
                .apply(RequestOptions.placeholderOf(circularProgress))
                .error(R.drawable.ic_error)
                .into(imgPoster)

            Glide.with(this@DetailActivity)
                .load(content.backdrop_path)
                .apply(RequestOptions.placeholderOf(circularProgress))
                .error(R.drawable.ic_error)
                .into(imgBackdrop)
        }
    }
}