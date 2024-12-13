package com.aruel.checkupapp.ui.notifications

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aruel.checkupapp.R
import com.aruel.checkupapp.data.factory.ViewModelFactory
import com.aruel.checkupapp.databinding.FragmentNotificationsBinding
import com.aruel.checkupapp.ui.checkup.CheckupViewModel
import com.aruel.checkupapp.ui.main.MainViewModel

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val notificationsViewModel: NotificationsViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAction()
        observeUserSession()

        return root


    }


    private fun setupAction() {
        binding.Logout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }
    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Confirm Logout")
            setMessage("Are you sure you want to logout?")
            setPositiveButton("Yes") { _, _ ->
                notificationsViewModel.logout()
            }
            setNegativeButton("No", null)
            create()
            show()
        }
    }
    private fun observeUserSession() {
        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenStarted {
            notificationsViewModel.getSession().collect { user ->
                // Update TextViews with username and email
                binding.textView5.text = user.username.ifEmpty { getString(R.string.User) }
                binding.textView6.text = user.email.ifEmpty { getString(R.string.emaill) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}