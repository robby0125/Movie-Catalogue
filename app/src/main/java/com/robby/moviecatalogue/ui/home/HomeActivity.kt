package com.robby.moviecatalogue.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.robby.moviecatalogue.R
import com.robby.moviecatalogue.databinding.ActivityHomeBinding
import com.robby.moviecatalogue.ui.search.SearchActivity
import com.robby.moviecatalogue.utils.ContentType

class HomeActivity : AppCompatActivity() {

    companion object {
        private val TABS_TITLE = intArrayOf(R.string.movies, R.string.tvshows)
    }

    private var curTabIndex = 0

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabs = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = TabsAdapter(this)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TABS_TITLE[position])
        }.attach()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                curTabIndex = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            maxWidth = Integer.MAX_VALUE
            queryHint = "Search here..."
            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    val searchType = if (curTabIndex == 0) ContentType.MOVIE else ContentType.TV

                    val searchIntent = Intent(this@HomeActivity, SearchActivity::class.java)
                    searchIntent.putExtra(SearchActivity.EXTRA_QUERY, query)
                    searchIntent.putExtra(SearchActivity.EXTRA_TYPE, searchType)
                    startActivity(searchIntent)

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        return true
    }
}