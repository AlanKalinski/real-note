package me.kalinski.realnote.ui.fragments.share

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.rengwuxian.materialedittext.MaterialEditText
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseFragment
import me.kalinski.realnote.di.activities.utils.Displayable
import me.kalinski.realnote.ui.activities.details.DetailsActivity
import me.kalinski.realnote.ui.fragments.share.adapter.ShareListAdapter
import me.kalinski.realnote.ui.fragments.share.adapter.ShareUserWrapper
import me.kalinski.utils.adapters.universalrecycler.listeners.RowItemClick
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class ShareFragment : BaseFragment(), IShareView, Displayable {

    @Inject
    lateinit var presenter: ISharePresenter

    val searchBar by lazy { find<MaterialEditText>(R.id.searchBar) }
    val resultBox by lazy {
        val recycler = find<RecyclerView>(R.id.resultBox)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler
    }
    val shareButton by lazy { find<Button>(R.id.shareButton) }

    val adapter by lazy {
        ShareListAdapter(object : RowItemClick<ShareUserWrapper> {
            override fun onClickItem(item: ShareUserWrapper) {

            }
        })
    }

    val layoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun getDisplayItem(): BaseFragment = this

    override fun getTagName(): String = "SHARE_FRAGMENT"

    override fun canShow(): Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this)
        presenter.loadUsers()

        initViewComponents()
        initListeners()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewComponents() {
        shareButton
        resultBox
        searchBar
    }

    private fun initListeners() {
        shareButton.setOnClickListener {
            val usersToShare = adapter.itemList.filter { it.checked }
            (activity as? DetailsActivity)?.note?.let {
                if (usersToShare.isNotEmpty()) presenter.shareNote(it, usersToShare)
            }
        }
    }

    override fun showErrorSharing() {
        toast("Sharing error occured.")
    }

    override fun navigateBack() {
        activity?.onBackPressed()
    }

    override fun showErrorLoading() {
        toast("Loading users error.")
    }

    override fun showUsers(it: List<ShareUserWrapper>) {
        adapter.itemList = it.sortedBy { it.displayName }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}