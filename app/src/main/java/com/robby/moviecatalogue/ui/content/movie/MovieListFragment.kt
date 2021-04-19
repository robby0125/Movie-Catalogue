package com.robby.moviecatalogue.ui.content.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.robby.moviecatalogue.databinding.FragmentMovieListBinding
import com.robby.moviecatalogue.ui.content.ContentsAdapter
import com.robby.moviecatalogue.ui.content.ContentsViewModel
import com.robby.moviecatalogue.utils.ContentType
import org.koin.android.ext.android.inject

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: ContentsViewModel by inject()
        val adapter = ContentsAdapter(ContentType.MOVIE)

        binding.rvMovies.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        viewModel.getMovieDiscover().observe(viewLifecycleOwner, {
            binding.rvMovies.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

            adapter.setListContents(it)
            adapter.notifyDataSetChanged()
        })

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }
}