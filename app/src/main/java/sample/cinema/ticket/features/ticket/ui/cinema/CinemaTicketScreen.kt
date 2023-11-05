package sample.cinema.ticket.features.ticket.ui.cinema

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import sample.cinema.ticket.R
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel

@Composable
fun CinemaTicketScreen(
    navController: NavController,
    viewModel: CinemaTicketViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val packedSeatList by viewModel.packedSeatList.collectAsState(initial = null)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(onClick = navController::navigateUp)
                .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(R.drawable.img_projector)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(21f / 9f)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            columns = GridCells.Fixed(3)
        ) {
            itemsIndexed(
                items = packedSeatList ?: emptyList()
            ) { index, item ->
                CinemaTicketPackedSeatListItem(
                    index = index + 1,
                    item = item,
                    onClick = viewModel::toggleSeatSelection
                )
            }
        }
    }
}

@Composable
fun CinemaTicketPackedSeatListItem(
    index: Int,
    item: List<CinemaTicketSeatModel>,
    onClick: (CinemaTicketSeatModel) -> Unit
) {
    var listSize by remember { mutableStateOf(Size.Zero) }

    val rotationDegrees = when {

        index % 3 == 0 -> -8f

        index % 3 == 1 -> 8f

        else -> 0f
    }

    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .height(44.dp)
            .graphicsLayer {
                rotationZ = rotationDegrees
            },
        contentAlignment = if (rotationDegrees == 0f) Alignment.BottomCenter else Alignment.TopCenter
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(
                    width = with(LocalDensity.current) { listSize.width.toDp() + 8.dp },
                    height = with(LocalDensity.current) { listSize.height.toDp() }
                ),
            painter = painterResource(id = R.drawable.ic_seat_combination),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceVariant
        )

        LazyRow(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .wrapContentWidth()
                .onGloballyPositioned {
                    listSize = it.size.toSize()
                },
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(
                items = item
            ) { item ->
                CinemaTicketSeatListItem(
                    item = item,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun CinemaTicketSeatListItem(
    item: CinemaTicketSeatModel,
    onClick: (CinemaTicketSeatModel) -> Unit
) {
    val color = when {

        item.available && item.selected -> MaterialTheme.colorScheme.primary

        item.available -> MaterialTheme.colorScheme.surface

        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Icon(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .size(32.dp)
            .clickable(
                enabled = item.available,
                onClick = { onClick.invoke(item) },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        painter = painterResource(id = R.drawable.ic_cinema_seat),
        contentDescription = null,
        tint = color
    )
}
