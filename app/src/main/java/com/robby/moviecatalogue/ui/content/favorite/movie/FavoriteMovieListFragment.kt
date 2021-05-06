package com.robby.moviecatalogue.ui.content.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.robby.moviecatalogue.databinding.FragmentFavoriteMovieListBinding
import com.robby.moviecatalogue.ui.content.ContentsAdapter
import com.robby.moviecatalogue.ui.content.ContentsViewModel
import com.robby.moviecatalogue.utils.ContentType
import org.koin.android.ext.android.inject

class FavoriteMovieListFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: ContentsViewModel by inject()

        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { movies ->
            if (movies == null || movies.isEmpty()) {
                binding.noDataLayout.root.visibility = View.VISIBLE
            } else {
                binding.noDataLayout.root.visibility = View.INVISIBLE

                val adapter = ContentsAdapter(ContentType.MOVIE)
                adapter.submitList(movies)

                with(binding.rvMovies) {
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    this.adapter = adapter
                }
            }
        })
    }
}