package com.example.marketingapp.favorite

import android.content.Intent
import android.util.Log
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
import androidx.compose.material.Text
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
import com.example.marketingapp.propertydetails.TopIconsRow
import com.example.marketingapp.search.PropertyCard
import com.example.marketingapp.search.formatDate

@Composable
fun FavoriteScreen(viewModel: FavouritePropertyViewModel = hiltViewModel()) {

    val context = LocalContext.current
    // Fetch all properties when the activity is created
    LaunchedEffect(Unit) {
        viewModel.getAllroperties() // Ensure the correct method name is used
    }

    // Collect the properties and log them
    val propertyList by viewModel.propertyList.collectAsState()
    LaunchedEffect(propertyList) {
        Log.d("PropertyDetailsActivity", "Properties in local database: $propertyList")
    }

    LazyColumn {
        items(propertyList) { property ->
            FavoritePropertyCard(
                imageUrls = property.imageUrl ?: emptyList(),
                price = property.price.toString(),
                title = property.title ?: "Unknown",
                description = property.description ?: "Unknown",
                area = property.area ?: 0.0,
                location = property.location ?: "Unknown",
                date = property.listedAt ?: "Unknown",
                property = property,
                viewModel = viewModel
            ) {
                val intent = Intent(context, PropertyDetailsActivity::class.java).apply {
                    putExtra("Favourite Property", property) // Ensure proper data passing
                }
                context.startActivity(intent)
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
    property: DataItem?, // Add this parameter
    viewModel: FavouritePropertyViewModel, // Add this parameter
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
            TopIconsRow(viewModel, property) // Add TopIconsRow here
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