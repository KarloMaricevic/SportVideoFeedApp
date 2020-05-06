package my.app.sportvideofeedapp.ui.containerFragment.feedFragment

import my.app.sportvideofeedapp.data.entities.Author

interface FeedFragmentCallback {
    fun feedAuthorPressedCallback(feedAuthor: Author)
}
