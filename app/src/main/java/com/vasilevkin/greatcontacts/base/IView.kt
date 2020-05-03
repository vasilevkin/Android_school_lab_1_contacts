package com.vasilevkin.greatcontacts.base


interface IView {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
}