@file:OptIn(ExperimentalMaterialApi::class)

package com.example.marketingapp.search

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.marketingapp.Dimension
import com.example.marketingapp.R
import com.example.marketingapp.TokenManager
import com.example.marketingapp.propertydetails.PropertyDetailsActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.marketingapp.search.HomeViewModel.SortOption

@Composable
fun SearchScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val filteredProperties by viewModel.filteredProperties.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val selectedSortOption by viewModel.selectedSortOption.collectAsState()

    val tokenManager = TokenManager(context)
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val token = tokenManager.getToken()
        if (token != null) {
            // Set the default sort option (newest) when the screen is opened
            viewModel.setSelectedSortOption(SortOption("listedAt", false))
            viewModel.getAllProperty(token)
        }
    }

    LaunchedEffect(viewModel.selectedStatus.collectAsState().value) {
        listState.scrollToItem(0)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                modifier = Modifier.padding(
                    top = Dimension.MediumPadding,
                    start = Dimension.LargePadding,
                    end = Dimension.LargePadding,
                    bottom = Dimension.SmallPadding
                ),
                text = searchText,
                onSearch = { query ->
                    val token = tokenManager.getToken()
                    if (query.isEmpty()) {
                        // Fetch all properties if search query is cleared
                        viewModel.getAllProperty(token ?: "")
                    } else {
                        viewModel.searchProperties(token ?: "", query)
                    }
                }
            )
            FilterByStatus { status ->
                viewModel.setSelectedStatus(status)
                viewModel.filterPropertiesByStatus(status)
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.green)
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(top = Dimension.MediumPadding)
                ) {
                    items(filteredProperties) { property ->
                        PropertyCard(
                            imageUrls = property?.imageUrl ?: emptyList(),
                            price = property?.price.toString(),
                            title = property?.title ?: "",
                            description = property?.description ?: "",
                            area = property?.area ?: 0.0,
                            location = property?.location ?: "",
                            date = property?.listedAt ?: "",
                            onClick = {
                                val intent =
                                    Intent(context, PropertyDetailsActivity::class.java).apply {
                                        putExtra("property", property)
                                        putExtra("source", "search")

                                    }
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }

        SortButton(
            onClick = { coroutineScope.launch { sheetState.show() } },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    top = Dimension.MediumPadding,
                    bottom = Dimension.biggestLargePadding,
                    start = Dimension.MediumPadding
                ),
            backgroundColor = colorResource(id = R.color.green2),
            contentColor = colorResource(id = R.color.green),
            iconRes = R.drawable.ic_sort,
            text = "Sort"
        )

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                SortOptions(
                    selectedOption = selectedSortOption,
                    onOptionSelected = { option ->
                        coroutineScope.launch { sheetState.hide() }
                        val token = tokenManager.getToken()
                        viewModel.setSelectedSortOption(option)
                    }
                )
            }
        ) {}
    }
}


@Composable
fun SortOptions(
    selectedOption: HomeViewModel.SortOption,
    onOptionSelected: (HomeViewModel.SortOption) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimension.LargePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sort_left),
                contentDescription = "icon sort left",
                tint = colorResource(id = R.color.green)
            )
            Text(
                text = "Sort",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(Dimension.MediumPadding),
                color = colorResource(id = R.color.green)
            )
        }

        val sortOptions = listOf(
            SortOption("listedAt", false) to "Newest",
            SortOption("price", true) to "Price (low to high)",
            SortOption("price", false) to "Price (high to low)"
        )

        LazyColumn(
            modifier = Modifier.padding(
                start = Dimension.LargePadding,
                end = Dimension.LargePadding
            )
        ) {
            itemsIndexed(sortOptions) { index, (option, description) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOptionSelected(option) }
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.green),
                            unselectedColor = colorResource(id = R.color.green)
                        ),
                        selected = option == selectedOption,
                        onClick = { onOptionSelected(option) }
                    )
                    Text(
                        text = "${description}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = Dimension.SmallPadding),
                        color = colorResource(id = R.color.green)
                    )
                }
                // Add a divider after each item except the last one
                if (index < sortOptions.size - 1) {
                    Divider(
                        modifier = Modifier.padding(horizontal = Dimension.LargePadding),
                        color = colorResource(id = R.color.gray),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    placeholderText: String = "Search",
    text: String,
    onSearch: (String) -> Unit
) {
    var textValue by remember { mutableStateOf(TextFieldValue(text)) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(colorResource(id = R.color.gray2), RoundedCornerShape(50))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimension.SmallSmallPadding, end = Dimension.SmallSmallPadding)
        ) {
            TextField(
                value = textValue,
                onValueChange = { newTextValue ->
                    textValue = newTextValue
                    // Do not trigger search here
                },
                placeholder = { Text(text = placeholderText, color = Color.Gray) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = Dimension.SmallPadding),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                )
            )
            IconButton(onClick = { onSearch(textValue.text) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            }
        }
    }
}


