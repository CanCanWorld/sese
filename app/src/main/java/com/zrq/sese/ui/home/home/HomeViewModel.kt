package com.zrq.sese.ui.home.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zrq.sese.base.BaseViewModel
import com.zrq.sese.entity.VideoItem
import com.zrq.sese.utils.Covert.picToVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.SocketTimeoutException

class HomeViewModel : BaseViewModel() {

    val list = MutableLiveData(mutableListOf<VideoItem>())

    fun load(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val l = mutableListOf<VideoItem>()
            try {
                val doc = Jsoup.connect(url).get()
                val elements = doc.getElementsByClass("thumb-block")
                elements.forEach {
                    val id = it.attr("data-id")
                    val thumbUnder = it.getElementsByClass("thumb-under")[0]
                    val titleTag = thumbUnder.getElementsByClass("title")[0].getElementsByClass("title")[0].getElementsByTag("a")[0]
                    val name = thumbUnder.getElementsByClass("name").text()
                    val title = titleTag.attr("title")
                    val path = titleTag.attr("href")
                    val cover = it.getElementsByClass("thumb-inside")[0].getElementsByClass("thumb")[0].getElementsByTag("a")[0].getElementsByTag("img")[0].attr("data-src")
                    val preview = picToVideo(cover)
                    val duration = thumbUnder.getElementsByClass("duration")[0].text()
                    val videoItem = VideoItem(id, title, path, cover, preview, name, duration)
                    Log.d(TAG, "videoItem: $videoItem")
                    l.add(videoItem)
                }
                list.postValue(l)
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    when (e) {
                        is SocketTimeoutException -> {
                            Toast.makeText(context, "请科学上网", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    list.postValue(l)
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}