package my.app.sportvideofeedapp.ui.containerFragment.feedFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.adapters.FeedListAdapter
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.adapters.DefaultItemDecoration
import my.app.sportvideofeedapp.adapters.SportSpinnerAdapter
import my.app.sportvideofeedapp.core.router.DefaultRouter
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.databinding.FragmentFeedBinding
import my.app.sportvideofeedapp.ui.NetworkFragment
import my.app.sportvideofeedapp.viewmodels.FeedViewModel
import my.app.sportvideofeedapp.viewmodels.SharedContainerViewModel
import javax.inject.Inject

class FeedFragment : NetworkFragment<FeedViewModel, DefaultRouter>(),
    FeedFragmentCallback, AdapterView.OnItemSelectedListener {

    private lateinit var mBinding: FragmentFeedBinding

    private lateinit var mSharedViewModel : SharedContainerViewModel

    @Inject
    lateinit var feedListAdapter: FeedListAdapter

    @Inject
    lateinit var sportAdapter: SportSpinnerAdapter

    @Inject
    lateinit var mFeedItemAdapter: FeedListAdapter

    private val itemDecorator = DefaultItemDecoration(spacingBetweenItems, spacingItemsOfParents)

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as BaseApplication)
            .getAppComponent()
            .getFeedSubcomponentFactory()
            .create(this, this, requireContext())
            .inject(this)
        mViewModel = ViewModelProvider(parentFragment as Fragment, mViewModelFactory).get(FeedViewModel::class.java)
        mSharedViewModel = ViewModelProvider(parentFragment as Fragment,mViewModelFactory).get(SharedContainerViewModel::class.java)
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
            it.adapter = mFeedItemAdapter
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

        mViewModel.getFeedPagedList().observe(viewLifecycleOwner, Observer {
            mFeedItemAdapter.submitList(it)
        })

        mViewModel.getNetworkState().observe(viewLifecycleOwner, Observer {
            mFeedItemAdapter.setNetworkState(it)
        })
        mFeedItemAdapter.setRetryCallback { mViewModel.retryPageLoad() }
    }

    override fun getRootView(): View {
        return mBinding.root
    }

    override fun feedItemPressedCallback(feedItem: FeedItem) {
        mSharedViewModel.sendClickedFeedItem(feedItem)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mViewModel.setChosenSport(parent?.getItemAtPosition(position) as Sport)
    }

    override fun isContainedInsedeOtherFragment(): Boolean = true

    companion object {
        const val spacingBetweenItems = 100
        const val spacingItemsOfParents = 20
    }
}
