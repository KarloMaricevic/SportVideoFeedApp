package my.app.sportvideofeedapp.ui.feedFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.adapters.FeedListAdapter
import my.app.sportvideofeedapp.adapters.DefaultItemDecoration
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
    FeedFragmentCallback, AdapterView.OnItemSelectedListener {

    private lateinit var mBinding: FragmentFeedBinding

    @Inject
    lateinit var feedListAdapter: FeedListAdapter

    @Inject
    lateinit var sportAdapter: SportSpinnerAdapter

    private val itemDecorator = DefaultItemDecoration(spacingBetweenItems, spacingItemsOfParents)

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
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.feedRecyclerView.also {
            it.addItemDecoration(itemDecorator)
            it.adapter = feedListAdapter
        }
        setUpMenu()
    }

    private fun setUpMenu() {
        (mBinding.feedToolbar.menu.findItem(R.id.sport_spinner).actionView as Spinner).also {
            it.adapter = sportAdapter
            it.onItemSelectedListener = this
            it.setPopupBackgroundResource(R.drawable.shape_popup_background_sport_spinner)
        }
    }

    override fun connectViewModel() {
        mViewModel.getAllSports().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            sportAdapter.clear()
            sportAdapter.addAll(it)
            if (mViewModel.getChosenSportPosition() >= 0) {
                (mBinding.feedToolbar.menu.findItem(R.id.sport_spinner).actionView as Spinner).setSelection(
                    mViewModel.getChosenSportPosition()
                )
            }
        })
        mViewModel.getFeedItemList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            feedListAdapter.setFeedItemList(it)
        })
    }

    override fun getRootView(): View {
        return mBinding.root
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

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mViewModel.setChosenSport(parent?.getItemAtPosition(position) as Sport)
        mBinding.feedRecyclerView.scheduleLayoutAnimation()
        mViewModel.loadFeedItems()
    }

    companion object {
        const val spacingBetweenItems = 10
        const val spacingItemsOfParents = 20
    }
}
