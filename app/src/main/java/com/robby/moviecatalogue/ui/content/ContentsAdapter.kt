package com.robby.moviecatalogue.ui.content

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.robby.moviecatalogue.R
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.databinding.ItemCardLayoutBinding
import com.robby.moviecatalogue.ui.detail.DetailActivity
import com.robby.moviecatalogue.utils.ContentType

class ContentsAdapter(private val contentType: ContentType) :
    PagedListAdapter<ContentEntity, ContentsAdapter.ContentViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContentEntity>() {
            override fun areItemsTheSame(oldItem: ContentEntity, newItem: ContentEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ContentEntity,
                newItem: ContentEntity
            ): Boolean = oldItem == newItem

        }
    }

    private var query = ""

    fun setQuery(query: String) {
        this.query = query
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding =
            ItemCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val content = getItem(position)
        if (content != null) {
            holder.bind(content)
        }
    }

    inner class ContentViewHolder(private val binding: ItemCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(content: ContentEntity) {
            with(binding) {
                tvTitle.text = content.title
                tvGenre.text = content.genre
                tvDate.text = content.releaseDate
                tvRate.text = content.voteAverage.toString()

                val circularProgress = CircularProgressDrawable(itemView.context)
                circularProgress.strokeWidth = 16f
                circularProgress.centerRadius = 64f
                circularProgress.start()

                Glide.with(itemView.context)
                    .load(content.posterPath)
                    .apply(RequestOptions.placeholderOf(circularProgress))
                    .error(R.drawable.ic_error)
                    .into(imgPoster)

                itemView.setOnClickListener {
                    val detailIntent = Intent(it.context, DetailActivity::class.java)
                    detailIntent.putExtra(DetailActivity.EXTRA_QUERY, query)
                    detailIntent.putExtra(DetailActivity.EXTRA_CONTENT_ID, content.id)
                    detailIntent.putExtra(DetailActivity.EXTRA_TYPE, contentType)
                    it.context.startActivity(detailIntent)
                }
            }
        }
    }
}
