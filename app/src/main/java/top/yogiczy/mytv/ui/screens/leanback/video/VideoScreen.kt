package top.yogiczy.mytv.ui.screens.leanback.video

import android.view.SurfaceView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.model.VideoOptionModel
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import top.yogiczy.mytv.data.utils.Constants
import top.yogiczy.mytv.ui.rememberLeanbackChildPadding
import top.yogiczy.mytv.ui.screens.leanback.settings.LeanbackSettingsViewModel
import top.yogiczy.mytv.ui.screens.leanback.video.components.LeanbackVideoPlayerMetadata
import top.yogiczy.mytv.ui.screens.leanback.video.player.TVGSYVideoPlayer

@Composable
fun LeanbackVideoScreen(
    modifier: Modifier = Modifier,
    state: LeanbackVideoPlayerState = rememberLeanbackVideoPlayerState(),
    showMetadataProvider: () -> Boolean = { false },
) {
    val context = LocalContext.current
    val childPadding = rememberLeanbackChildPadding()
    val settingsViewModel: LeanbackSettingsViewModel = viewModel()

    Box(modifier = modifier.fillMaxSize()) {
        when(settingsViewModel.videoPlayer){
            Constants.GSY_VIDEO_PLAYER ->
                AndroidView(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .aspectRatio(state.aspectRatio),
                    factory = {
                        //使用GSY播放
                        val gsyView= TVGSYVideoPlayer(context)
                        state.setGSYView(gsyView)
                        when(settingsViewModel.videoPlayerProtocol){
                            Constants.TCP_PLAYER_PROTOCOL ->{
                                val gsyVideoManager=gsyView.getGSYVideoManager() as GSYVideoManager
                                val newList = arrayListOf<VideoOptionModel>(
                                    VideoOptionModel(1,"rtsp_transport", "tcp")
                                )
                                gsyVideoManager.optionModelList = newList
                            }
                        }
                        gsyView
                    },
                    update = { gsyView ->
                        state.setGSYView(gsyView)
                    },
                )
            else ->
                AndroidView(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .aspectRatio(state.aspectRatio),
                    factory = {
                        // PlayerView 切换视频时黑屏闪烁，使用 SurfaceView 代替
                        SurfaceView(context)
                    },
                    update = { surfaceView ->
                        state.setVideoSurfaceView(surfaceView)
                    },
                )
        }

        LeanbackVideoPlayerErrorScreen(
            errorProvider = { state.error },
        )

        if (showMetadataProvider()) {
            LeanbackVideoPlayerMetadata(
                modifier = Modifier.padding(start = childPadding.start, top = childPadding.top),
                metadata = state.metadata,
            )
        }
    }
}