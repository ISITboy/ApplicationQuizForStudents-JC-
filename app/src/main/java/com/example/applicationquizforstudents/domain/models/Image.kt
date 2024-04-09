package com.example.applicationquizforstudents.domain.models

data class Image(
    val id :Int = 0,
    val url:String,
    val description:String,
){
    constructor() : this(0, "", "")
}
