package my.app.sportvideofeedapp.viewmodels

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import my.app.sportvideofeedapp.core.viewModel.BaseViewModel
import my.app.sportvideofeedapp.data.entities.Author
import javax.inject.Inject

class SharedContainerViewModel @Inject constructor() : BaseViewModel() {
    private val mClickedFeedItem = PublishRelay.create<Author>()

    fun sendClickedFeedItem(feedAuthor: Author) {
        mClickedFeedItem.accept(feedAuthor)
    }

    fun getClickedFeedItem() = mClickedFeedItem as Observable<Author>
}
