package my.app.sportvideofeedapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.app.sportvideofeedapp.adapters.PlayerViewHolder
import my.app.sportvideofeedapp.utlis.exo.ExoUtil

class ExoPlayerRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    var mExoUtil: ExoUtil? = null

    var playingViewHolder: PlayerViewHolder? = null

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            SCROLL_STATE_IDLE -> {
                if (layoutManager is LinearLayoutManager) {
                    val itemPosition =
                        (layoutManager as LinearLayoutManager?)?.findFirstCompletelyVisibleItemPosition()
                            ?: NO_POSITION
                    if (itemPosition != NO_POSITION) {
                        val viewHolder = findViewHolderForAdapterPosition(itemPosition)
                        if (viewHolder is PlayerViewHolder) {
                            playVideo(viewHolder)
                        }
                    }
                }
            }
        }
    }

    private fun playVideo(viewHolder: PlayerViewHolder) {
        if (viewHolder != playingViewHolder) {
            playingViewHolder?.stop()
            playingViewHolder = viewHolder
            viewHolder.play(mExoUtil ?: throw NullPointerException())
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mExoUtil?.stopPlayer()
    }
}
