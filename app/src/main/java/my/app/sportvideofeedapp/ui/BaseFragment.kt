package my.app.sportvideofeedapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.core.views.LoadingView
import my.app.sportvideofeedapp.routers.NavigationPlaces
import my.app.sportvideofeedapp.core.router.Router

import my.app.sportvideofeedapp.core.viewModel.BaseViewModel
import my.app.sportvideofeedapp.core.viewModel.BaseViewModel.Loading.LOADING
import my.app.sportvideofeedapp.core.viewModel.BaseViewModel.Loading.NOT_LOADING
import my.app.sportvideofeedapp.core.views.NavigationView
import my.app.sportvideofeedapp.utlis.widgets.CustomProgressDialog
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel<NP>, R : Router, NP : NavigationPlaces> : Fragment(),
    LoadingView, NavigationView<NP> {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var router: R

    protected lateinit var mViewModel: VM

    private var mLadingDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getAppComponent()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoading()
    }

    private fun observeLoading() {
        mViewModel.getIsLoading().observe(viewLifecycleOwner, Observer {
            when (it) {
                NOT_LOADING -> hideLoading()
                LOADING -> showLoading()
                else -> {}
            } })
    }

    override fun showLoading() {
        if (!mLadingDialog.isVisible) {
            mLadingDialog.show(activity!!.supportFragmentManager, "Loading")
        }
    }

    override fun hideLoading() {
        if (mLadingDialog.isVisible) {
            mLadingDialog.dismiss()
        }
    }

    override fun navigate(navigateTo: NP) {
        when (navigateTo) {
            is NavigationPlaces.NavigateBack -> router.navigateBack()
            is NavigationPlaces.ExitApp -> router.exitApp()
        }
    }

    fun observeNavigation() {
        mViewModel.getNavigateTo().observe(this, Observer {
            navigate(it)
        })
    }
}
