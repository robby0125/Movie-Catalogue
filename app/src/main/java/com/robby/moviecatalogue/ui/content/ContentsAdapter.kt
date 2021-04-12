package com.robby.moviecatalogue.ui.content

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.robby.moviecatalogue.R
import com.robby.moviecatalogue.data.Content
import com.robby.moviecatalogue.databinding.ItemCardLayoutBinding
import com.robby.moviecatalogue.ui.detail.DetailActivity

class ContentsAdapter : RecyclerView.Adapter<ContentsAdapter.ContentViewHolder>() {

    private val listContents = ArrayList<Content>()

    fun setListContents(contents: ArrayList<Content>) {
        listContents.clear()
        listContents.addAll(contents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding =
            ItemCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(listContents[position])
    }

    override fun getItemCount(): Int = listContents.size

    inner class ContentViewHolder(private val binding: ItemCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(content: Content) {
            with(binding) {
                tvTitle.text = content.title
                tvGenre.text = content.genres
                tvDate.text = content.release_date
                tvRate.text = content.rate

                val circularProgress = CircularProgressDrawable(itemView.context)
                circularProgress.strokeWidth = 16f
                circularProgress.centerRadius = 64f
                circularProgress.start()

                Glide.with(itemView.context)
                    .load(content.poster_path)
                    .apply(RequestOptions.placeholderOf(circularProgress))
                    .error(R.drawable.ic_error)
                    .into(imgPoster)

                itemView.setOnClickListener {
                    val detailIntent = Intent(it.context, DetailActivity::class.java)
                    detailIntent.putExtra(DetailActivity.EXTRA_CONTENT_ID, content.id)
                    it.context.startActivity(detailIntent)
                }
            }
        }
    }
}
