package com.qihoo.kids.allinonedemo.bindadapter

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.qihoo.kids.allinonedemo.R

/**
 * @author yolo.huang
 * @date 2020/8/12
 */
object AnimationBindingAdapter {

    private const val VERTICAL_BIAS_ANIMATION_DURATION = 900L

    private const val BG_COLOR_ANIMATION_DURATION = 500L

    @BindingAdapter(value = ["animationBackground","animationStage"],requireAll = true)
    @JvmStatic fun animationBackground(view: View, state:Boolean, stage:Boolean){
        if(!state){
            view.setBackgroundColor(ContextCompat.getColor(view.context,R.color.colorBackgroundStop))
            view.setTag(R.id.hasBeenAnimated,false)
            return
        }
        if (stage){
            animationColor(view,true)
            view.setTag(R.id.hasBeenAnimated,true)
        }else{
            val hasBeenAnimated = view.getTag(R.id.hasBeenAnimated) as Boolean
            if(hasBeenAnimated){
                animationColor(view,false)
                view.setTag(R.id.hasBeenAnimated,false)
            }
        }
    }

    @BindingAdapter(value = ["animationBiasState","animationBiasStage"],requireAll = true)
    @JvmStatic fun animationBias(view:View,timeRunning:Boolean,stage:Boolean){
        when{
            timeRunning && stage -> animationProgressBias(view,0.6f)
            timeRunning && !stage -> animationProgressBias(view,0.4f)
            else -> animationProgressBias(view,0.5f)
        }

    }

    @JvmStatic fun animationColor(view: View,isStart:Boolean){
        val colorStop = ContextCompat.getColor(view.context, R.color.colorBackgroundStop)
        val colorRun = ContextCompat.getColor(view.context,R.color.colorBackgroundRun)

        val animator = if(isStart){
            ObjectAnimator.ofObject(view,
            "backgroundColor",
                ArgbEvaluator(),
                colorStop,
                colorRun
            )
        }else{
            ObjectAnimator.ofObject(view,
                "backgroundColor",
                ArgbEvaluator(),
                colorRun,
                colorStop
            )
        }
        animator.duration = BG_COLOR_ANIMATION_DURATION
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    @JvmStatic fun animationProgressBias(view: View,position:Float){
        val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
        val animator = ValueAnimator.ofFloat(layoutParams.verticalBias,position)
        animator.addUpdateListener { animation ->
            val newParams = view.layoutParams as ConstraintLayout.LayoutParams
            val animationBias = animation.animatedValue as Float
            newParams.verticalBias = animationBias
            view.requestLayout()
        }
        animator.duration = VERTICAL_BIAS_ANIMATION_DURATION
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }


}