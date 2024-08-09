package com.example.marketingapp.favorite

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.domain.datamodel.getallproperty.DataItem
import com.example.marketingapp.Dimension
import com.example.marketingapp.R
import com.example.marketingapp.propertydetails.PropertyDetailsActivity
import com.example.marketingapp.search.formatDate
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(viewModel: FavouritePropertyViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var refreshTrigger by remember { mutableStateOf(Unit) }

    LaunchedEffect(refreshTrigger) {
        viewModel.getAllProperties()
    }

    val propertyList by viewModel.propertyList.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(snackbarData = data)
            }
        }
    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding
        ) {
            items(propertyList) { property ->
                FavoritePropertyCard(
                    imageUrls = property.imageUrl?.filterNotNull() ?: emptyList(),
                    price = property.price.toString(),
                    title = property.title ?: "Unknown",
                    description = property.description ?: "Unknown",
                    area = property.area ?: 0.0,
                    location = property.location ?: "Unknown",
                    date = property.listedAt ?: "Unknown",
                    property = property,
                    viewModel = viewModel,
                    onClick = {
                        val intent = Intent(context, PropertyDetailsActivity::class.java).apply {
                            putExtra("property", property)
                            putExtra("source", "favorite")
                        }
                        context.startActivity(intent)
                    },
                    onRemoveFavorite = {
                        viewModel.removeFavorite(property)
                        refreshTrigger = Unit
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.property_removed_from_favorites))
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun FavoritePropertyCard(
    imageUrls: List<String?>,
    price: String,
    title: String,
    description: String,
    area: Double,
    location: String,
    date: String,
    property: DataItem?,
    viewModel: FavouritePropertyViewModel,
    onClick: () -> Unit,
    onRemoveFavorite: () -> Unit
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
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimension.MediumImageHeight)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
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
                            contentDescription = "Property Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = {
                                currentImageIndex =
                                    (currentImageIndex - 1).takeIf { it >= 0 }
                                        ?: (imageUrls.size - 1)
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

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        FavoriteTopIconsRow(
                            viewModel = viewModel,
                            property = property,
                            onRemoveFavorite = onRemoveFavorite
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
                        contentDescription = "Area Icon",
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


@Composable
fun FavoriteTopIconsRow(
    viewModel: FavouritePropertyViewModel,
    property: DataItem?,
    onRemoveFavorite: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Handle action 1 */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_email),
                contentDescription = "Action 1",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = {
            property?.let {
                viewModel.removeFavorite(it)
                onRemoveFavorite()
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite_red),
                contentDescription = "Favorite",
                tint = Color.Red,
            )
        }
    }
}


