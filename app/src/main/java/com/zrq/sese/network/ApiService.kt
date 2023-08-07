package com.zrq.sese.network

import com.google.gson.JsonObject
import retrofit2.http.*

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/8/4 18:09
 */
interface ApiService {

    @FormUrlEncoded
    @POST("/threads/video-comments/get-posts/top/{id}/0/0")
    suspend fun loadComment(
        @Path("id") id :String,
        @FieldMap map: Map<String, @JvmSuppressWildcards Any?>
    ): JsonObject
}