package com.qihoo.kids.allinonedemo.bindadapter

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseMethod
import com.qihoo.kids.allinonedemo.R
import java.lang.NumberFormatException

/**
 * @author yolo.huang
 * @date 2020/8/11
 */
object NumberBindingAdapter {

    @BindingAdapter("numberOfSets")
    @JvmStatic fun setNumberOfSets(view:EditText,value:String){
        view.setText(value)
    }

    @InverseBindingAdapter(attribute = "numberOfSets")
    @JvmStatic fun getNumberOfSets(view: EditText):String{
        return view.text.toString()
    }

    @BindingAdapter("numberOfSetsAttrChanged")
    @JvmStatic fun setListener(view: EditText, listener: InverseBindingListener?) {
        view.onFocusChangeListener = View.OnFocusChangeListener { focusedView, hasFocus ->
            val textView = focusedView as TextView
            if (hasFocus) {
                // Delete contents of the EditText if the focus entered.
                textView.text = ""
            } else {
                // If the focus left, update the listener
                listener?.onChange()
            }
        }
    }
}

object NumberConverter{


    @InverseMethod("stringToSetArray")
    @JvmStatic fun setArrayToString(context: Context,value:Array<Int>):String{
        return context.getString(R.string.sets_format,value[0]+1,value[1])
    }

    @JvmStatic fun stringToSetArray(context: Context,value:String):Array<Int>{
        if(value.isEmpty()){
            return arrayOf(0,0)
        }
        return try{
            arrayOf(0,value.toInt())
        }catch (e:NumberFormatException){
            arrayOf(0,0)
        }
    }

}