package my.app.sportvideofeedapp.ui.feedFragment

import my.app.sportvideofeedapp.data.entities.Sport

interface FeedFragmentCallback {
    fun chosenSportCallback(sport: Sport)
}
