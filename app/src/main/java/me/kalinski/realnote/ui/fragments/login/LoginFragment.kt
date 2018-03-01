package me.kalinski.realnote.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.rengwuxian.materialedittext.MaterialEditText
import me.kalinski.realnote.R
import me.kalinski.realnote.di.activities.BaseFragment
import me.kalinski.realnote.di.activities.utils.Displayable
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

class LoginFragment : BaseFragment(), ILoginView, Displayable {

    @Inject
    lateinit var presenter: ILoginPresenter

    override fun getDisplayItem(): BaseFragment = this

    override fun getTagName(): String = "LOGIN_VIEW"

    override fun canShow(): Boolean = true

    val actionButton by lazy { find<Button>(R.id.actionButton) }
    val changeStateButton by lazy { find<Button>(R.id.changeStateButton) }
    val closeButton by lazy { find<Button>(R.id.closeButton) }

    val login by lazy { find<MaterialEditText>(R.id.login) }
    val password by lazy { find<MaterialEditText>(R.id.password) }
    val repeat by lazy { find<MaterialEditText>(R.id.repeat) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initViewComponents()
        initListeners()
    }

    private fun initViewComponents() {
        actionButton
        changeStateButton
        closeButton

        login
        password
        repeat

        presenter.reloadViewDependOnMode()
    }

    private fun initListeners() {
        closeButton.setOnClickListener { activity?.onBackPressed() }
        actionButton.setOnClickListener {
            presenter.doAction(
                    login.text.toString(),
                    password.text.toString(),
                    repeat.text.toString()
            )
        }
        changeStateButton.setOnClickListener { presenter.changeMode() }
    }

    override fun showLoginView() {
        repeat.visibility = View.GONE
        actionButton.text = getString(R.string.login_action)
        changeStateButton.text = getString(R.string.register_button)
    }

    override fun showRegisterView() {
        repeat.visibility = View.VISIBLE
        actionButton.text = getString(R.string.register_action)
        changeStateButton.text = getString(R.string.login_button)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}