package com.mustafa.giphy.utilities

import android.view.View

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: Giphy
 * Package: com.mustafa.giphy.utilities
 * Date: 8/22/2021
 */

interface SnackbarUndoListener : View.OnClickListener {
    override fun onClick(v: View) {
        snackbarUndoClick()
    }

    fun snackbarUndoClick()
}