@Composable
fun FilterByStatus(onStatusSelected: (String) -> Unit) {
    var selectedStatus by remember { mutableStateOf("All") }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimension.LargePadding,
                end = Dimension.LargePadding,
                bottom = Dimension.MediumPadding
            ),
//        horizontalArrangement = Arrangement.spacedBy(Dimension.SmallPadding),
        contentPadding = PaddingValues(horizontal = Dimension.SmallPadding)
    ) {
        // Define status items
        val statusItems = listOf(
            StatusItem(displayText = "All", statusValue = "All"),
            StatusItem(displayText = "Furnetured", statusValue = "Furnetured"),
            StatusItem(displayText = "UnFernetired", statusValue = "UnFernetired")
        )

        items(statusItems) { item ->
            StatusButton(
                text = item.displayText,
                isSelected = selectedStatus == item.statusValue,
                onClick = {
                    selectedStatus = item.statusValue
                    onStatusSelected(item.statusValue)
                }
            )
        }
    }
}

@Composable
fun StatusButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(Dimension.MediumPadding),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(id = R.color.green) else colorResource(id = R.color.unSelected_button),
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        modifier = Modifier
            .wrapContentWidth() // Allows the button to size itself based on its content
            .padding(horizontal = Dimension.SmallPadding / 2) // Adjust padding as needed
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = Dimension.SmallFontSize,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color.Black
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


data class StatusItem(val displayText: String, val statusValue: String)


@Composable
fun PropertyCard(
    imageUrls: List<String?>,
    price: String,
    title: String,
    description: String,
    area: Double,
    location: String,
    date: String,
    onClick: () -> Unit,
//    onLongClick: () -> Unit
) {
    var currentImageIndex by remember { mutableStateOf(0) }
    val hasImages = imageUrls.isNotEmpty()

    val formattedDate = formatDate(date)
    Card(
        modifier = Modifier
            .padding(
                start = Dimension.LargePadding,
                end = Dimension.LargePadding,
                top = Dimension.SmallPadding,
                bottom = Dimension.SmallPadding
            )
            .fillMaxWidth()
            .clickable(onClick = onClick)
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onLongPress = {
//                        onLongClick() // Call the long click callback
//                    }
//                )
//            }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimension.MediumImageHeight)
            ) {
                if (hasImages) {
                    val painter = rememberAsyncImagePainter(
                        model = imageUrls[currentImageIndex],
                        placeholder = painterResource(id = R.drawable.no_image),
                        error = painterResource(id = R.drawable.no_image)
                    )
                    val painterState = painter.state

                    if (painterState is AsyncImagePainter.State.Loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }

                    Image(
                        painter = painter,
                        contentDescription = "Property.sq Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    IconButton(
                        onClick = {
                            currentImageIndex =
                                (currentImageIndex - 1).takeIf { it >= 0 } ?: (imageUrls.size - 1)
                        },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Previous Image",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = {
                            currentImageIndex = (currentImageIndex + 1) % imageUrls.size
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = "Next Image",
                            tint = Color.White
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.no_image),
                        contentDescription = "No Image Available",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    imageUrls.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .size(8.dp)
                                .background(
                                    if (index == currentImageIndex) colorResource(id = R.color.green2) else colorResource(
                                        id = R.color.gray
                                    ), shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(Dimension.MediumPadding)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title, style = MaterialTheme.typography.body2, color = Color.Black
                    )
                    Text(
                        text = "$price EGP",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.green)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = Dimension.SmallPadding)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_area),
                        contentDescription = "Location Icon",
                        tint = Color.Gray,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$area sqm",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(Dimension.SmallSmallPadding))
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Dimension.SmallSmallPadding))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = Dimension.SmallPadding)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location Icon",
                        tint = colorResource(id = R.color.green),
                    )
                    Spacer(modifier = Modifier.width(Dimension.SmallPadding))
                    Text(
                        text = location,
                        style = MaterialTheme.typography.body2,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(Dimension.SmallPadding))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.body2,
                        color = colorResource(id = R.color.gray),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}


fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()
        )
        val outputFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: "Unknown Date"
    } catch (e: Exception) {
        "Unknown Date"
    }
}

@Composable
fun SortButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorResource(id = R.color.green2),
    contentColor: Color = colorResource(id = R.color.green),
    iconRes: Int,
    text: String
) {
    Box(
        modifier = modifier
            .background(
                backgroundColor,
                RoundedCornerShape(
                    bottomStart = Dimension.LargeCornerRadius,
                    topStart = Dimension.LargeCornerRadius
                )
            )
            .clickable(onClick = onClick)
            .padding(horizontal = Dimension.LargePadding, vertical = Dimension.MediumPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = text,
                color = contentColor
            )
            Spacer(modifier = Modifier.width(Dimension.SmallSpace))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = contentColor
            )
        }
    }
}


