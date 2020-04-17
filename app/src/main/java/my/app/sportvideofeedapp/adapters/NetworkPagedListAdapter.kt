package my.app.sportvideofeedapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.databinding.LayoutPagnationNetworkStateBinding

abstract class NetworkPagedListAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, RecyclerView.ViewHolder>(diffUtil) {

    private var loadState: PagerLoadState? = null

    private var mRetryCallback: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.layout_pagnation_network_state -> NetworkStateViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.layout_pagnation_network_state -> {
                val holderWithType = holder as NetworkStateViewHolder
                holderWithType.bind(loadState, mRetryCallback)
            }
        }
    }

    protected fun hasExtraRow(): Boolean {
        return loadState != null && loadState != PagerLoadState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_pagnation_network_state
    }

    @Suppress("NestedBlockDepth")
    fun setNetworkState(newLoadState: PagerLoadState?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.loadState
                val hadExtraRow = hasExtraRow()
                this.loadState = newLoadState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newLoadState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

    fun setRetryCallback(callback: () -> Unit) {
        mRetryCallback = callback
    }
}

class NetworkStateViewHolder(private val mBinding: LayoutPagnationNetworkStateBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(loadState: PagerLoadState?, retryCallback: (() -> Unit)?) {
        mBinding.retryLoadingButton.setOnClickListener { retryCallback?.invoke() }
        mBinding.retryLoadingButton.visibility =
            if (loadState?.status == Status.FAILED) {
                View.VISIBLE
            } else {
                View.GONE
            }

        mBinding.loadingProgressBar.visibility =
            if (loadState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        mBinding.errorMessageTextView.apply {
            if (loadState?.message != null) {
                visibility = View.VISIBLE
                text = loadState.message
            } else {
                visibility = View.GONE
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val mBinding = LayoutPagnationNetworkStateBinding.inflate(layoutInflater, parent, false)
            return NetworkStateViewHolder(mBinding)
        }
    }
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class PagerLoadState private constructor(
    val status: Status,
    val message: String? = null
) {
    companion object {
        val LOADED = PagerLoadState(Status.SUCCESS)
        val LOADING = PagerLoadState(Status.RUNNING)
        fun error(msg: String?) = PagerLoadState(Status.FAILED, msg)
    }
}
