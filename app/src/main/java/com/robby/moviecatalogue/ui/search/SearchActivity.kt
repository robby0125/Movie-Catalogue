package com.robby.moviecatalogue.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.robby.moviecatalogue.databinding.ActivitySearchBinding
import com.robby.moviecatalogue.ui.content.ContentsAdapter
import com.robby.moviecatalogue.utils.ContentType
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUERY = "extra_query"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvSearchResult.visibility = View.INVISIBLE
        binding.tvEmptyResult.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        val query = intent.getStringExtra(EXTRA_QUERY)
        val type = intent.getSerializableExtra(EXTRA_TYPE) as ContentType

        val adapter = ContentsAdapter(type)

        with(binding.rvSearchResult) {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            setHasFixedSize(true)
            this.adapter = adapter
        }

        if (query != null) {
            val viewModel: SearchViewModel by inject()
            viewModel.setQueryAndType(query, type)

            viewModel.getSearchResult().observe(this, {
                binding.rvSearchResult.visibility = View.VISIBLE
                binding.tvEmptyResult.visibility = View.GONE
                binding.progressBar.visibility = View.GONE

                adapter.setListContents(it)
                adapter.notifyDataSetChanged()
            })
        }

        supportActionBar?.title = "Search Result"
    }
}