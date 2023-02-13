package com.newsblur.keyboard

import android.content.Context
import android.content.res.Configuration
import android.view.KeyEvent

class KeyboardManager {

    private var listener: KeyboardListener? = null

    fun addListener(listener: KeyboardListener) {
        this.listener = listener
    }

    fun removeListener() {
        this.listener = null
    }

    /**
     * @return Return <code>true</code> to prevent this event from being propagated
     * further, or <code>false</code> to indicate that you have not handled
     */
    fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean = when (keyCode) {
        /**
         * Home events
         */
        KeyEvent.KEYCODE_E -> {
            handleKeycodeE(event)
        }
        KeyEvent.KEYCODE_A -> {
            handleKeycodeA(event)
        }
        KeyEvent.KEYCODE_DPAD_RIGHT -> {
            listener?.onKeyboardEvent(KeyboardEvent.SwitchViewRight)
            true
        }
        KeyEvent.KEYCODE_DPAD_LEFT -> {
            listener?.onKeyboardEvent(KeyboardEvent.SwitchViewLeft)
            true
        }
        /**
         * Story events
         */
        KeyEvent.KEYCODE_J -> {
            listener?.onKeyboardEvent(KeyboardEvent.PreviousStory)
            true
        }
        KeyEvent.KEYCODE_K -> {
            listener?.onKeyboardEvent(KeyboardEvent.NextStory)
            true
        }
        KeyEvent.KEYCODE_N -> {
            listener?.onKeyboardEvent(KeyboardEvent.NextUnreadStory)
            true
        }
        KeyEvent.KEYCODE_W -> {
            listener?.onKeyboardEvent(KeyboardEvent.ToggleTextView)
            true
        }
        KeyEvent.KEYCODE_U, KeyEvent.KEYCODE_M -> {
            listener?.onKeyboardEvent(KeyboardEvent.ToggleReadUnread)
            true
        }
        KeyEvent.KEYCODE_S -> {
            if (event.isShiftPressed) listener?.onKeyboardEvent(KeyboardEvent.ShareStory)
            else listener?.onKeyboardEvent(KeyboardEvent.SaveUnsaveStory)
            true
        }
        KeyEvent.KEYCODE_O, KeyEvent.KEYCODE_V -> {
            listener?.onKeyboardEvent(KeyboardEvent.OpenInBrowser)
            true
        }
        KeyEvent.KEYCODE_C -> {
            listener?.onKeyboardEvent(KeyboardEvent.ScrollToComments)
            true
        }
        KeyEvent.KEYCODE_T -> {
            listener?.onKeyboardEvent(KeyboardEvent.OpenStoryTrainer)
            true
        }
        else -> false
    }

    private fun handleKeycodeE(event: KeyEvent): Boolean = if (event.isAltPressed) {
        listener?.onKeyboardEvent(KeyboardEvent.OpenAllStories)
        true
    } else false

    private fun handleKeycodeA(event: KeyEvent): Boolean = if (event.isAltPressed) {
        listener?.onKeyboardEvent(KeyboardEvent.AddFeed)
        true
    } else false

    fun isKnownKeyCode(keyCode: Int, overridePad: Boolean = false): Boolean {
        val isShortcutKey = isShortcutKeyCode(keyCode)
        return if (overridePad) isShortcutKey && isPadKeyCode(keyCode)
        else isShortcutKey
    }

    private fun isPadKeyCode(keyCode: Int) = when (keyCode) {
        KeyEvent.KEYCODE_DPAD_LEFT,
        KeyEvent.KEYCODE_DPAD_RIGHT,
        -> true
        else -> false
    }

    private fun isShortcutKeyCode(keyCode: Int) = when (keyCode) {
        KeyEvent.KEYCODE_E,
        KeyEvent.KEYCODE_A,
        KeyEvent.KEYCODE_J,
        KeyEvent.KEYCODE_K,
        KeyEvent.KEYCODE_N,
        KeyEvent.KEYCODE_U,
        KeyEvent.KEYCODE_M,
        KeyEvent.KEYCODE_S,
        KeyEvent.KEYCODE_O,
        KeyEvent.KEYCODE_V,
        KeyEvent.KEYCODE_C,
        KeyEvent.KEYCODE_T,
        KeyEvent.KEYCODE_W,
        -> true
        else -> false
    }

    companion object {

        @JvmStatic
        fun hasHardwareKeyboard(context: Context) =
                context.resources.configuration.keyboard == Configuration.KEYBOARD_QWERTY
    }
}