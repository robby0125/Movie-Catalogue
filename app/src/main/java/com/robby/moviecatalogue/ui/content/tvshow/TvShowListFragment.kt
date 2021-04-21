package com.robby.moviecatalogue.ui.content.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.robby.moviecatalogue.databinding.FragmentTvShowListBinding
import com.robby.moviecatalogue.ui.content.ContentsAdapter
import com.robby.moviecatalogue.ui.content.ContentsViewModel
import com.robby.moviecatalogue.utils.ContentType
import org.koin.android.ext.android.inject

class TvShowListFragment : Fragment() {

    private lateinit var binding: FragmentTvShowListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: ContentsViewModel by inject()
        val adapter = ContentsAdapter(ContentType.TV)

        binding.rvTvShows.visibility = View.INVISIBLE
        binding.noDataLayout.root.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        viewModel.getTvDiscover().observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE

            if (it.isNotEmpty()) {
                binding.rvTvShows.visibility = View.VISIBLE

                adapter.setListContents(it)
                adapter.notifyDataSetChanged()
            } else {
                binding.noDataLayout.root.visibility = View.VISIBLE
            }
        })

        with(binding.rvTvShows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }
}