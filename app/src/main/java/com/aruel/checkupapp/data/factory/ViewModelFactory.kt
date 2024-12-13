package com.aruel.checkupapp.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aruel.checkupapp.data.di.Injection
import com.aruel.checkupapp.data.pref.UserPreference
import com.aruel.checkupapp.data.pref.dataStore
import com.aruel.checkupapp.data.repository.UserRepository
import com.aruel.checkupapp.ui.checkup.CheckupViewModel
import com.aruel.checkupapp.ui.dashboard.DashboardViewModel
import com.aruel.checkupapp.ui.history.HistoryViewModel
import com.aruel.checkupapp.ui.home.HomeViewModel
import com.aruel.checkupapp.ui.intro.IntroViewModel
import com.aruel.checkupapp.ui.login.LoginViewModel
import com.aruel.checkupapp.ui.main.MainViewModel
import com.aruel.checkupapp.ui.notifications.NotificationsViewModel
import com.aruel.checkupapp.ui.register.SignupViewModel
import com.aruel.checkupapp.ui.result.ResultViewModel

class ViewModelFactory(
    private val repository: UserRepository,
    private val userPreference: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NotificationsViewModel::class.java) -> {
                NotificationsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CheckupViewModel::class.java) -> {
                CheckupViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(IntroViewModel::class.java) -> {
                IntroViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val userPreference = UserPreference.getInstance(context.dataStore)
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context),
                        userPreference
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}

