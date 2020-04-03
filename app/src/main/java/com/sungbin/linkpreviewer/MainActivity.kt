package com.sungbin.linkpreviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.sungbin.linkpreviewer.library.LinkPreviewer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LinkPreviewer(this)
        val layout = view.getView("https://github.com",
            R.drawable.ic_launcher_foreground, R.string.app_name,
            R.string.app_name, R.string.app_name)
        setContentView(layout)
    }
}
