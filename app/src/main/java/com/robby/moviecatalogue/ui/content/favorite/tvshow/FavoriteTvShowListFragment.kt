package com.robby.moviecatalogue.ui.content.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.robby.moviecatalogue.databinding.FragmentFavoriteTvShowListBinding
import com.robby.moviecatalogue.ui.content.ContentsAdapter
import com.robby.moviecatalogue.ui.content.ContentsViewModel
import com.robby.moviecatalogue.utils.ContentType
import org.koin.android.ext.android.inject

class FavoriteTvShowListFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteTvShowListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTvShowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: ContentsViewModel by inject()

        viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, { tvShows ->
            if (tvShows == null || tvShows.isEmpty()) {
                binding.noDataLayout.root.visibility = View.VISIBLE
            } else {
                binding.noDataLayout.root.visibility = View.INVISIBLE

                val adapter = ContentsAdapter(ContentType.TV)
                adapter.submitList(tvShows)

                with(binding.rvTvShows) {
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    this.adapter = adapter
                }
            }
        })
    }
}