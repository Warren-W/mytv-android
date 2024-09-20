package top.yogiczy.mytv.ui.screens.leanback.video.player

import android.content.Context
import android.view.MotionEvent
import android.view.View
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import top.yogiczy.mytv.R


class TVGSYVideoPlayer(context: Context?) : StandardGSYVideoPlayer(context) {

    override fun getLayoutId(): Int {
        return R.layout.tv_layout_standard
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }
}