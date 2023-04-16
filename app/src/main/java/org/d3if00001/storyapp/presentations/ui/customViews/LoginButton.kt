package org.d3if00001.storyapp.presentations.ui.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import org.d3if00001.storyapp.R

class LoginButton : AppCompatButton {
    private lateinit var enabledBackground:Drawable
    private lateinit var disabledBackground:Drawable
    private var txtColor:Int = 0
    constructor(context:Context) : super(context){
        init()
    }
    constructor(context:Context, attrs:AttributeSet) : super(context,attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = if(isEnabled) "Login" else "Harap isi Form Login"
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.color.brown2_200) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disabled) as Drawable
    }

}