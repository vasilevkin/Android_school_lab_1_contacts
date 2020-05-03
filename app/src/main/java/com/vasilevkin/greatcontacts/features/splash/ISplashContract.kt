package com.vasilevkin.greatcontacts.features.splash

import com.vasilevkin.greatcontacts.base.BaseContract


interface ISplashContract {

    interface Presenter : BaseContract.Presenter {
        fun onViewDestroyed()
    }

    interface View : BaseContract.View {
        fun finishView()
    }
}
