package my.app.sportvideofeedapp.core.viewModel

import io.reactivex.disposables.CompositeDisposable
import my.app.sportvideofeedapp.routers.NavigationPlaces

// Use it if you don't have to talk to network but still need Rx (RoomDatabase)
abstract class RxViewModel<NP : NavigationPlaces> :
    BaseViewModel<NP>() {
    protected val mCompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }
}
