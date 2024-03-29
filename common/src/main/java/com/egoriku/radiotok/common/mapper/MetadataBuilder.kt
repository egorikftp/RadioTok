package com.egoriku.radiotok.common.mapper

import com.egoriku.radiotok.common.ext.toFlagEmoji

object MetadataBuilder {

    fun build(countryCode: String, tags: String) = buildList {
        if (countryCode.isNotEmpty()) {
            add(countryCode.toFlagEmoji)
        }

        if (tags.isNotEmpty()) {
            add(
                tags
                    .replace(",,", ",")
                    .replace(",", ", ")
            )
        }
    }.joinToString(separator = " ")
}