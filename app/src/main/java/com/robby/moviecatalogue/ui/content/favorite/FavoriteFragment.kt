package com.robby.moviecatalogue.ui.content.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.robby.moviecatalogue.R
import com.robby.moviecatalogue.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    companion object {
        private val TABS_TITLE = intArrayOf(R.string.movies, R.string.tvshows)
    }

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = TabsAdapter(this)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TABS_TITLE[position])
        }.attach()
    }
}