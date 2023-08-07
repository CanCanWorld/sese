package com.zrq.sese.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import xyz.doikki.videoplayer.player.AbstractPlayer;
import xyz.doikki.videoplayer.player.BaseVideoView;


/**
 * 可播放在线和本地url
 * Created by Doikki on 2022/7/18.
 */
public class MyVideoView extends MyBaseVideoView<AbstractPlayer> {
    public MyVideoView(@NonNull Context context) {
        super(context);
    }

    public MyVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
