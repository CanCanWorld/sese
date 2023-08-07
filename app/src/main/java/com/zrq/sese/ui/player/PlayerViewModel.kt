package com.zrq.sese.ui.player

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.zrq.sese.base.BaseViewModel
import com.zrq.sese.entity.CommentItem
import com.zrq.sese.network.entity.RelateEntity
import com.zrq.sese.network.entity.RelateEntityItem
import org.jsoup.Jsoup
import java.util.regex.Pattern

class PlayerViewModel : BaseViewModel() {

    var id = ""

    val comments = MutableLiveData(mutableListOf<CommentItem>())

    val commentSum = MutableLiveData(0)

    val relates = MutableLiveData(mutableListOf<RelateEntityItem>())

    private var high = ""
    private var low = ""
    private var hls = ""

    fun loadVideo(path: String, callback: (String) -> Unit) {
        Thread {
            Log.d(TAG, "path: $path")
            val doc = Jsoup.connect("https://xvideos.com$path").get()
            val element = doc.getElementsByTag("script")
            element.forEach {
                if (it.toString().contains(".mp4", true)) {
                    Log.d(TAG, "it: $it")
                    val pattern = Pattern.compile("setVideoUrlHigh.*\'.*mp4.*\'")
                    val pattern2 = Pattern.compile("setVideoUrlLow.*\'.*mp4.*\'")
                    val pattern3 = Pattern.compile("setVideoHLS.*\'.*m3u8.*\'")
                    val matcher = pattern.matcher(it.toString())
                    val matcher2 = pattern2.matcher(it.toString())
                    val matcher3 = pattern3.matcher(it.toString())
                    while (matcher.find()) {
                        Log.d(TAG, "matcher: ${matcher.group()}")
                        high = matcher.group().replaceBefore("http", "").replace("'", "")
                        Log.d(TAG, "High: $high")

                    }
                    while (matcher2.find()) {
                        Log.d(TAG, "matcher2: ${matcher2.group()}")
                        low = matcher2.group().replaceBefore("http", "").replace("'", "")
                        Log.d(TAG, "Low: $low")
                    }
                    while (matcher3.find()) {
                        Log.d(TAG, "matcher3: ${matcher3.group()}")
                        hls = matcher3.group().replaceBefore("http", "").replace("'", "")
                        Log.d(TAG, "hls: $hls")
                    }
                    when {
                        hls != "" -> {
                            callback(hls)
                        }
                        high != "" -> {
                            callback(high)
                        }
                        else -> {
                            callback(low)
                        }
                    }
                }
            }
            val script = doc.getElementById("video-player-bg")?.getElementsByTag("script")?.get(0)
            val pattern = Pattern.compile("video_related=.*;window.wpn_categories")
            val matcher = pattern.matcher(script.toString())
            while (matcher.find()) {
                val relate = matcher.group().replace("video_related=", "").replace(";window.wpn_categories", "")
                val relateEntity = Gson().fromJson(relate, RelateEntity::class.java)
                relates.postValue(relateEntity)
            }

        }.start()
    }

    fun loadComment() {
        launch(
            block = {
                Log.d(TAG, "id: $id")
                val map = HashMap<String, Any?>()
                map["load_all"] = 1
                val loadComment = apiService.loadComment(id, map)
                val posts = loadComment.getAsJsonObject("posts")
                val posts2 = posts.getAsJsonObject("posts")
                val ids = posts.getAsJsonArray("ids")
                commentSum.value = ids.size()
                val list = mutableListOf<CommentItem>()
                ids.forEach {
                    val item = posts2.getAsJsonObject(it.toString().replace("\"", ""))
                    val name = (item.get("name") ?: "未知").toString().replace("\"", "")
                    val message = (item.get("message") ?: "未知").toString().replace("\"", "")
                    val pic = (item.get("pic") ?: "未知").toString().replace("\"", "")
                    val country = (item.get("country_name") ?: "未知").toString().replace("\"", "")
                    val time = (item.get("time_diff") ?: "未知").toString().replace("\"", "")
                    list.add(CommentItem(name, message, pic, country, time))
                }
                comments.postValue(list)
            }
        )
    }

    companion object {
        const val TAG = "CommentViewModel"
    }
}