package my.app.sportvideofeedapp.ui.feedFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.adapters.FeedListAdapter
import my.app.sportvideofeedapp.adapters.FeedListItemDecoration
import my.app.sportvideofeedapp.adapters.SportSpinnerAdapter
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.databinding.FragmentFeedBinding
import my.app.sportvideofeedapp.routers.FeedRouter
import my.app.sportvideofeedapp.routers.NavigationPlaces
import my.app.sportvideofeedapp.routers.PlaceHolderNavigationPlaces
import my.app.sportvideofeedapp.ui.NetworkFragment
import my.app.sportvideofeedapp.viewmodels.FeedViewModel
import javax.inject.Inject

class FeedFragment : NetworkFragment<FeedViewModel, FeedRouter>(),
    FeedFragmentCallback {

    private lateinit var mBinding: FragmentFeedBinding

    @Inject
    lateinit var feedListAdapter: FeedListAdapter

    @Inject
    lateinit var sportAdapter: SportSpinnerAdapter

    private val itemDecorator = FeedListItemDecoration(spacingBetweenItems, spacingItemsOfParents)

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getAppComponent()
            .getFeedSubcomponentFactory()
            .create(this, this, context!!)
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
        setUpMenu()
        connectViewModel()
        mBinding.feedRecyclerView.adapter = feedListAdapter
        mBinding.feedRecyclerView.addItemDecoration(itemDecorator)
        return mBinding.root
    }

    private fun setUpMenu() {
        (mBinding.feedToolbar.menu.findItem(R.id.sport_spinner).actionView as Spinner).also {
            it.adapter = sportAdapter
        }
    }

    override fun connectViewModel() {
        mViewModel.getAllSports().observe(viewLifecycleOwner, Observer {
            sportAdapter.clear()
            sportAdapter.addAll(it)
        })
        mViewModel.getFeedItemList().observe(viewLifecycleOwner, Observer {
            feedListAdapter.setFeedItemList(it)
        })
        mViewModel.getChosenSport().observe(viewLifecycleOwner, Observer {
            sportAdapter.setChosenSport(it)
        })
    }

    override fun getRootView(): View {
        return mBinding.root
    }

    override fun chosenSportCallback(sport: Sport) {
        mViewModel.setChosenSport(sport)
        mViewModel.loadFeedItems()
    }

    override fun feedItemPressedCallback(feedItem: FeedItem) {
        mViewModel.navigateToVideoFragment(feedItem)
    }

    override fun navigate(navigateTo: NavigationPlaces) {
        super.navigate(navigateTo)
        when (navigateTo) {
            is PlaceHolderNavigationPlaces.NavigateToVideoFragment -> router.navigateToVideoFragment(
                navigateTo.feedItem
            )
        }
    }

    companion object {
        const val spacingBetweenItems = 10
        const val spacingItemsOfParents = 20
    }
}
