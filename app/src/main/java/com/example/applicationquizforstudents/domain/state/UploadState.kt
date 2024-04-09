package com.example.applicationquizforstudents.domain.state


sealed class UploadState {
    class Success(val url:String) : UploadState()

    class Error(val e: Exception) : UploadState()

    class Loading(val progress : Pair<Long,Long>) : UploadState()

    object Nothing : UploadState()
}