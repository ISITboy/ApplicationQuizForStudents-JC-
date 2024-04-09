package com.example.applicationquizforstudents.presentation.ui.screens.quiz.editimage

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.applicationquizforstudents.domain.models.Image
import com.example.applicationquizforstudents.domain.state.RealtimeCRUDState
import com.example.applicationquizforstudents.domain.state.UploadState
import com.example.applicationquizforstudents.domain.state.UploadState.Loading
import com.example.applicationquizforstudents.presentation.ui.screens.authorization.components.EmailInputLayout
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun EditImageScreen(
    modifier: Modifier = Modifier,
    editType: Int? = null,
    viewModel: EditImageViewModel = hiltViewModel(),
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onCreateImageClick:(String,String)->Unit,
    onUpdateImageClick:(Image,Int)->Unit


) {
    viewModel.getImageById(editType)


    var selectedImageUrl by remember {
        mutableStateOf<Uri?>(null)
    }
    val uploadTask by viewModel.state.collectAsState()
    val description = viewModel.description.collectAsState()
    val photoUrl by viewModel.photoUrl.collectAsState()
    val imageState by viewModel.imageState.collectAsState()
    var count by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = selectedImageUrl, key2 = imageState) {

        when (imageState) {
            is RealtimeCRUDState.SuccessForImages -> {
                val data = (imageState as RealtimeCRUDState.SuccessForImages).images.first()
                viewModel.updateDescription(data.description)
                viewModel.updatePhotoUrl(data.url)
            }

            is RealtimeCRUDState.Loading -> {

            }

            is RealtimeCRUDState.Delete -> {
                onDeleteClick()
            }

            is RealtimeCRUDState.CreateImage -> Toast.makeText(
                viewModel.appContext,
                "Done",
                Toast.LENGTH_SHORT
            ).show()

            else -> {

            }
        }
        selectedImageUrl?.let { uri ->
            Log.d("TTQQ","selectedImageUrl")
            viewModel.updatePhotoUrl(photoUrl = uri.toString())
        }

    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUrl = uri }
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(10.dp))

        AsyncImage(
            modifier = Modifier.size(350.dp, 200.dp),
            model = photoUrl,
            contentDescription = null
        )
        Spacer(Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Text(text = "Изменить")
            }
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        }

        Spacer(Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                editType?.let {
                    viewModel.removeImage(it)
                }
            }) { Text("УДАЛИТЬ") }
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(10.dp))
        EmailInputLayout(email = description, invalid = mutableStateOf(false), onChangeEmail = {
            viewModel.updateDescription(it)
        })
        Log.d("TTQQ","4")
        when (uploadTask) {
            is Loading -> {
                val progress = (uploadTask as Loading).progress
                LinearProgressIndicator(
                    progress = progress.second.toFloat() / progress.first
                )
            }

            is UploadState.Error -> {
                val errorMessage = (uploadTask as UploadState.Error).e.message.toString()
                Text(text = errorMessage)
            }

            is UploadState.Success -> {
                val data = (uploadTask as UploadState.Success).url
                editType?.let { id ->
                    if (id == -1 && count ==0) {
                        count++
//                        viewModel.createImage(viewModel.photoUrl.value, description.value)
                        onCreateImageClick(data,description.value)
                    }
                    if(id>-1) {
                        onUpdateImageClick(Image(
                            id = editType,
                            url = data,
                            description = description.value
                        ),editType)
                    }
                }
            }

            else -> {

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onBackClick() }) {
                Text(text = "Назад")
            }
            Button(onClick = {
                if (selectedImageUrl == null) {
                    editType?.let {
                        viewModel.updateImage(
                            image = Image(
                                id = editType,
                                url = photoUrl,
                                description = description.value
                            ), id = editType
                        )
                    }
                } else {
                    selectedImageUrl?.let {
                        viewModel.uploadFile(it)
                    }
                }
            }) {
                Text(text = "Принять")
            }
        }
    }

}




