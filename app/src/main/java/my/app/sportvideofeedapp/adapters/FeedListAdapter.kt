package my.app.sportvideofeedapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.app.sportvideofeedapp.data.entities.FeedItem
import my.app.sportvideofeedapp.databinding.ItemSingleFeedBinding
import my.app.sportvideofeedapp.di.qualifiers.ActivityContext
import my.app.sportvideofeedapp.ui.feedFragment.FeedFragmentCallback
import javax.inject.Inject

class FeedListAdapter @Inject constructor(
    @ActivityContext fragmentContext: Context,
    private val mCallback: FeedFragmentCallback
) : RecyclerView.Adapter<FeedItemViewHolder>() {

    private var data = listOf<FeedItem>()

    private val glide = Glide.with(fragmentContext)

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
            .into(holder.getPosterImageView())
    }

    fun setFeedItemList(feedItemList: List<FeedItem>) {
        data = feedItemList
        notifyDataSetChanged()
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
