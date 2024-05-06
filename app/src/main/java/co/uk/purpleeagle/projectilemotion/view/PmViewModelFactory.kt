package co.uk.purpleeagle.projectilemotion.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController

class PmViewModelFactory(
    private val navController: NavHostController,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PmViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PmViewModel(navController,) as T
        }
        throw(IllegalArgumentException("View Model does not exist"))
    }
}