package my.app.sportvideofeedapp.data.pagnationDataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import my.app.sportvideofeedapp.adapters.PagerLoadState

abstract class StatePageKeyDataSource<Key, Value> : PageKeyedDataSource<Key, Value>() {

    protected val mCompositeDisposable = CompositeDisposable()

    protected val mState = MutableLiveData<PagerLoadState>()

    protected var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>
    ) {
        mState.postValue(PagerLoadState.LOADING)
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        mState.postValue(PagerLoadState.LOADING)
    }

    protected fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = action
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    fun getState() = mState as LiveData<PagerLoadState>

    fun retry() {
        if (retryCompletable != null) {
            mCompositeDisposable.add(retryCompletable!!
                .subscribe(
                    { Unit },
                    {
                        Log.e("Error loading pages:", it.message ?: "Unknown")
                    }
                ))
        }
    }
}
