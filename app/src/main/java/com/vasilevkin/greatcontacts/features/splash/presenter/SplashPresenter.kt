package com.vasilevkin.greatcontacts.features.splash.presenter

import com.vasilevkin.greatcontacts.base.BasePresenter
import com.vasilevkin.greatcontacts.features.splash.ISplashContract


class SplashPresenter : BasePresenter<ISplashContract.View>(),
    ISplashContract.Presenter {

    override fun onViewCreated() {
        view?.finishView()
    }

    override fun onViewDestroyed() {
        view = null
    }
}
