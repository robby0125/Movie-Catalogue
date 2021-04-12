package com.robby.moviecatalogue.ui.search

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.robby.moviecatalogue.databinding.ActivitySearchBinding
import com.robby.moviecatalogue.ui.content.ContentsAdapter

class SearchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUERY = "extra_query"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val query = intent.getStringExtra(EXTRA_QUERY)
        val type = intent.getIntExtra(EXTRA_TYPE, 0)

        if (query != null) {
            val viewModel: SearchViewModel by viewModels()
            val contentResult = viewModel.getContentResult(query, type)

            if (contentResult.size > 0) {
                binding.tvEmptyResult.visibility = View.GONE

                val adapter = ContentsAdapter()
                adapter.setListContents(contentResult)

                with(binding.rvSearchResult) {
                    layoutManager = LinearLayoutManager(this@SearchActivity)
                    setHasFixedSize(true)
                    this.adapter = adapter
                }
            } else binding.rvSearchResult.visibility = View.GONE
        }

        supportActionBar?.title = "Search Result"
    }
}