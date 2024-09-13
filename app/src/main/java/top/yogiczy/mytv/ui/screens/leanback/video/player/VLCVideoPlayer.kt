package top.yogiczy.mytv.ui.screens.leanback.video.player

import android.content.Context
import android.net.Uri
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.interfaces.IVLCVout
import org.videolan.libvlc.util.VLCVideoLayout

class VLCVideoPlayer(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
) : LeanbackVideoPlayer(coroutineScope) {
    private val libVLC = LibVLC(context)
    private val mediaPlayer = MediaPlayer(libVLC)
    override fun prepare(url: String) {
        val media = Media(mediaPlayer.libVLC, Uri.parse(url))
        mediaPlayer.media = media
        mediaPlayer.vlcVout.addCallback(object : IVLCVout.Callback {
            override fun onSurfacesCreated(vlcVout: IVLCVout) {
                mediaPlayer.play()
            }

            override fun onSurfacesDestroyed(vlcVout: IVLCVout) {
                log.d("Surfaces destroyed")
            }
        })
        //切换频道后播放
        mediaPlayer.play()
    }

    override fun play() {
        mediaPlayer.play()
    }


    override fun pause() {
        mediaPlayer.pause()
    }

    override fun setVideoSurfaceView(surfaceView: SurfaceView) {

    }

    override fun setVLCVideoLayout(vlcVideoLayout: VLCVideoLayout) {
        mediaPlayer.attachViews(vlcVideoLayout, null, false, false)
    }
    override fun initialize() {
        super.initialize()
        mediaPlayer.setEventListener (vlcPlayerListener )
    }

    private val vlcPlayerListener = MediaPlayer.EventListener { event ->
        when (event.type) {
            MediaPlayer.Event.Vout -> triggerReady()
            MediaPlayer.Event.Buffering -> {
                triggerError(null)
                triggerBuffering(true)
            }
        }
    }
}


