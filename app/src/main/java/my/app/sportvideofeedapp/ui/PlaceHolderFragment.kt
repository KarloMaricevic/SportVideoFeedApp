package my.app.sportvideofeedapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.databinding.FragmentPlaceHolderBinding
import my.app.sportvideofeedapp.routers.PlaceHolderNavigationPlaces
import my.app.sportvideofeedapp.routers.PlaceHolderRouter
import my.app.sportvideofeedapp.viewmodels.placeholderViewmodel.PlaceHolderViewModel

class PlaceHolderFragment : NetworkFragment<PlaceHolderViewModel, PlaceHolderRouter, PlaceHolderNavigationPlaces>() {

    private lateinit var mBinding: FragmentPlaceHolderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getAppComponent()
            .getPlaceHolderSubcomponentFactory()
            .create(this)
            .inject(this)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(PlaceHolderViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPlaceHolderBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun getRootView(): View {
            return mBinding.root
    }
}
