package com.mustafa.giphy.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.base
 * Date: 8/21/2021
 */

open class BaseFragment<VDB : ViewDataBinding>(private val layoutId: Int) : Fragment() {

    //    lateinit var mFragmentNavigation: FragmentNavigation
    protected lateinit var binding: VDB

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val args = arguments
//        if (args != null) {
//            mInt = args.getInt(ARGS_INSTANCE)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        setupUI()
        return binding.root
    }

    open fun setupUI() {}


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        enterTransition = MaterialFadeThrough()
//        if (context is FragmentNavigation) {
//            mFragmentNavigation = context
//        }
//    }

//    interface FragmentNavigation {
//        fun pushFragment(fragment: Fragment, transactionOptions: FragNavTransactionOptions? = null)
//        fun popFragment()
//        fun badge(show: Boolean, tab: Int = NavControllerBaseActivity.INDEX_COMPANIES) {}
//        fun disablePopFragment(isDisabled: Boolean = true) {}
//    }

    fun Int.toDp(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    inline fun <reified T> start(block: Intent.() -> Unit = {}) {
        startActivity(Intent(activity, T::class.java).apply(block))
    }

    fun EditText.checkInputText(msg: String): Boolean = if (this.text.isBlank()) {
        this.error = msg
        requestFocus(this)
        false
    } else true

    private fun requestFocus(view: View) {
        if (view.requestFocus())
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun String.toast(isLong: Boolean = true, gravityTop: Boolean = false) {
        val t = Toast.makeText(
            context,
            this,
            if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        )
        if (gravityTop) t.setGravity(Gravity.TOP, 0, resources.displayMetrics.heightPixels / 4)
        t.show()
    }

    fun View.snack(text: String, isLong: Boolean = true) {
        Snackbar.make(this, text, if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT)
            .show()
    }

//    fun errorMsg(msg: String?): String = if (msg != Constants.Consts.API_NO_INTERNET && msg != null) msg else context?.getString(R.string.error_message) ?: ""

    // background image changer
//    private lateinit var imageChangerHandler: Handler
//    private lateinit var imageChangerRunnable: Runnable
//    fun ImageView.backgroundImageChanger(urls: ArrayList<String>) {
//        if (urls.isNotEmpty()) {
//            var i = 0
//            imageChangerHandler = Handler()
//            imageChangerRunnable = Runnable {
//                val animation = animate().alpha(0.0F)
//                animation.duration = 250
//                animation.withEndAction { glide(urls[i]) { animate().alpha(1.0F).duration = 250 } }
//                i++
//                if (urls.size == i) i = 0
//                imageChangerHandler.postDelayed(imageChangerRunnable, 5000)
//            }
//            imageChangerHandler.postDelayed(imageChangerRunnable, 0)
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        if (::imageChangerHandler.isInitialized) imageChangerHandler.postDelayed(imageChangerRunnable, 0)
//    }

//    override fun onPause() {
//        super.onPause()
//        if (::imageChangerHandler.isInitialized) imageChangerHandler.removeCallbacks(imageChangerRunnable)
//    }

}
