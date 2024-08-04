package com.example.marketingapp.propertydetails

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.domain.datamodel.getallproperty.DataItem

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
    // Display property details
    if (property != null) {
        Log.d("TAG", "PropertyDetailsScreen: ${property.location}/${property.title}/${property.area}/")
    } else {
        Text(text = "No property data available")
    }
}