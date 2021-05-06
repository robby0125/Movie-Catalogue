package com.robby.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.robby.moviecatalogue.R
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
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
    private lateinit var detailLiveData: LiveData<ContentEntity>

    private val viewModel: DetailViewModel by inject()
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Content Detail"

        val contentQuery = intent.getStringExtra(EXTRA_QUERY)
        val contentId = intent.getIntExtra(EXTRA_CONTENT_ID, 0)
        val contentType = intent.getSerializableExtra(EXTRA_TYPE) as ContentType

        viewModel.setContentType(contentType)
        viewModel.setSelectedContentID(contentId)

        contentQuery?.let { viewModel.setQuery(it) }

        binding.progressBar.visibility = View.VISIBLE
        binding.detailContent.visibility = View.INVISIBLE

        detailLiveData =
            if (contentType == ContentType.MOVIE) viewModel.movieDetail else viewModel.tvShowDetail

        detailLiveData.observe(this, {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                binding.detailContent.visibility = View.VISIBLE
                populateContent(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        detailLiveData.observe(this, {
            setFavoriteState(it.isFavorite)
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            setFavoriteState(viewModel.setFavorite())
            return true
        }
        return super.onOptionsItemSelected(item)
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

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return

        val menuItem = menu?.findItem(R.id.action_favorite)
        menuItem?.icon = ContextCompat.getDrawable(
            this,
            if (state) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )
    }
}