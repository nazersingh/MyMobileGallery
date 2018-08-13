package com.example.saini.mymobilegallery

interface CallBackInterface<T> {
    fun onSuccess(t: T)
    fun onFailure(throwable: Throwable)
}