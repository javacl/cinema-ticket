package sample.cinema.ticket.features.ticket.ui.cinema

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
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
import sample.cinema.ticket.core.theme.w300
import sample.cinema.ticket.core.theme.w400
import sample.cinema.ticket.core.theme.w500
import sample.cinema.ticket.core.theme.w600
import sample.cinema.ticket.core.theme.w700
import sample.cinema.ticket.core.theme.x1
import sample.cinema.ticket.core.theme.x2
import sample.cinema.ticket.core.theme.x3
import sample.cinema.ticket.core.theme.x4
import sample.cinema.ticket.core.theme.x6
import sample.cinema.ticket.core.util.ui.AppCard
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketDayModel
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel

@Composable
fun CinemaTicketScreen(
    navController: NavController,
    viewModel: CinemaTicketViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val selectedSeatList by viewModel.selectedSeatList.collectAsState(initial = null)

    val packedSeatList by viewModel.packedSeatList.collectAsState(initial = null)

    val dayList by viewModel.dayList.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()

    val hourList by viewModel.hourList.collectAsState()
    val selectedHour by viewModel.selectedHour.collectAsState()

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

        CinemaTicketCardItem(
            selectedSeatList = selectedSeatList,
            dayList = dayList,
            selectedDay = selectedDay,
            hourList = hourList,
            selectedHour = selectedHour
        )
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

@Composable
fun CinemaTicketCardItem(
    selectedSeatList: List<CinemaTicketSeatModel>?,
    dayList: List<CinemaTicketDayModel>,
    selectedDay: CinemaTicketDayModel?,
    hourList: List<String>,
    selectedHour: String?
) {
    AppCard(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                items(
                    items = dayList
                ) { item ->
                    CinemaTicketDayListItem(
                        item = item,
                        selected = item == selectedDay
                    )
                }
            }

            LazyRow(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                items(
                    items = hourList
                ) { item ->
                    CinemaTicketHourListItem(
                        item = item,
                        selected = item == selectedHour
                    )
                }
            }
        }
    }
}

@Composable
fun CinemaTicketDayListItem(
    item: CinemaTicketDayModel,
    selected: Boolean
) {
    val modifier: Modifier
    val contentColor: Color

    if (selected) {

        modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        contentColor = MaterialTheme.colorScheme.onSecondary

    } else {

        modifier = Modifier.border(
            width = 0.1.dp,
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.large
        )
        contentColor = MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(MaterialTheme.shapes.large)
            .then(modifier)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = item.day,
            style = MaterialTheme.typography.w600.x6,
            color = contentColor
        )

        Text(
            text = item.name,
            style = MaterialTheme.typography.w300.x1,
            color = contentColor
        )
    }
}

@Composable
fun CinemaTicketHourListItem(
    item: String,
    selected: Boolean
) {
    val modifier: Modifier
    val contentColor: Color

    if (selected) {

        modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        contentColor = MaterialTheme.colorScheme.onSecondary

    } else {

        modifier = Modifier.border(
            width = 0.1.dp,
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.large
        )
        contentColor = MaterialTheme.colorScheme.onSurface
    }

    Text(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(MaterialTheme.shapes.large)
            .then(modifier)
            .padding(8.dp),
        text = item,
        style = MaterialTheme.typography.w500.x3,
        color = contentColor
    )
}
