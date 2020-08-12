package com.qihoo.kids.allinonedemo.bindadapter

import android.content.Context
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.qihoo.kids.allinonedemo.R
import java.lang.NumberFormatException

/**
 * @author yolo.huang
 * @date 2020/8/11
 */
object BindingAdapter {

    @BindingAdapter("onFocusChanged")
    @JvmStatic fun onFocusChanged(view:EditText,listener:View.OnFocusChangeListener?){
        view.onFocusChangeListener = View.OnFocusChangeListener{ focusedView, hasFocus ->
            val textView = focusedView as TextView
            if(hasFocus){
                view.setTag(R.id.previous_value,view.text)
                textView.text = ""
            }else{
                if(textView.text.isEmpty()){
                    val tag:CharSequence? = view.getTag(R.id.previous_value) as CharSequence
                    textView.text = tag?:""
                }
                listener?.onFocusChange(focusedView,hasFocus)
            }
        }
    }

    @BindingAdapter(value = ["max","progress"],requireAll = true)
    @JvmStatic fun setProgress(view:ProgressBar,max:Int,progress:Int){
        view.max = max
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
            view.setProgress(progress,false)
        }else{
            view.progress = progress
        }

    }

    @BindingAdapter("clearTextOnFocus")
    @JvmStatic fun clearTextOnFocus(view: EditText,clear:Boolean){
        if(clear) onFocusChanged(view,null)
    }

    @BindingAdapter("loseFocusWhen")
    @JvmStatic fun loseFocusWhen(view: EditText, condition: Boolean) {
        if (condition) view.clearFocus()
    }

    @Suppress("unused")
    @BindingAdapter("goneUnless")
    @JvmStatic fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }


    @BindingAdapter("hideKeyboardOnInputDone")
    @JvmStatic fun hideKeyboardOnInputDone(view:EditText,hide:Boolean){
        if(!hide) return
        val listener = TextView.OnEditorActionListener{ _, actionId,_ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                view.clearFocus()
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken,0)
            }
            false
        }

        view.setOnEditorActionListener(listener)
    }


}