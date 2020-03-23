package my.app.sportvideofeedapp.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FeedListItemDecoration(
    private val mSpaceBetweenItems: Int,
    private val mSpacingOfParent: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.childCount
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(
                mSpacingOfParent,
                mSpaceBetweenItems,
                mSpacingOfParent,
                mSpaceBetweenItems
            )
        } else {
            outRect.set(mSpacingOfParent, 0, mSpacingOfParent, mSpaceBetweenItems)
        }
    }
}
