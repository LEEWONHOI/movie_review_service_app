package com.example.wonhoi_moive_review_service_app.presentation.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wonhoi_moive_review_service_app.databinding.FragmentHomeBinding
import com.example.wonhoi_moive_review_service_app.domain.model.FeaturedMovie
import com.example.wonhoi_moive_review_service_app.domain.model.Movie
import com.example.wonhoi_moive_review_service_app.extension.dip
import com.example.wonhoi_moive_review_service_app.extension.toGone
import com.example.wonhoi_moive_review_service_app.extension.toVisible
import com.example.wonhoi_moive_review_service_app.presentation.home.HomeAdapter.Companion.ITEM_VIEW_TYPE_FEATURED
import com.example.wonhoi_moive_review_service_app.presentation.home.HomeAdapter.Companion.ITEM_VIEW_TYPE_SECTION_HEADER
import org.koin.android.scope.ScopeFragment

class HomeFragment : ScopeFragment(), HomeContract.View {

    private var binding: FragmentHomeBinding? = null

    override val presenter: HomeContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false)
        .also {
            binding = it
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindView()
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoadingIndicator() {
        binding?.progressBar?.toVisible()
    }

    override fun hideLoadingIndicator() {
        binding?.progressBar?.toGone()
    }

    override fun showErrorDescription(message: String) {
        binding?.recyclerView?.toGone()
        binding?.errorDescriptionTextView?.toVisible()
        binding?.errorDescriptionTextView?.text = message
    }

    override fun showMovies(featuredMovie: FeaturedMovie?, movies: List<Movie>) {
        binding?.recyclerView?.toVisible()
        binding?.errorDescriptionTextView?.toGone()
        (binding?.recyclerView?.adapter as? HomeAdapter)?.run {
            addData(featuredMovie, movies)
            notifyDataSetChanged()
        }
    }

    private fun initViews() {
        binding?.recyclerView?.apply {
            adapter = HomeAdapter()
            val gridLayoutManager = createGridLayoutManager()
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(gridLayoutManager.spanCount, dip(6f)))
        }
    }

    private fun bindView() {
        (binding?.recyclerView?.adapter as? HomeAdapter)?.apply {
            onMovieClickListener = { movie ->
                val action = HomeFragmentDirections.toMovieReviewsAction(movie)
                findNavController().navigate(action)
            }
        }
    }

    private fun RecyclerView.createGridLayoutManager(): GridLayoutManager =
        GridLayoutManager(context, 3, RecyclerView.VERTICAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (adapter?.getItemViewType(position)) {
                        ITEM_VIEW_TYPE_SECTION_HEADER,
                        ITEM_VIEW_TYPE_FEATURED -> {
                            spanCount   // 3 칸의 사이즈를 전부 쓰겠다.
                        }
                        else -> {
                            1
                        }
                    }
            }
        }
}