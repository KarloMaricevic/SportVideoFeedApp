package my.app.sportvideofeedapp.ui.containerFragment.feedFragment

import my.app.sportvideofeedapp.data.entities.FeedItem

interface FeedFragmentCallback {
    fun feedItemPressedCallback(feedItem: FeedItem)
}
