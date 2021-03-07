package com.egoriku.radiotok

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.data.entity.StationEntity
import com.egoriku.radiotok.ui.theme.RadioTokTheme
import com.egoriku.radiotok.viewmodel.RadioViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {

    private val viewModel: RadioViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RadioTokTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val radioStations by viewModel.stations.collectAsState()
                    LazyColumn {
                        items(items = radioStations) {
                            RadioItem(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadioItem(entity: StationEntity) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                CoilImage(
                    data = entity.favicon,
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )

                Text(text = entity.name, modifier = Modifier.padding(16.dp))
            }

            LazyRow(
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tags = entity.tags.split(",").toTypedArray()

                items(tags) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .border(
                                1.dp,
                                LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                                CircleShape
                            )
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}