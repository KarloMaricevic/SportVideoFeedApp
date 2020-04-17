package my.app.sportvideofeedapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.databinding.ItemSingleFeedBinding
import my.app.sportvideofeedapp.di.qualifiers.FragmentContext
import my.app.sportvideofeedapp.ui.feedFragment.FeedFragmentCallback
import javax.inject.Inject

class FeedListAdapter @Inject constructor(
    @FragmentContext private val fragmentContext: Context,
    private val mCallback: FeedFragmentCallback
) : NetworkPagedListAdapter<FeedItem>(diff_callback) {

    private val mGlide = Glide.with(fragmentContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_single_feed -> {
                val binding = ItemSingleFeedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FeedItemViewHolder(binding, mCallback)
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (hasExtraRow() && position == itemCount - 1) {
            return super.getItemViewType(position)
        } else return R.layout.item_single_feed
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_single_feed -> {
                val item = getItem(position)
                if (item != null) {
                    val holderWithType = holder as FeedItemViewHolder
                    holderWithType.bind(item)
                    mGlide
                        .load(item.video.thumbnailUrl)
                        .error(R.drawable.ic_glide_error_24dp)
                        .fitCenter()
                        .into(holder.getPosterImageView())
                }
            }
            else -> super.onBindViewHolder(holder, position)
        }
    }

    companion object {
        val diff_callback = object : DiffUtil.ItemCallback<FeedItem>() {
            override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
                oldItem.id == newItem.id

            // no need
            override fun getChangePayload(oldItem: FeedItem, newItem: FeedItem): Any? = Unit

            override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
                oldItem == newItem
        }
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
