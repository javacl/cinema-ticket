package sample.cinema.ticket.features.ticket.ui.cinema

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
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
import sample.cinema.ticket.core.theme.x6
import sample.cinema.ticket.core.util.ui.AppButton
import sample.cinema.ticket.core.util.ui.AppCard
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketDayModel
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel

@Composable
fun CinemaTicketScreen(
    viewModel: CinemaTicketViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val onBackPressDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val selectedSeatList by viewModel.selectedSeatList.collectAsState(initial = null)

    val packedSeatList by viewModel.packedSeatList.collectAsState(initial = null)

    val statusTypeList by viewModel.statusTypeList.collectAsState()

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
                .clickable { onBackPressDispatcher?.onBackPressed() }
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
                .graphicsLayer {
                    rotationX = -45f
                },
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

            items(
                items = statusTypeList
            ) { item ->
                CinemaTicketStatusTypeListItem(
                    item = item
                )
            }
        }

        CinemaTicketCardItem(
            selectedSeatList = selectedSeatList,
            dayList = dayList,
            selectedDay = selectedDay,
            hourList = hourList,
            selectedHour = selectedHour,
            onDayClicked = viewModel::setSelectedDay,
            onHourClicked = viewModel::setSelectedHour
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
fun CinemaTicketStatusTypeListItem(
    item: CinemaTicketStatusType
) {
    val color = when (item) {
        CinemaTicketStatusType.Available -> MaterialTheme.colorScheme.surface
        CinemaTicketStatusType.Taken -> MaterialTheme.colorScheme.surfaceVariant
        CinemaTicketStatusType.Selected -> MaterialTheme.colorScheme.primary
    }

    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = item.name,
            style = MaterialTheme.typography.w400.x2,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun CinemaTicketCardItem(
    selectedSeatList: List<CinemaTicketSeatModel>?,
    dayList: List<CinemaTicketDayModel>,
    selectedDay: CinemaTicketDayModel?,
    hourList: List<String>,
    selectedHour: String?,
    onDayClicked: (CinemaTicketDayModel) -> Unit,
    onHourClicked: (String) -> Unit
) {
    AppCard(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
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
                        selected = item == selectedDay,
                        onClick = onDayClicked
                    )
                }
            }

            LazyRow(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                items(
                    items = hourList
                ) { item ->
                    CinemaTicketHourListItem(
                        item = item,
                        selected = item == selectedHour,
                        onClick = onHourClicked
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp, top = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = "$100.00",
                        style = MaterialTheme.typography.w700.x6,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = stringResource(
                            R.string.param_tickets,
                            selectedSeatList?.size ?: 0
                        ),
                        style = MaterialTheme.typography.w400.x1,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                AppButton(
                    text = stringResource(id = R.string.label_buy_tickets),
                    iconResource = R.drawable.ic_card,
                    height = 40.dp,
                    isWrap = true,
                    textStyle = MaterialTheme.typography.w300.x3,
                    iconSize = 20.dp,
                    wrapPadding = 16.dp
                ) {

                }
            }
        }
    }
}

@Composable
fun CinemaTicketDayListItem(
    item: CinemaTicketDayModel,
    selected: Boolean,
    onClick: (CinemaTicketDayModel) -> Unit
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
            .clickable { onClick.invoke(item) }
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
    selected: Boolean,
    onClick: (String) -> Unit
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
            .clickable { onClick.invoke(item) }
            .padding(8.dp),
        text = item,
        style = MaterialTheme.typography.w500.x3,
        color = contentColor
    )
}
