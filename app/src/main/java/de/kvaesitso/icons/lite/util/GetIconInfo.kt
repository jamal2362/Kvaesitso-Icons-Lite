package de.kvaesitso.icons.lite.util

import android.content.Context
import de.kvaesitso.icons.lite.R
import de.kvaesitso.icons.lite.model.IconInfo
import org.xmlpull.v1.XmlPullParser

fun Context.getIconInfo(): List<IconInfo> {
    val iconInfo = mutableListOf<IconInfo>()

    try {
        val xmlId = R.xml.grayscale_icon_map
        val parser = resources.getXml(xmlId)
        val depth = parser.depth
        var type: Int
        while (
            (
                parser.next()
                    .also { type = it } != XmlPullParser.END_TAG || parser.depth > depth
                ) &&
            type != XmlPullParser.END_DOCUMENT
        ) {
            if (type != XmlPullParser.START_TAG) continue
            if ("icon" == parser.name) {
                val pkg = parser.getAttributeValue(null, "package")
                val iconName = parser.getAttributeValue(null, "name")
                val iconId = parser.getAttributeResourceValue(null, "drawable", 0)
                val iconDrawable = resources.getResourceEntryName(iconId)
                if (iconId != 0 && pkg.isNotEmpty()) {
                    iconInfo += IconInfo(iconName, iconDrawable, pkg, iconId)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return iconInfo
}
