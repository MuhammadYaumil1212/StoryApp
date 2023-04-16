package org.d3if00001.storyapp.presentations.ui.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import org.d3if00001.storyapp.R

class PasswordEditText : AppCompatEditText,View.OnTouchListener {
    private lateinit var clearButtonImage: Drawable
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    fun showClearButton() =  setButtonDrawable(endOfTheText=clearButtonImage)
    private fun hideClearButton() =  setButtonDrawable()

    private fun setButtonDrawable(
        startOfTheText:Drawable = ContextCompat.getDrawable(context,R.drawable.password) as Drawable,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText:Drawable? = null

    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )

    }

    private fun init(){
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24dp) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (text.toString().isNotEmpty()) showClearButton() else hideClearButton()
                error = if (text != null && text!!.length < 8) {
                    "Password harus minimal 8 karakter"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if(compoundDrawables[2] != null){
            val clearButtonStart : Float
            val clearButtonEnd : Float
            var isClearButtonClicked = false
            if(layoutDirection == View.LAYOUT_DIRECTION_RTL){
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                when{
                    event?.x!! < clearButtonEnd -> isClearButtonClicked = true
                }
            }else{
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                when{
                    event?.x!! > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if(isClearButtonClicked){
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> {
                        clearButtonImage = ContextCompat.getDrawable(context,R.drawable.ic_close_black_24dp) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearButtonImage = ContextCompat.getDrawable(context,R.drawable.ic_close_black_24dp) as Drawable
                        when{
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                    }
                    else  -> return false
                }
            }else return false
        }
        return false
    }
}