package com.egoriku.radiotok.presentation.ui.feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.ext.toFlagEmoji
import com.egoriku.radiotok.foundation.HSpacer

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    val instantPlay: List<RadioCollection> = listOf(
        RadioCollection("Random", R.drawable.ic_random),
        RadioCollection("Liked", R.drawable.ic_favorite),
    )

    val forYou: List<RadioCollection> = listOf(
        RadioCollection("Liked", R.drawable.ic_favorite),
        RadioCollection("Disliked", R.drawable.ic_favorite_border),
    )

    val smartPlaylists: List<RadioCollection> = listOf(
        RadioCollection("Local stations"),
        RadioCollection("Top clicks"),
        RadioCollection("Top Vote"),
        RadioCollection("Changed lately"),
        RadioCollection("Playing"),
    )

    val byTags: List<RadioCollection> = listOf(
        RadioCollection("Folk"),
        RadioCollection("Dance"),
        RadioCollection("News")
    )

    val byCountry: List<RadioCollection> = listOf(
        RadioCollection("Pl".toFlagEmoji),
        RadioCollection("US".toFlagEmoji),
        RadioCollection("NL".toFlagEmoji)
    )

    val byLanguage: List<RadioCollection> = listOf(
        RadioCollection("80er"),
        RadioCollection("akan"),
        RadioCollection("all")
    )

    Surface(modifier = modifier) {
        LazyColumn(contentPadding = paddingValues) {
            item {
                CollectionHeader("Instant Play")
                RadioItemsRow(items = instantPlay)
            }
            item {
                CollectionHeader("Personal Playlists")
                RadioItemsRow(items = forYou)
            }
            item {
                CollectionHeader("Smart Playlists")
                RadioItemsRow(items = smartPlaylists)
            }
            item {
                CollectionHeader("By Genres")
                RadioItemsRow(items = byTags)
            }
            item {
                CollectionHeader("By Country")
                RadioItemsRow(items = byCountry)
            }
            item {
                CollectionHeader("By Language")
                RadioItemsRow(items = byLanguage)
            }
        }
    }
}

@Composable
fun CollectionHeader(title: String) {
    Text(
        modifier = Modifier.padding(start = 18.dp, top = 8.dp),
        text = title,
        style = MaterialTheme.typography.h6
    )
}

data class RadioCollection(
    val name: String,
    val drawableId: Int = -1
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RadioItemsRow(items: List<RadioCollection>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items) {
            RadioItem(collection = it)
        }
    }
}

@Composable
fun RadioItem(
    modifier: Modifier = Modifier,
    collection: RadioCollection
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = modifier.size(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (collection.drawableId != -1) {
                Icon(
                    painter = painterResource(id = collection.drawableId),
                    contentDescription = null
                )
                HSpacer(16.dp)
            }

            Text(text = collection.name, style = MaterialTheme.typography.h5)
        }
    }
}