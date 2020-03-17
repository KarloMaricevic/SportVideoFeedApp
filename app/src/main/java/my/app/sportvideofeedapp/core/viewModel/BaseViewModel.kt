package my.app.sportvideofeedapp.core.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.app.sportvideofeedapp.routers.NavigationPlaces
import my.app.sportvideofeedapp.core.viewModel.BaseViewModel.Loading.LOADING
import my.app.sportvideofeedapp.core.viewModel.BaseViewModel.Loading.NOT_LOADING

abstract class BaseViewModel<NP : NavigationPlaces> : ViewModel() {

    protected val navigateTo = MutableLiveData<NP>()

    enum class Loading {
        NOT_LOADING,
        LOADING
    }

    private val isLoading = MutableLiveData<Loading>(NOT_LOADING)

    fun showLoading() {
        isLoading.value = LOADING
    }

    fun showNotLoading() {
        isLoading.value = NOT_LOADING
    }

    fun getIsLoading() = isLoading as LiveData<Loading>

    fun getNavigateTo() = navigateTo as LiveData<NP>
}
