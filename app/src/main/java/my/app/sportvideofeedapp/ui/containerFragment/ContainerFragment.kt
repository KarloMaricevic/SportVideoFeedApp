package my.app.sportvideofeedapp.ui.containerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.disposables.Disposable
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.adapters.ContainerPagerAdapter
import my.app.sportvideofeedapp.adapters.FEED_PAGE_INDEX
import my.app.sportvideofeedapp.adapters.SETTINGS_PAGE_INDEX
import my.app.sportvideofeedapp.databinding.FragmentContainerBinding
import my.app.sportvideofeedapp.routers.ContainerNavigationPlaces
import my.app.sportvideofeedapp.routers.ContainerRouter
import my.app.sportvideofeedapp.routers.NavigationPlaces
import my.app.sportvideofeedapp.ui.BaseFragment
import my.app.sportvideofeedapp.viewmodels.ContainerViewModel
import my.app.sportvideofeedapp.viewmodels.SharedContainerViewModel

class ContainerFragment : BaseFragment<ContainerViewModel, ContainerRouter>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: FragmentContainerBinding

    private lateinit var mContainerPagerAdapter: ContainerPagerAdapter

    private lateinit var mSharedViewModel: SharedContainerViewModel

    private lateinit var mClickedFeedItemDisposable : Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as BaseApplication)
            .getAppComponent()
            .getContainerSubcomponentFactory()
            .create(this)
            .inject(this)
        requireActivity().setTheme(AppCompatDelegate.MODE_NIGHT_YES)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(ContainerViewModel::class.java)
        mSharedViewModel =
            ViewModelProvider(this, mViewModelFactory).get(SharedContainerViewModel::class.java)
        mContainerPagerAdapter = ContainerPagerAdapter(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentContainerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.containerViewPager.adapter = mContainerPagerAdapter
        mBinding.containerViewPager.isUserInputEnabled = false
        mBinding.containerBottomNavigationView.setOnNavigationItemSelectedListener(this)
    }


    override fun isContainedInsedeOtherFragment(): Boolean = false

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.feed -> mBinding.containerViewPager.currentItem = FEED_PAGE_INDEX
            R.id.settings -> mBinding.containerViewPager.currentItem = SETTINGS_PAGE_INDEX
        }
        return true
    }

    override fun connectViewModel() {
        mClickedFeedItemDisposable = mSharedViewModel.getClickedFeedItem().subscribe({
            mViewModel.navigateToVideoFragment(it)
        }, {})
    }


    override fun navigate(navigateTo: NavigationPlaces) {
        when (navigateTo) {
            is ContainerNavigationPlaces.NavigateToVideoFragment -> router.navigateToVideoFragment(
                navigateTo.feedItem
            )
        }
    }

    override fun onPause() {
        super.onPause()
        mClickedFeedItemDisposable.dispose()
        mBinding.containerViewPager.adapter = null
    }
}
