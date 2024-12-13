package com.aruel.checkupapp.ui.dashboard

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aruel.checkupapp.R
import com.aruel.checkupapp.data.factory.ViewModelFactory
import com.aruel.checkupapp.data.utils.NetworkUtils
import com.aruel.checkupapp.databinding.FragmentDashboardBinding
import com.aruel.checkupapp.ui.about.AboutActivity
import com.aruel.checkupapp.ui.history.HistoryActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null


    private val binding get() = _binding!!

    private val dashboardViewModel: DashboardViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        binding.Mesagge.setOnClickListener {
            openWhatsApp()
        }

        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenStarted {
            dashboardViewModel.news.collect { articles ->
                articles.let {
                    adapter = NewsAdapter(it)
                    binding.rvStories.adapter = adapter
                }
            }
        }
        
        binding.consul.setOnClickListener {
            findNavController().navigate(R.id.navigation_checkup)
        }

        binding.setting.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.tips.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserSession()
    }

    private fun observeUserSession() {
        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenStarted {
            dashboardViewModel.getSession().collect { user ->
                val isUserLoggedIn = user.username.isNotEmpty()
                binding.textView4.text = user.username.ifEmpty { getString(R.string.User) }

                if (isUserLoggedIn) {
                    if (NetworkUtils.isInternetAvailable(requireContext())) {
                        dashboardViewModel.fetchHealthNews()
                } else {
                    Toast.makeText(requireContext(), "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter(emptyList())
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
    }

    private fun openWhatsApp() {
        val message = "Halo dok! saya ingin berkonsultasi terkait mental health"
        val phoneNumber = "6285691775503"
        val url = "https://wa.me/$phoneNumber?text=${Uri.encode(message)}"

        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi")
            .setMessage("Apakah Anda ingin membuka WhatsApp untuk mengirim pesan?")
            .setPositiveButton("Ya") { _, _ ->
                // Jika pengguna menekan Ya, buka WhatsApp
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(requireContext(), "WhatsApp tidak ditemukan di perangkat ini", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
