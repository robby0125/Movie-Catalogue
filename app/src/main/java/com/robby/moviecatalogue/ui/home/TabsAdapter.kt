package com.robby.moviecatalogue.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.robby.moviecatalogue.ui.content.movie.MovieListFragment
import com.robby.moviecatalogue.ui.content.tvshow.TvShowListFragment

class TabsAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MovieListFragment()
        1 -> TvShowListFragment()
        else -> Fragment()
    }
}