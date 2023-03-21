package com.shunyank.cyberdost.models

data class UrlStats(
    val harmless:Int,
    val malicious:Int,
    val suspicious:Int,
    val undetected:Int,
)
