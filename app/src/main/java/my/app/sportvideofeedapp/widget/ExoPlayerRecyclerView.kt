package my.app.sportvideofeedapp.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.app.sportvideofeedapp.adapters.PlayerView
import my.app.sportvideofeedapp.utlis.exo.ExoUtil

class ExoPlayerRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    var mExoUtil: ExoUtil? = null

    var playingViewHolder: PlayerView? = null

    override fun onScrollStateChanged(state: Int) {
        if (state == SCROLL_STATE_IDLE && layoutManager is LinearLayoutManager) {
            val adapterPosition =
                (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            if (adapterPosition != NO_POSITION) {
                val viewHolder = findViewHolderForAdapterPosition(adapterPosition)
                if (viewHolder is PlayerView) {
                    playVideo(viewHolder)
                }
            }

        }
    }

    private fun playVideo(viewHolder: PlayerView) {
        if (viewHolder != playingViewHolder) {
            playingViewHolder?.stop()
            playingViewHolder = viewHolder
            viewHolder.play(mExoUtil ?: throw NullPointerException())
        }
    }
}
