package my.app.sportvideofeedapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.databinding.FragmentFeedBinding
import my.app.sportvideofeedapp.routers.PlaceHolderNavigationPlaces
import my.app.sportvideofeedapp.routers.FeedRouter
import my.app.sportvideofeedapp.viewmodels.FeedViewModel

class FeedFragment : NetworkFragment<FeedViewModel, FeedRouter, PlaceHolderNavigationPlaces>() {

    private lateinit var mBinding: FragmentFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getAppComponent()
            .getFeedSubcomponentFactory()
            .create(this)
            .inject(this)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(FeedViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFeedBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun getRootView(): View {
            return mBinding.root
    }
}
