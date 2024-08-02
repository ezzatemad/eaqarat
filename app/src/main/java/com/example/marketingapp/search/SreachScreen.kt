package com.example.marketingapp.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.marketingapp.Dimension
import com.example.marketingapp.R
import com.example.marketingapp.TokenManager
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun SearchScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val filteredProperties by viewModel.filteredProperties.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val tokenManager = TokenManager(context)
    val token = tokenManager.getToken()
    LaunchedEffect(Unit) {
        if (token != null) {
            viewModel.getAllProperty(token)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.green),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                FilterByStatus { status -> viewModel.filterPropertiesByStatus(status) }
                LazyColumn(
                    modifier = Modifier.padding(top = Dimension.LargePadding)
                ) {
                    items(filteredProperties) { property ->
                        PropertyCard(
                            imageUrls = property?.imageUrl ?: emptyList(),
                            price = property?.price.toString(),
                            title = property?.title ?: "",
                            description = property?.description ?: "",
                            area = property?.area ?: 0.0,
                            location = property?.location ?: "",
                            date = property?.listedAt ?: ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterByStatus(onStatusSelected: (String) -> Unit) {
    var selectedStatus by remember { mutableStateOf("All") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimension.LargePadding, end = Dimension.LargePadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatusButton(
            text = stringResource(id = R.string.all),
            isSelected = selectedStatus == stringResource(R.string.all),
            onClick = {
                selectedStatus = "All"
                onStatusSelected("All")
            }
        )
        StatusButton(
            text = stringResource(R.string.furnished),
            isSelected = selectedStatus == stringResource(R.string.furnished),
            onClick = {
                selectedStatus = "Furnetured"
                onStatusSelected("Furnetured")
            }
        )
        StatusButton(
            text = stringResource(R.string.unfernetired),
            isSelected = selectedStatus == stringResource(R.string.unfernetired),
            onClick = {
                selectedStatus = "UnFernetired"
                onStatusSelected("UnFernetired")
            }
        )
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
            .padding(Dimension.SmallPadding)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = Dimension.SmallFontSize,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color.Black
            )
        )
    }
}

@Composable
fun PropertyCard(
    imageUrls: List<String?>,
    price: String,
    title: String,
    description: String,
    area: Double,
    location: String,
    date: String
) {
    var currentImageIndex by remember { mutableStateOf(0) }
    val hasImages = imageUrls.isNotEmpty()

    val formattedDate = formatDate(date)
    Card(
        shape = RoundedCornerShape(Dimension.MediumPadding),
        elevation = Dimension.SmallPadding,
        modifier = Modifier
            .padding(
                start = Dimension.LargePadding,
                end = Dimension.LargePadding,
                top = Dimension.LargePadding
            )
            .fillMaxWidth()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimension.MediumImageHeight)
            ) {
                if (hasImages) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = imageUrls[currentImageIndex],
                            placeholder = painterResource(id = R.drawable.ic_launcher_background),
                            error = painterResource(id = R.drawable.ic_launcher_foreground)
                        ),
                        contentDescription = "Property Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = {
                            currentImageIndex =
                                (currentImageIndex - 1).takeIf { it >= 0 } ?: (imageUrls.size - 1)
                        }, modifier = Modifier.align(Alignment.CenterStart)
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
                        }, modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = "Next Image",
                            tint = Color.White
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "No Image Available",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
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

