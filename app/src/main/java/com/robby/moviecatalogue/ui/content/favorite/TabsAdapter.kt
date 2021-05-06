package com.robby.moviecatalogue.ui.content.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.robby.moviecatalogue.ui.content.favorite.movie.FavoriteMovieListFragment
import com.robby.moviecatalogue.ui.content.favorite.tvshow.FavoriteTvShowListFragment

class TabsAdapter(f: Fragment) : FragmentStateAdapter(f) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> FavoriteMovieListFragment()
        1 -> FavoriteTvShowListFragment()
        else -> Fragment()
    }
}