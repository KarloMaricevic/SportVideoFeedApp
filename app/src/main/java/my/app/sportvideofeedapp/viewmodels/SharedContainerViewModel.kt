package my.app.sportvideofeedapp.viewmodels

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import my.app.sportvideofeedapp.core.viewModel.BaseViewModel
import my.app.sportvideofeedapp.data.entities.FeedItem
import javax.inject.Inject

class SharedContainerViewModel @Inject constructor() : BaseViewModel() {
    private val mClickedFeedItem = PublishRelay.create<FeedItem>()

    fun sendClickedFeedItem(feedItem: FeedItem){
        mClickedFeedItem.accept(feedItem)
    }

    fun getClickedFeedItem() = mClickedFeedItem as Observable<FeedItem>
}