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
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import sample.cinema.ticket.core.model.NetworkViewState
import sample.cinema.ticket.core.theme.w300
import sample.cinema.ticket.core.theme.w400
import sample.cinema.ticket.core.theme.w500
import sample.cinema.ticket.core.theme.w600
import sample.cinema.ticket.core.theme.w700
import sample.cinema.ticket.core.theme.x1
import sample.cinema.ticket.core.theme.x2
import sample.cinema.ticket.core.theme.x3
import sample.cinema.ticket.core.theme.x6
import sample.cinema.ticket.core.util.TicketShape
import sample.cinema.ticket.core.util.extensions.parseServerErrorMessage
import sample.cinema.ticket.core.util.extensions.parseValidationErrorMessage
import sample.cinema.ticket.core.util.snackBar.SnackBarType
import sample.cinema.ticket.core.util.ui.AppButton
import sample.cinema.ticket.core.util.ui.AppCard
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketDayModel
import sample.cinema.ticket.features.ticket.data.model.CinemaTicketSeatModel
import sample.cinema.ticket.features.ticket.domain.validation.BuyCinemaTicketValidationError

@Composable
fun CinemaTicketScreen(
    viewModel: CinemaTicketViewModel = hiltViewModel(),
    showMainSnackBar: (message: String?, type: SnackBarType, duration: SnackbarDuration) -> Unit
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

    val networkViewState by viewModel.networkViewState.collectAsState()

    LaunchedEffect(networkViewState) {

        if (networkViewState.showValidationError) {

            (networkViewState.validationError as BuyCinemaTicketValidationError?)?.let {

                showMainSnackBar.invoke(
                    context.parseValidationErrorMessage(it.message),
                    SnackBarType.Warning,
                    SnackbarDuration.Short
                )
            }
        }

        if (networkViewState.showError) {

            showMainSnackBar.invoke(
                context.parseServerErrorMessage(networkViewState),
                SnackBarType.Error,
                SnackbarDuration.Short
            )
        }

        if (networkViewState.showSuccess) {

            showMainSnackBar.invoke(
                context.getString(R.string.msg_done_successfully),
                SnackBarType.Success,
                SnackbarDuration.Short
            )
        }
    }

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
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .height(100.dp)
                .clip(TicketShape()),
            contentScale = ContentScale.Crop
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
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

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            statusTypeList.forEach { item ->
                CinemaTicketStatusTypeListItem(
                    item = item
                )
            }
        }

        CinemaTicketCardItem(
            networkViewState = networkViewState,
            selectedSeatList = selectedSeatList,
            dayList = dayList,
            selectedDay = selectedDay,
            hourList = hourList,
            selectedHour = selectedHour,
            onDayClick = viewModel::setSelectedDay,
            onHourClick = viewModel::setSelectedHour,
            onBuyClick = viewModel::buyCinemaTicket
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
            .padding(vertical = 8.dp)
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
fun RowScope.CinemaTicketStatusTypeListItem(
    item: CinemaTicketStatusType
) {
    val color = when (item) {
        CinemaTicketStatusType.Available -> MaterialTheme.colorScheme.surface
        CinemaTicketStatusType.Taken -> MaterialTheme.colorScheme.surfaceVariant
        CinemaTicketStatusType.Selected -> MaterialTheme.colorScheme.primary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
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
    networkViewState: NetworkViewState,
    selectedSeatList: List<CinemaTicketSeatModel>?,
    dayList: List<CinemaTicketDayModel>,
    selectedDay: CinemaTicketDayModel,
    hourList: List<String>,
    selectedHour: String,
    onDayClick: (CinemaTicketDayModel) -> Unit,
    onHourClick: (String) -> Unit,
    onBuyClick: () -> Unit
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
                        onClick = onDayClick
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
                        onClick = onHourClick
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
                    wrapPadding = 16.dp,
                    onClick = onBuyClick,
                    loading = networkViewState.showProgress
                )
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
