package com.example.applicationquizforstudents.presentation.ui.screens.quiz.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.domain.models.Specialty
import com.example.applicationquizforstudents.domain.models.User
import com.example.applicationquizforstudents.presentation.ui.Utils.listResultAnswer

@Composable
fun ResultScreen(
    viewModel: ResultViewModel,
    onMenuClick:()->Unit
){
    Column {
        Row(
            modifier=  Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale= ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.feu_logo1),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text="Результат")

            viewModel.findResult().forEach {list->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(5.dp).border(width = 2.dp, Color.Black, shape = RectangleShape).padding(5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = list.key,style =MaterialTheme.typography.titleMedium  ,modifier = Modifier.fillMaxWidth(0.65f))
                    Text(text = "${list.value}%",style =MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth(0.35f))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(onClick = {
                viewModel.updateUser(
                    User.Base(
                    id = viewModel.getUserId(),
                    email = "",
                    surname = "",
                    name = "",
                    password = "",
                    economy = viewModel.findResult().getValue(Specialty.ME.description).toString(),
                    electronic_economy = viewModel.findResult().getValue(Specialty.EM.description).toString(),
                    isit = viewModel.findResult().getValue(Specialty.ISIT.description).toString(),
                    accounting = viewModel.findResult().getValue(Specialty.EUP.description).toString()
                ))
                onMenuClick()
            }) {
                Text("Меню")
            }
        }
    }

}