package my.app.sportvideofeedapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import my.app.sportvideofeedapp.adapters.PagerLoadState
import my.app.sportvideofeedapp.core.viewModel.NetworkViewModel
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.data.interactors.FeedInteractor
import my.app.sportvideofeedapp.data.pagnationDataSource.FeedDataSourceFactory
import my.app.sportvideofeedapp.routers.PlaceHolderNavigationPlaces.NavigateToVideoFragment
import my.app.sportvideofeedapp.utlis.scheduler.SchedulerProvider
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val feedInteractor: FeedInteractor,
    private val mFeedDataSourceFactory: FeedDataSourceFactory,
    private val schedulersProvider: SchedulerProvider
) :
    NetworkViewModel() {

    private val allSports = MutableLiveData<List<Sport>>()

    private lateinit var chosenSport: Sport

    private val feedItemPagedList: LiveData<PagedList<FeedItem>>

    init {
        val getAllSportsObserver = feedInteractor
            .getAllSports()
            .observeOn(schedulersProvider.ui())
            .subscribe(
                {
                    allSports.value = it
                },
                {
                    handleNetworkError(it)
                }
            )
        val confing = PagedList.Config.Builder()
            .setPageSize(mInitPageLoad)
            .setEnablePlaceholders(false)
            .build()

        feedItemPagedList = LivePagedListBuilder(mFeedDataSourceFactory, confing).build()
        mCompositeDisposable.add(getAllSportsObserver)
    }

    fun getAllSports() = allSports as LiveData<List<Sport>>

    fun setChosenSport(sport: Sport) {
        chosenSport = sport
        mFeedDataSourceFactory.getDataSourceFactory().value!!.setChosenSport(sport)
    }

    fun getChosenSportPosition(): Int {
        return if (::chosenSport.isInitialized) {
            allSports.value!!.indexOf(chosenSport)
        } else {
            -1
        }
    }

    fun getFeedPagedList() = feedItemPagedList

    fun retryPageLoad() {
        mFeedDataSourceFactory.getDataSourceFactory().value!!.retry()
    }

    fun getNetworkState(): LiveData<PagerLoadState> = Transformations.switchMap(
        mFeedDataSourceFactory.getDataSourceFactory()
    ) { it.getState() }

    fun navigateToVideoFragment(feedItem: FeedItem) {
        navigateTo.accept(NavigateToVideoFragment(feedItem))
    }

    companion object {
        const val mInitPageLoad = 2
    }
}
