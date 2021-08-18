package com.example.wonhoi_moive_review_service_app.presentation.mypage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wonhoi_moive_review_service_app.databinding.FragmentMyPageBinding
import com.example.wonhoi_moive_review_service_app.domain.model.ReviewedMovie
import com.example.wonhoi_moive_review_service_app.extension.dip
import com.example.wonhoi_moive_review_service_app.extension.toGone
import com.example.wonhoi_moive_review_service_app.extension.toVisible
import com.example.wonhoi_moive_review_service_app.presentation.home.GridSpacingItemDecoration
import org.koin.android.scope.ScopeFragment

class MyPageFragment : ScopeFragment(), MyPageContract.View {

    override val presenter: MyPageContract.Presenter by inject()

    private var binding: FragmentMyPageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyPageBinding.inflate(inflater, container, false)
        .also {
            binding = it
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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

    override fun showNoDataDescription(message: String) {
        binding?.recyclerView?.toGone()
        binding?.descriptionTextView?.toVisible()
        binding?.descriptionTextView?.text = message
    }

    override fun showErrorDescription(message: String) {
        binding?.recyclerView?.toGone()
        binding?.descriptionTextView?.toVisible()
        binding?.descriptionTextView?.text = message
    }

    override fun showReviewdMovies(reviewedMovies: List<ReviewedMovie>) {
        (binding?.recyclerView?.adapter as? MyPageAdapter)?.apply {
            this.reviewedMovies = reviewedMovies
            notifyDataSetChanged()
        }
    }

    private fun initView() {
        binding?.recyclerView?.apply {
            adapter = MyPageAdapter()
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            addItemDecoration(GridSpacingItemDecoration(3, dip(6f)))
        }
    }

    private fun bindView() {
        (binding?.recyclerView?.adapter as? MyPageAdapter)?.apply {
            onMovieClickListener = { movie ->
                val action = MyPageFragmentDirections.toMovieReviewsAction(movie)
                findNavController().navigate(action)
            }
        }
    }
}