package com.example.spacelaunches.presentation.composable

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spacelaunches.R
import com.example.spacelaunches.domain.model.UpcomingLaunches
import com.example.spacelaunches.presentation.states.viewmodel.Upcomingviewmodel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaunchItem(
    launches : UpcomingLaunches,
    modifier :Modifier = Modifier,
    addReminderClicked: (UpcomingLaunches) -> Unit,
) {
Card(
 modifier = modifier
     .padding(8.dp)
     .height(400.dp)
     .fillMaxWidth(),


 elevation=CardDefaults.cardElevation()
) {

    val isoDateString = launches.net
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val date = isoFormat.parse(isoDateString)
    val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
    val formattedDate = outputFormat.format(date)

    Row(
        modifier = Modifier.fillMaxWidth()
        , horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = launches.image,
            contentDescription = launches.name,
            modifier = Modifier
                .weight(1f)
                .height(200.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(1.dp)),
            contentScale = ContentScale.Crop
           , placeholder = painterResource(id = R.drawable.img)
        )
        Spacer(modifier = Modifier.width(24.dp))
                Chip(onClick = {

                },modifier = Modifier
                    .align(Alignment.Top)
                    .padding(end = 10.dp),
            colors = ChipDefaults.chipColors(backgroundColor =
            MaterialTheme.colorScheme.onBackground)) {
            launches.status?.let { Text(text = it.abbrev ,fontWeight = FontWeight.ExtraBold) }

        }


    }

      Spacer(modifier = Modifier.height(4.dp))
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center,

        modifier = Modifier.fillMaxWidth()
            ){

        Text(
            text = launches.name.toString(),
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold, fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = launches.launch_service_provider?.name.toString(),
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold, fontSize = 17.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = launches.pad?.location?.name.toString(),
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium, fontSize = 17.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Launche date : ${formattedDate}",
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium, fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Button(onClick = {addReminderClicked(launches) }) {
            Text(text = "Set To Remainder")
        }
        
    }

    

}


}