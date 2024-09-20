package top.yogiczy.mytv.ui.screens.leanback.video.player

import android.content.Context
import android.view.SurfaceView
import android.view.View
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.coroutines.CoroutineScope

class GSYVideoPlayer(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
) : LeanbackVideoPlayer(coroutineScope)  {
    var videoPlayer : TVGSYVideoPlayer? =null
    var firstUrl=""
    var isFirstPrepare=false
    override fun prepare(url: String) {
        videoPlayer?.setUp(url, true, null)
        videoPlayer?.isShowFullAnimation = true
        videoPlayer?.setIsTouchWigetFull(false)
        videoPlayer?.setThumbPlay(false)

        // videoPlayer?.startButton?.visibility = View.INVISIBLE
        // videoPlayer?.backButton?.visibility = View.INVISIBLE
        videoPlayer?.startPlayLogic()
        if(videoPlayer ==null){
            isFirstPrepare=true
            firstUrl=url
        }else{
            isFirstPrepare=false
        }
    }

    override fun play() {
        if(isFirstPrepare){
            prepare(firstUrl)
        }
        videoPlayer?.startPlayLogic()
    }


    override fun pause() {
        videoPlayer?.onVideoPause()
    }

    override fun setVideoSurfaceView(surfaceView: SurfaceView) {

    }

    override fun setGSYView(gsyView: TVGSYVideoPlayer) {
        videoPlayer=gsyView
    }
    override fun initialize() {
        super.initialize()
        videoPlayer?.setVideoAllCallBack(videoCallBack)
    }

    private val videoCallBack = object : VideoAllCallBack{
        override fun onStartPrepared(url: String?, vararg objects: Any?) {

        }

        override fun onPrepared(url: String?, vararg objects: Any?) {
            triggerReady()
        }

        override fun onClickStartIcon(url: String?, vararg objects: Any?) {
        }

        override fun onClickStartError(url: String?, vararg objects: Any?) {
        }

        override fun onClickStop(url: String?, vararg objects: Any?) {
        }

        override fun onClickStopFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onClickResume(url: String?, vararg objects: Any?) {
        }

        override fun onClickResumeFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onClickSeekbar(url: String?, vararg objects: Any?) {
        }

        override fun onClickSeekbarFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onAutoComplete(url: String?, vararg objects: Any?) {
        }

        override fun onComplete(url: String?, vararg objects: Any?) {
        }

        override fun onEnterFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onQuitSmallWidget(url: String?, vararg objects: Any?) {
        }

        override fun onEnterSmallWidget(url: String?, vararg objects: Any?) {
        }

        override fun onTouchScreenSeekVolume(url: String?, vararg objects: Any?) {
        }

        override fun onTouchScreenSeekPosition(url: String?, vararg objects: Any?) {
        }

        override fun onTouchScreenSeekLight(url: String?, vararg objects: Any?) {
        }

        override fun onPlayError(url: String?, vararg objects: Any?) {
        }

        override fun onClickStartThumb(url: String?, vararg objects: Any?) {
        }

        override fun onClickBlank(url: String?, vararg objects: Any?) {
        }

        override fun onClickBlankFullscreen(url: String?, vararg objects: Any?) {
        }
    }

    // private val vlcPlayerListener = MediaPlayer.EventListener { event ->
    //     when (event.type) {
    //         MediaPlayer.Event.Vout -> triggerReady()
    //         MediaPlayer.Event.Buffering -> {
    //             triggerError(null)
    //             triggerBuffering(true)
    //         }
    //     }
    // }
}