package com.robby.moviecatalogue.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.robby.moviecatalogue.R
import com.robby.moviecatalogue.databinding.ActivityHomeBinding
import com.robby.moviecatalogue.ui.content.favorite.FavoriteFragment
import com.robby.moviecatalogue.ui.content.movie.MovieListFragment
import com.robby.moviecatalogue.ui.content.tvshow.TvShowListFragment
import com.robby.moviecatalogue.ui.search.SearchActivity
import com.robby.moviecatalogue.utils.ContentType

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        commitFragment(MovieListFragment())

        binding.navView.selectedItemId = R.id.navigation_movie
        binding.navView.setOnNavigationItemSelectedListener { item ->
            var fragment = Fragment()

            when (item.itemId) {
                R.id.navigation_movie -> {
                    searchView.visibility = View.VISIBLE
                    fragment = MovieListFragment()
                }
                R.id.navigation_tv -> {
                    searchView.visibility = View.VISIBLE
                    fragment = TvShowListFragment()
                }
                R.id.navigation_favorite -> {
                    searchView.setQuery("", false)
                    searchView.clearFocus()
                    searchView.isIconified = true
                    searchView.visibility = View.INVISIBLE
                    fragment = FavoriteFragment()
                }
            }

            commitFragment(fragment)

            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            maxWidth = Integer.MAX_VALUE
            queryHint = "Search here..."
            setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    val searchType =
                        if (binding.navView.selectedItemId == R.id.navigation_movie) ContentType.MOVIE else ContentType.TV

                    val searchIntent =
                        Intent(this@HomeActivity, SearchActivity::class.java)
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

    private fun commitFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frame_container, fragment)
        }
    }
}