package my.app.sportvideofeedapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import my.app.sportvideofeedapp.core.viewModel.BaseViewModel
import my.app.sportvideofeedapp.data.entities.FeedItem
import javax.inject.Inject

class VideoViewModel @Inject constructor() : BaseViewModel() {

    private var feedItem = MutableLiveData<FeedItem>()

    fun setFeedItem(feedItem: FeedItem) {
        this.feedItem.value = feedItem
    }

    fun getFeedItem() = feedItem as LiveData<FeedItem>
}
