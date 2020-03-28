package my.app.sportvideofeedapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.databinding.ItemSingleFeedBinding
import my.app.sportvideofeedapp.di.qualifiers.FragmentContext
import my.app.sportvideofeedapp.ui.feedFragment.FeedFragmentCallback
import my.app.sportvideofeedapp.utlis.helper.loadAnimation
import javax.inject.Inject

class FeedListAdapter @Inject constructor(
    @FragmentContext private val fragmentContext: Context,
    private val mCallback: FeedFragmentCallback
) : RecyclerView.Adapter<FeedItemViewHolder>() {

    private var data = listOf<FeedItem>()

    private val glide = Glide.with(fragmentContext)

    private var wentToPosition = 0
    private var animatedLast = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        val binding = ItemSingleFeedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FeedItemViewHolder(binding, mCallback)
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        holder.bind(data[position])
        glide
            .load(data[position].video.thumbnailUrl)
            .error(R.drawable.ic_glide_error_24dp)
            .fitCenter()
            .into(holder.getPosterImageView())

        if (!isLastItem(position) && position >= wentToPosition) {
            animateItem(holder.itemView, position)
        } else if (isLastItem(position) && !animatedLast) {
            animateLastItem(holder.itemView, position)
        }
    }

    fun setFeedItemList(feedItemList: List<FeedItem>) {
        data = feedItemList
        wentToPosition = 0
        animatedLast = false
        notifyDataSetChanged()
    }

    private fun isLastItem(position: Int) = (position == itemCount - 1)

    private fun animateItem(view: View, itemPosition: Int) {
        loadAnimation(view, fragmentContext, R.anim.item_animation_fall_down)
        wentToPosition = itemPosition
    }

    private fun animateLastItem(view: View, itemPosition: Int) {
        loadAnimation(view, fragmentContext, R.anim.item_animation_fall_down)
        wentToPosition = itemPosition
        animatedLast = true
    }
}

class FeedItemViewHolder(
    private val mBinding: ItemSingleFeedBinding,
    private val mFeedFragmentCallback: FeedFragmentCallback
) :
    RecyclerView.ViewHolder(mBinding.root), View.OnClickListener {

    fun bind(feedItem: FeedItem) {
        mBinding.feedItemCardView.setOnClickListener(this)
        mBinding.feedItem = feedItem
        mBinding.executePendingBindings()
    }

    fun getPosterImageView(): ImageView {
        return mBinding.posterImageView
    }

    override fun onClick(v: View?) {
        mFeedFragmentCallback.feedItemPressedCallback(mBinding.feedItem!!)
    }
}
