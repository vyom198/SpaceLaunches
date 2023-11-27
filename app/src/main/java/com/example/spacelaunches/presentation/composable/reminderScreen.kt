package com.example.spacelaunches.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spacelaunches.R
import com.example.spacelaunches.data.local.entity.Reminder
import com.example.spacelaunches.presentation.states.viewmodel.ReminderViewmodel
import com.example.spacelaunches.presentation.states.viewmodel.RemindersScreenEvent
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun reminderScreen(
    viewModel : ReminderViewmodel,
    onBackPressed : () -> Unit ,
    reminderCancelled: () -> Unit,
    reminderNotCancelled: (String?) -> Unit

) {
    val state by viewModel.reminderState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                RemindersScreenEvent.ReminderCancelled -> {
                    reminderCancelled()
                }

                is RemindersScreenEvent.ReminderNotCancelled -> {
                    reminderNotCancelled(event.infoMessage)
                }
            }
        }
    }

   Scaffold(modifier = Modifier
        .fillMaxSize(),
       topBar = {
        TopAppBar(
            title = {
                Text(text = "Reminders", fontWeight = FontWeight.Medium)
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed ) {
                    Icon(imageVector = Icons.Default.ArrowBack , contentDescription =null )
                }

            }

        )
    }
    ){

     Column(modifier = Modifier.fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,


           ){
                if (state.data.isNotEmpty()){
                    state.data.let {
                        LazyColumn(modifier = Modifier.padding(top = 70.dp)){
                            items(it){
                                ReminderItem(it, cancelReminderClicked = { reminderId->
                                        viewModel.cancelReminders(reminderId)

                                    })
                            }
                        }
                    }

                }else{
               Box(modifier = Modifier.fillMaxSize(),
                   contentAlignment = Alignment.Center){
                   Text(text = "No schedulers for upcoming Launch")
                }


                }
               if(state.error!= null){
                  Box(modifier = Modifier.fillMaxSize(),
                      contentAlignment = Alignment.Center){
                      Text(text = "error Occoured")
                  }

              }

            }

       }

   }




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReminderItem(reminder: Reminder, modifier : Modifier = Modifier,
                 cancelReminderClicked: (String) -> Unit,) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .height(400.dp)
            .fillMaxWidth(),


        elevation= CardDefaults.cardElevation()
    ) {

        val isoDateString = reminder.net
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
                model = reminder.image,
                contentDescription = reminder.name,
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
                reminder.status?.let { Text(text = it.abbrev ,fontWeight = FontWeight.ExtraBold) }

            }


        }

        Spacer(modifier = Modifier.height(4.dp))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            modifier = Modifier.fillMaxWidth()
        ){

            Text(
                text = reminder.name.toString(),
                maxLines = 1,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold, fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = reminder.launch_service_provider?.name.toString(),
                maxLines = 1,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold, fontSize = 17.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = reminder.pad?.location?.name.toString(),
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
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                             cancelReminderClicked(reminder.id)
            },modifier= Modifier
                .align(Alignment.End)
                .padding(end = 5.dp), border = BorderStroke(color = Color.Transparent, width = 2.dp)
            ) {
                Text(text = "Cancel")
            }

        }



    }

}
