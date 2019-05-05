package xyz.czanik.indoorway_countries

import org.json.JSONObject

fun JSONObject.toJsonString() : String {
    val builder = StringBuilder().append("{")
    var prefix = ""
    keys().forEach {
        val key = "\"$it\""
        val rawValue = getString(it)
        val value = if(rawValue.startsWith("[") || rawValue.startsWith("{")) rawValue else "\"$rawValue\""
        builder.append(prefix).append("$key:$value")
        prefix = ","
    }
   return builder.append("}").toString()
}