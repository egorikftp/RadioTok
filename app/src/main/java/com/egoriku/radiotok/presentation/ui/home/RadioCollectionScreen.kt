package com.egoriku.radiotok.presentation.ui.home

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
import com.egoriku.radiotok.foundation.HSpacer

@Composable
fun RadioCollectionScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    val forYou: List<RadioCollection> = listOf(
        RadioCollection("Random", R.drawable.ic_random),
        RadioCollection("Liked", R.drawable.ic_favorite),
        RadioCollection("Trending Now", R.drawable.ic_trending_up)
    )

    val byGenre: List<RadioCollection> = listOf(
        RadioCollection("Folk", R.drawable.ic_random),
        RadioCollection("Dance", R.drawable.ic_favorite),
        RadioCollection("News", R.drawable.ic_trending_up)
    )

    val byCountry: List<RadioCollection> = listOf(
        RadioCollection("Poland", R.drawable.ic_random),
        RadioCollection("USA", R.drawable.ic_favorite),
        RadioCollection("NL", R.drawable.ic_trending_up)
    )

    Surface(modifier = modifier) {
        LazyColumn(contentPadding = paddingValues) {
            item {
                CollectionHeader("For you")
                RadioItemsRow(items = forYou)
            }

            item {
                CollectionHeader("By Genres")
                RadioItemsRow(items = byGenre)
            }

            item {
                CollectionHeader("By Country")
                RadioItemsRow(items = byCountry)
            }

            item {
                CollectionHeader("By Country")
                RadioItemsRow(items = byCountry)
            }
        }
    }
}

@Composable
fun CollectionHeader(title: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
        text = title,
        style = MaterialTheme.typography.h6
    )
}

data class RadioCollection(
    val name: String,
    val drawableId: Int
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
            Icon(
                painter = painterResource(id = collection.drawableId),
                contentDescription = null
            )
            HSpacer(16.dp)
            Text(text = collection.name, style = MaterialTheme.typography.h5)
        }
    }
}