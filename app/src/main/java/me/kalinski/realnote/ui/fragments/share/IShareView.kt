package me.kalinski.realnote.ui.fragments.share

import me.kalinski.realnote.di.base.BaseMvp
import me.kalinski.realnote.ui.fragments.share.adapter.ShareUserWrapper

interface IShareView : BaseMvp.MvpView {
    fun showErrorLoading()
    fun showUsers(it: List<ShareUserWrapper>)
    fun showErrorSharing()
    fun navigateBack()
}