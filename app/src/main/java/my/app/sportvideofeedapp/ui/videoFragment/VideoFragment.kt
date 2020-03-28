package my.app.sportvideofeedapp.ui.videoFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlaybackException
import io.reactivex.disposables.Disposable
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.core.router.DefaultRouter
import my.app.sportvideofeedapp.databinding.FragmentVideoBinding
import my.app.sportvideofeedapp.ui.BaseFragment
import my.app.sportvideofeedapp.utlis.exo.CustomPlayerControlDispatcher
import my.app.sportvideofeedapp.utlis.exo.ExoUtil
import my.app.sportvideofeedapp.utlis.exo.ExoUtilHandler
import my.app.sportvideofeedapp.viewmodels.VideoViewModel
import javax.inject.Inject

class VideoFragment : BaseFragment<VideoViewModel, DefaultRouter>(),
    ExoUtil.PlayerStateListener, VideoFragmentCallback {

    @Inject
    internal lateinit var mExoUtil: ExoUtil

    private val mCustomPlayerControlDispatcher = CustomPlayerControlDispatcher(this)

    private lateinit var mBinding: FragmentVideoBinding

    private val navigationArgs: VideoFragmentArgs by navArgs()

    private lateinit var mSeekDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getAppComponent()
            .getVideoSubcomponentFactory()
            .create(this)
            .inject(this)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(VideoViewModel::class.java)
        mViewModel.setFeedItem(navigationArgs.feedItem)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentVideoBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.setFeedItem(navigationArgs.feedItem)
        mBinding.videoPlayerView.setControlDispatcher(mCustomPlayerControlDispatcher)
        setUpExoPlayer()
    }

    override fun onPause() {
        mViewModel.setSavedPlayerPosition(mExoUtil.getPlayerPosition())
        mSeekDisposable.dispose()
        super.onPause()
    }

    override fun connectViewModel() {
        mBinding.videoViewModel = mViewModel

        mSeekDisposable = mViewModel.getEvents().subscribe(
            {
                mExoUtil.seekTo(it)
            },
            {
                Log.e("SeekCallbackError", it.message ?: "Unknown")
            }
        )

        mViewModel.getPlayWhenReady().observe(this, Observer {
            when (it) {
                true -> mExoUtil.playVideo()
                false -> mExoUtil.pauseVideo()
            }
        })

        mViewModel.getIsShowMorePressed().observe(this, Observer {
            when (it) {
                true -> mBinding.videoDescriptionTextView?.visibility = View.VISIBLE
                false -> mBinding.videoDescriptionTextView?.visibility = View.GONE
            }
        })

        mViewModel.getPlayerState().observe(this, Observer {
            when (it) {
                VideoViewModel.PlayerState.STATE_BUFFERING -> showBufferingVideo()
                VideoViewModel.PlayerState.STATE_READY -> hideBufferingVideo()
                else -> {
                }
            }
        })

        mViewModel.getError().observe(this, Observer {
            mViewModel.navigateBack()
        })
    }

    //region Callbacks

    // Suppressed magic number because they are defined in Player.java class
    @Suppress("MagicNumber")
    override fun onPlayerStateChanged(playbackState: Int) {
        when (playbackState) {
            1 -> mViewModel.setState(VideoViewModel.PlayerState.STATE_IDLE)
            2 -> mViewModel.setState(VideoViewModel.PlayerState.STATE_BUFFERING)
            3 -> mViewModel.setState(VideoViewModel.PlayerState.STATE_READY)
            4 -> mViewModel.setState(VideoViewModel.PlayerState.STATE_ENDED)
            else -> {
            }
        }
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        mViewModel.setError(error)
    }

    override fun playPressed() {
        mViewModel.playVideo()
    }

    override fun pausePressed() {
        mViewModel.pauseVideo()
    }

    override fun seekTo(positionMs: Long) {
        mViewModel.seekTo(positionMs)
    }

    //endregion

    private fun setUpExoPlayer() {
        mExoUtil.setPlayerView(mBinding.videoPlayerView)
        mExoUtil.setUrl(mViewModel.getFeedItem().video.videoUrl)
        mExoUtil.setPlayerControlDispatcher(mCustomPlayerControlDispatcher)
        mExoUtil.setInitPlayerPosition(mViewModel.getSavedPlayerPosition())
        mExoUtil.setListener(this)
        ExoUtilHandler.makeAndConnectExoUtilHandler(mExoUtil, this.lifecycle)
    }

    private fun showBufferingVideo() {
        mBinding.videoBufferingContentLoadingProgressBar.show()
    }

    private fun hideBufferingVideo() {
        mBinding.videoBufferingContentLoadingProgressBar.hide()
    }
}
