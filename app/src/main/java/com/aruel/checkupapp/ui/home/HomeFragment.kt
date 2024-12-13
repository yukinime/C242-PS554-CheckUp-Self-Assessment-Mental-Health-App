package com.aruel.checkupapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aruel.checkupapp.data.factory.ViewModelFactory
import com.aruel.checkupapp.data.utils.NetworkUtils
import com.aruel.checkupapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val predictionResult = "depresi"
        if (NetworkUtils.isInternetAvailable(requireContext())) {
            homeViewModel.fetchArticles(predictionResult)
            observeArticles()
        } else {
            Toast.makeText(
                requireContext(),
                "Tidak ada koneksi internet. Tidak dapat memuat artikel.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun observeArticles() {
        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenStarted {
            homeViewModel.articles.collectLatest { response ->
                if (response != null) {
                    val articles = response.data ?: emptyList()
                    val adapter = ArticleAdapter(articles)
                    binding.rvStories.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvStories.adapter = adapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
