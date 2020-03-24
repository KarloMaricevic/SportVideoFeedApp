package my.app.sportvideofeedapp.ui.videoFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.core.router.DefaultRouter
import my.app.sportvideofeedapp.databinding.FragmentVideoBinding
import my.app.sportvideofeedapp.ui.BaseFragment
import my.app.sportvideofeedapp.utlis.exo.ExoUtil
import my.app.sportvideofeedapp.utlis.exo.ExoUtilFactory
import my.app.sportvideofeedapp.viewmodels.VideoViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class VideoFragment : BaseFragment<VideoViewModel, DefaultRouter>(),
    ExoUtil.PlayerStateListener {

    @Inject
    internal lateinit var exoUtilFactory: ExoUtilFactory
    private lateinit var exoUtil: ExoUtil

    private lateinit var mBinding: FragmentVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getAppComponent()
            .getVideoSubcomponentFactory()
            .create(this)
            .inject(this)
        val navigationArgs: VideoFragmentArgs by navArgs()
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(VideoViewModel::class.java)
        mViewModel.setFeedItem(navigationArgs.feedItem)
        setUpExoPlayer(navigationArgs.feedItem.video.videoUrl)
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
        exoUtil.setPlayerView(mBinding.videoPlayerView)
    }

    private fun setUpExoPlayer(url: String) {
        exoUtil = exoUtilFactory.exoUtil
        exoUtil.setUrl(url)
        exoUtil.setListener(this)
        this.lifecycle.addObserver(ExoUtilHandler(exoUtil))
    }

    override fun onPlayerStateChanged(playbackState: Int) {}

    override fun onPlayerError() {
        Log.e("Error", "erer")
    }
}
