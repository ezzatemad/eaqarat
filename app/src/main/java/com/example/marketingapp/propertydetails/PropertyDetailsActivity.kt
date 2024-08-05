package com.example.marketingapp.propertydetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.domain.datamodel.getallproperty.DataItem
import com.example.marketingapp.Dimension
import com.example.marketingapp.R
import com.example.marketingapp.search.formatDate

class PropertyDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val property: DataItem? = intent.getParcelableExtra("property")
            PropertyDetailsScreen(property)
        }
    }
}

@Composable
fun PropertyDetailsScreen(property: DataItem?) {
    var currentImageIndex by remember { mutableStateOf(0) }
    val hasImages = property?.imageUrl?.isNotEmpty() == true

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            ImageSection(property, currentImageIndex, { currentImageIndex = it })

            PropertyInfoSection(property)
        }
        ContactRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun ImageSection(property: DataItem?, currentImageIndex: Int, onImageChange: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimension.LargeImageHeight)
    ) {
        if (property?.imageUrl?.isNotEmpty() == true) {
            val painter = rememberAsyncImagePainter(
                model = property.imageUrl!![currentImageIndex],
                placeholder = painterResource(id = R.drawable.no_image_iv),
                error = painterResource(id = R.drawable.no_image_iv)
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
                    val newIndex =
                        (currentImageIndex - 1).takeIf { it >= 0 } ?: (property.imageUrl!!.size - 1)
                    onImageChange(newIndex)
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
                    val newIndex = (currentImageIndex + 1) % property.imageUrl!!.size
                    onImageChange(newIndex)
                }, modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Next Image",
                    tint = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                property.imageUrl!!.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .size(8.dp)
                            .background(
                                if (index == currentImageIndex) colorResource(id = R.color.green) else colorResource(
                                    id = R.color.gray
                                ), shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.no_image_iv),
                contentDescription = "No Image Available",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun PropertyInfoSection(property: DataItem?) {
    val formattedDate = formatDate(property?.listedAt ?: "")
    Column(modifier = Modifier.padding(Dimension.LargePadding)) {
        Text(
            text = "${property?.price.toString()} EGP",
            fontSize = Dimension.MediumFontSize,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.green)
        )

        Row(
            modifier = Modifier.padding(top = Dimension.SmallSmallPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_area),
                contentDescription = "property area",
                tint = colorResource(id = R.color.gray)
            )
            Spacer(modifier = Modifier.width(Dimension.SmallSmallPadding))
            Text(
                text = "${property?.area} sqm",
                fontWeight = FontWeight.ExtraLight,
                color = colorResource(id = R.color.gray)
            )
        }
        Spacer(modifier = Modifier.height(Dimension.SmallPadding))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formattedDate,
                color = colorResource(id = R.color.gray),
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier
                .background(color = colorResource(id = R.color.green2))
                .padding(Dimension.MediumPadding)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "property location",
                tint = colorResource(id = R.color.green),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(Dimension.SmallSmallPadding))
            Text(
                text = property?.location.toString(),
                color = colorResource(id = R.color.black),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = property?.title.toString(),
            modifier = Modifier.padding(top = Dimension.SmallPadding),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = Dimension.MediumFontSize)
        )
        Text(
            text = property?.description.toString(),
            modifier = Modifier.padding(top = Dimension.SmallPadding),
            color = colorResource(id = R.color.gray),
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = Dimension.SmallFontSize)
        )
        Column(modifier = Modifier.padding(top = Dimension.LargePadding)) {
            Text(
                text = "Property Information",
                fontWeight = FontWeight.Bold,
                fontSize = Dimension.MediumFontSize,
                color = Color.Black,
                modifier = Modifier.padding(bottom = Dimension.MediumPadding)
            )
            PropertyInfoRow(label = "Title", value = property?.title ?: "")
            PropertyInfoRow(label = "Price", value = property?.price.toString())
            PropertyInfoRow(label = "Location", value = property?.location ?: "")
            PropertyInfoRow(label = "Type", value = property?.propertyType ?: "")
            PropertyInfoRow(label = "Status", value = property?.status ?: "")
            PropertyInfoRow(label = "Area", value = "${property?.area} msq")
            PropertyInfoRow(label = "Created at", value = formattedDate)
        }
    }
}
@Composable
fun ContactRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactOption(
            iconRes = R.drawable.ic_email,
            text = "Mail",
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(Dimension.MediumPadding))
        ContactOption(
            iconRes = R.drawable.ic_email,
            text = "Call",
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(Dimension.MediumPadding))
        ContactOption(
            iconRes = R.drawable.ic_email,
            text = null,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ContactOption(iconRes: Int, text: String?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.green2), shape = RoundedCornerShape(Dimension.LargeCornerRadius)
            )
            .padding(Dimension.SmallPadding)
            .border(1.dp, Color.Transparent, shape = RoundedCornerShape(Dimension.LargeCornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = Dimension.MediumPadding, vertical = Dimension.SmallPadding)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                tint = colorResource(id = R.color.green)
            )
            if (text != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text, color = colorResource(id = R.color.green),
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun PropertyInfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(bottom = Dimension.MediumPadding)) {
        Text(
            text = "$label  ",
            color = colorResource(id = R.color.gray),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(Dimension.LargePadding))
        Text(
            text = value, color = Color.Black, fontWeight = FontWeight.Bold
        )
    }
}
