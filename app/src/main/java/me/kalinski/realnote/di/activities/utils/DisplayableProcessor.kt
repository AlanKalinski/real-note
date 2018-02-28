package me.kalinski.realnote.di.activities.utils

import me.kalinski.realnote.di.scope.ApplicationScope
import timber.log.Timber
import javax.inject.Inject

@ApplicationScope
class DisplayableProcessor @Inject constructor() {

    private var displayableList: MutableList<Displayable> = mutableListOf()
    private var displayInProgress: Boolean = false

    private var startQueue: ((item: Displayable) -> Unit)? = null
    private var close: ((item: Displayable) -> Unit)? = null

    fun hasMoreInQueue() = displayableList.size > 0
    fun firstInQueue() = if (displayableList.size > 0) displayableList[0] else null

    fun attachView(startQueue: ((item: Displayable) -> Unit), close: ((item: Displayable) -> Unit)) {
        this.startQueue = startQueue
        this.close = close
        if (displayInProgress) show()
    }

    fun detachView() {
        this.startQueue = null
        this.close = null
    }

    fun addToQueue(displayable: Displayable) {
        addToQueue(displayable, displayableList.size)
    }

    fun addToQueue(displayable: Displayable, position: Int) {
        this.displayableList.add(position, displayable)
    }

    fun show() {
        displayInProgress = true
        if (displayableList.size == 0) {
            Timber.w("Nothing Displayable element to display")
            displayInProgress = false
            return
        }
        startQueue?.invoke(displayableList[0])
    }

    fun hide() {
        if (displayableList.size == 0) {
            Timber.w("Nothing Displayable element to hide")
            displayInProgress = false
            return
        }
        val itemToRemove = displayableList[0]
        displayableList.remove(itemToRemove)
        close?.invoke(itemToRemove)
    }

    fun hide(tag: String) {
        if (displayableList.size == 0) {
            Timber.w("Nothing Displayable element to hide")
            return
        }
        if (displayableList[0].getTagName().equals(tag, true)) {
            val itemToRemove = displayableList[0]
            displayableList.remove(itemToRemove)
            close?.invoke(itemToRemove)
        }
    }
}