package com.nancy.labellens.presentation.autofill.smartfill


fun parseSmartFillText(text: String): SmartFillResult {

    val lines = text.lines()

    val title = lines.firstOrNull().orEmpty()

    val date = lines.find {
        it.contains("202", true) || it.contains("date", true)
    }.orEmpty()

    val location = lines.find {
        it.contains("location", true) ||
                it.contains("venue", true) ||
                it.contains("address", true)
    }.orEmpty()

    return SmartFillResult(
        title = title,
        date = date,
        location = location
    )
}
