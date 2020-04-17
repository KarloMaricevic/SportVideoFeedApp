package my.app.sportvideofeedapp.data.pagnationDataSource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.data.network.ApiHelper
import my.app.sportvideofeedapp.utlis.scheduler.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedDataSourceFactory @Inject constructor(
    private val mApiHelper: ApiHelper,
    private val mSchedulerProvider: SchedulerProvider
) : DataSource.Factory<Int, FeedItem>() {

    private val mFeedDataSourceLiveData = MutableLiveData<FeedPaginationDataSource>()

    override fun create(): DataSource<Int, FeedItem> {
        val feedDataSource = FeedPaginationDataSource(mApiHelper, mSchedulerProvider)
        mFeedDataSourceLiveData.postValue(feedDataSource)
        return feedDataSource
    }

    fun getDataSourceFactory() = mFeedDataSourceLiveData as LiveData<FeedPaginationDataSource>
}
