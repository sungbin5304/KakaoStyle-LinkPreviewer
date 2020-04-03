package com.sungbin.linkpreviewer.library

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


@Suppress("DEPRECATION")
class LinkPreviewer constructor(ctx: Activity){
    private val context = ctx

    private var imageView: ImageView
    private var mainLayout: LinearLayout
    private var titleView: TextView
    private var descriptionView: TextView
    private var urlView: TextView
    private var linkView: TextView
    private var cardView: CardView

    private var useChromeTab = false

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        @SuppressLint("InflateParams")
        val inflater = LayoutInflater.from(context).inflate(R.layout.linkpreviewer_layout, null, false)
        mainLayout = inflater.findViewById(R.id.linkpreviwer_layout)
        imageView = mainLayout.findViewById(R.id.iv_link)
        titleView = mainLayout.findViewById(R.id.link_title)
        descriptionView = mainLayout.findViewById(R.id.link_description)
        urlView = mainLayout.findViewById(R.id.link_url)
        linkView = mainLayout.findViewById(R.id.tv_link)
        cardView = mainLayout.findViewById(R.id.cv_main)
    }

    fun setRadius(radius: Int){
        cardView.radius = radius.toFloat()
    }

    fun getLinkView(): TextView{
        return linkView
    }

    fun setFontFamily(fontRes: Int){
        titleView.typeface = ResourcesCompat.getFont(context, fontRes)
        descriptionView.typeface = ResourcesCompat.getFont(context, fontRes)
        urlView.typeface = ResourcesCompat.getFont(context, fontRes)
        linkView.typeface = ResourcesCompat.getFont(context, fontRes)
    }

    fun showLinkViewUnderLine(){
        linkView.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    fun getView(link: String, defaultImageRes: Int, defaultTitleRes: Int,
                defaultDescriptionRes: Int, defaultUrlRes: Int): LinearLayout{
        linkView.text = link
        linkView.setOnClickListener {
            showWebTab(link)
        }
        cardView.setOnClickListener {
            showWebTab(link)
        }
        val imageUrl = parseOgTagData(link, OgTag.IMAGE, defaultImageRes)
        if(imageUrl != "null") imageView.setImageBitmap(getImageFromWeb(imageUrl))
        else imageView.setImageDrawable(ContextCompat.getDrawable(context, defaultImageRes))
        titleView.text = parseOgTagData(link, OgTag.TITLE, defaultTitleRes)
        descriptionView.text = parseOgTagData(link, OgTag.DESCRIPTION, defaultDescriptionRes)
        urlView.text = parseOgTagData(link, OgTag.URL, defaultUrlRes)
        mainLayout.requestLayout()
        mainLayout.invalidate()
        return mainLayout
    }

    fun setGravity(gravity: Int){
        mainLayout.gravity = gravity
    }

    private fun getImageFromWeb(link: String): Bitmap{
        val url =  URL(link)
        val `is` = url.openStream()
        return BitmapFactory.decodeStream(`is`)
    }

    private fun parseOgTagData(link: String, ogTag: String, defaultRes: Int): String{
        val html = getHtml(link)!!
        var tag = "<meta property=\"og:$ogTag\" content=\""
        if(!html.contains(tag)) tag = "<meta name=\"og:$ogTag\" content=\""
        return if(html.contains(tag)) {
            val result = html.split(tag)[1].split("\"")[0]
            if(ogTag == OgTag.URL){
                if(result.contains("//")) result.split("//")[1]
                else result
            }
            else result
        }
        else {
            if(ogTag == OgTag.IMAGE){
                "null"
            }
            else context.getString(defaultRes)
        }
    }

    fun setUseChromeTab(tf: Boolean){
        useChromeTab = tf
    }

    private fun getHtml(link: String): String? {
        try {
            val url = URL(link)
            val con = url.openConnection()
            if (con != null) {
                con.connectTimeout = 5000
                con.useCaches = false
                val isr = InputStreamReader(con.getInputStream())
                val br = BufferedReader(isr)
                var str = br.readLine()
                var line: String? = ""
                while ({ line = br.readLine(); line }() != null) {
                    str += "\n" + line
                }
                br.close()
                isr.close()
                return str
            }
            return null
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    private fun showWebTab(url: String?) {
        var link = url
        if(!link!!.contains("http")) link = "https://$link"
        try {
            if(useChromeTab) {
                val pm = context.packageManager
                val pi =
                    pm.getPackageInfo("com.android.chrome", PackageManager.GET_META_DATA)

                @Suppress("UNUSED_VARIABLE")
                val appInfo = pi.applicationInfo
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.intent.setPackage("com.android.chrome")
                customTabsIntent.launchUrl(context, Uri.parse(link))
            }
            else {
                val intent = Intent(Intent.ACTION_VIEW)
                val uri: Uri = Uri.parse(link)
                intent.data = uri
                context.startActivity(intent)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                val uri: Uri = Uri.parse(link)
                intent.data = uri
                context.startActivity(intent)
            }
            catch (e: Exception){
                throw e
            }
        }
    }

}