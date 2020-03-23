package my.app.sportvideofeedapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import my.app.sportvideofeedapp.core.viewModel.NetworkViewModel
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.data.interactors.FeedInteractor
import my.app.sportvideofeedapp.routers.PlaceHolderNavigationPlaces
import my.app.sportvideofeedapp.utlis.scheduler.SchedulerProvider
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val feedInteractor: FeedInteractor,
    private val schedulersProvider: SchedulerProvider
) :
    NetworkViewModel<PlaceHolderNavigationPlaces>() {

    private val allSports = MutableLiveData<List<Sport>>()

    private val chosenSport = MutableLiveData<Sport>()

    private val feedItemList = MutableLiveData<List<FeedItem>>()

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
        mCompositeDisposable.add(getAllSportsObserver)
    }

    fun getAllSports() = allSports as LiveData<List<Sport>>

    fun setChosenSport(sport: Sport) {
        chosenSport.value = sport
    }

    fun getChosenSport() = chosenSport as LiveData<Sport>

    fun getFeedItemList() = feedItemList as LiveData<List<FeedItem>>

    fun loadFeedItems() {
        val getPageDisposable = feedInteractor.getFeedPage(sportSlug = chosenSport.value!!.slug)
            .observeOn(schedulersProvider.ui())
            .subscribe(
                {
                    feedItemList.value = it
                },
                {
                    handleNetworkError(it)
                }
            )
        mCompositeDisposable.add(getPageDisposable)
    }
}
