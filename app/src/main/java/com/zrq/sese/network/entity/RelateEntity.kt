package com.zrq.sese.network.entity

import androidx.annotation.Keep

@Keep
class RelateEntity : ArrayList<RelateEntityItem>()

@Keep
data class RelateEntityItem(
    val c: Int,
    val ch: Boolean,
    val d: String,
    val fk: Int,
    val h: Int,
    val hm: Int,
    val hp: Int,
    val i: String,
    val id: Int,
    val `if`: String,
    val il: String,
    val ip: String,
    val n: Boolean,
    val p: String,
    val pm: Boolean,
    val pn: String,
    val pu: String,
    val r: String,
    val t: String,
    val td: Int,
    val tf: String,
    val u: String,
    val ui: Int,
    val ut: Boolean,
    val v: Int,
    val ve: Int,
    val vim: Int,
    var isPlayer: Boolean = false
)