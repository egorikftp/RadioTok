package com.egoriku.radiotok.presentation.screen.playlist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.foundation.NetworkImage

@Composable
fun RadioListItem(radioItemModel: RadioItemModel) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetworkImage(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
            data = radioItemModel.icon,
            error = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_radio),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(id = R.string.cc_radio_logo_error)
                )
            }
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f)
        ) {
            Text(
                text = radioItemModel.title,
                style = typography.h6.copy(fontSize = 16.sp),
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = radioItemModel.metadata,
                style = typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.padding(4.dp)
        )
    }
}