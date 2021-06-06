package com.ronak.movietray.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ronak.movietray.data.Resource
import com.ronak.movietray.databinding.FragmentHomeBinding
import com.ronak.movietray.dto.MoviesData
import com.ronak.movietray.ui.home.adapter.MoviesAdapter
import com.ronak.movietray.ui.home.adapter.MoviesGridAdapter
import com.ronak.movietray.utils.observe
import com.ronak.movietray.viewModel.MoviesViewModel
import kotlin.math.abs


const val SUCCESS = 0
const val LOADING = 1
const val ERROR = 2

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesGridAdapter: MoviesGridAdapter
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun handleMoviesList(status: Resource<MoviesData>) {
        when (status) {
            is Resource.Loading -> showView(LOADING)
            is Resource.Success -> status.data?.let { bindListData(moviesData = it) }
            is Resource.DataError -> {
                showView(ERROR)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(moviesViewModel.moviesLiveData, ::handleMoviesList)
        binding.viewFlipper.displayedChild = 1
    }

    private fun bindListData(moviesData: MoviesData) {
        if (!(moviesData.results.isNullOrEmpty())) {
            moviesAdapter = MoviesAdapter(moviesViewModel, moviesData.results)
            moviesGridAdapter = MoviesGridAdapter(moviesViewModel, moviesData.results)
            binding.rvHorizontalMovies.adapter = moviesAdapter
            binding.rvGridMovies.adapter = moviesGridAdapter
            val viewPager2: ViewPager2 = binding.rvHorizontalMovies

            viewPager2.clipToPadding = false
            viewPager2.clipChildren = false
            viewPager2.offscreenPageLimit = 3
            viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 1f + r * 0.15f
            }
            viewPager2.setPageTransformer(compositePageTransformer)

            showView(SUCCESS)
        } else {
            showView(ERROR)
        }
    }

    private fun showView(state: Int) {
        val viewFlipper = binding.viewFlipper
        if (viewFlipper.childCount > state && viewFlipper.displayedChild != state) {
            viewFlipper.displayedChild = state
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}