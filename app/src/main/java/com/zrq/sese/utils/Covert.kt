package com.zrq.sese.utils

object Covert {

    //https://img-cf.xvideos-cdn.com/videos/videopreview/06/69/eb/0669eb02197aaf29a77b51eb72d252bb_169-2.mp4
    //https://img-cf.xvideos-cdn.com/videos/videopreview/06/69/eb/0669eb02197aaf29a77b51eb72d252bb-2.mp4
    //https://img-cf.xvideos-cdn.com/videos/thumbs169/06/69/eb/0669eb02197aaf29a77b51eb72d252bb-2/0669eb02197aaf29a77b51eb72d252bb.17.jpg

    //https://img-egc.xvideos-cdn.com/videos/thumbs169ll/63/12/d8/6312d87848556b57f308db846aacb6fe/6312d87848556b57f308db846aacb6fe.7.jpg
    //https://img-egc.xvideos-cdn.com/videos/videopreview/63/12/d8/6312d87848556b57f308db846aacb6fe_169-2.mp4

    //https://img-egc.xvideos-cdn.com/videos/thumbs169ll/e0/fb/38/e0fb387effe0e47b62a92605375a0fc1/e0fb387effe0e47b62a92605375a0fc1.5.jpg
    //https://img-egc.xvideos-cdn.com/videos/videopreview/e0/fb/38/e0fb387effe0e47b62a92605375a0fc1_169.mp4
    fun picToVideo(cover: String): String {
        val index = cover.lastIndexOf('/')
        var target = cover.removeRange(index, cover.length)
        val indexOf = target.indexOf("/thumbs")
        val first = target.subSequence(0, indexOf)
        val sequence = target.subSequence(indexOf + 1, target.length)
        val last = sequence.subSequence(sequence.indexOf('/'), sequence.length)
        target = "${first}/videopreview${last}_169.mp4"
        return target
    }

}