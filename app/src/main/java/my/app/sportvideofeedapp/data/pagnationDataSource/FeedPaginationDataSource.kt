package my.app.sportvideofeedapp.data.pagnationDataSource

import io.reactivex.functions.Action
import my.app.sportvideofeedapp.adapters.PagerLoadState
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.data.network.ApiHelper
import my.app.sportvideofeedapp.utlis.scheduler.SchedulerProvider

class FeedPaginationDataSource(
    private val mApiHelper: ApiHelper,
    private val mAppSchedulerProvider: SchedulerProvider
) :
    StatePageKeyDataSource<Int, FeedItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, FeedItem>
    ) {
        if (mChosenSport != null) {
            super.loadInitial(params, callback)
            val initialPageDisposable =
                mApiHelper.getFeedPages(
                    1,
                    params.requestedLoadSize,
                    mChosenSport!!.slug
                ).observeOn(mAppSchedulerProvider.ui())
                    .subscribe({
                        mState.postValue(PagerLoadState.LOADED)
                        callback.onResult(it, null, 1 + params.requestedLoadSize)
                    },
                        {
                            setRetry(Action { loadInitial(params, callback) })
                            mState.postValue(PagerLoadState.error("Something went wrong"))
                        }
                    )
            mCompositeDisposable.add(initialPageDisposable)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItem>) {
        super.loadAfter(params, callback)
        val getLoadAfterDisposable =
            mApiHelper.getFeedPages(
                params.key,
                params.requestedLoadSize,
                mChosenSport!!.slug
            )
                .observeOn(mAppSchedulerProvider.ui())
                .subscribe({
                    mState.postValue(PagerLoadState.LOADED)
                    callback.onResult(it, params.key + params.requestedLoadSize)
                },
                    {
                        setRetry(Action { loadAfter(params, callback) })
                        mState.postValue(PagerLoadState.error("Something went wrong"))
                    }
                )
        mCompositeDisposable.add(getLoadAfterDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItem>) = Unit

    fun setChosenSport(sport: Sport) {
        mChosenSport = sport
        invalidate()
    }

    companion object {
        // workaround if chosenSport not static he gets deleted on invalidate()
        private var mChosenSport: Sport? = null
    }
}
