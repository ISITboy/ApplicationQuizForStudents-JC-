package com.example.applicationquizforstudents.presentation.ui.screens.authorization.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.applicationquizforstudents.R
import com.example.applicationquizforstudents.presentation.theme.ApplicationQuizForStudentsTheme

@Composable
fun EmailInputLayout(
    modifier: Modifier = Modifier,
    email: State<String>,
    invalid:MutableState<Boolean>,
    enabled:Boolean = true,
    onChangeEmail:(String)->Unit
) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.8f),
        value = email.value,
        onValueChange = { onChangeEmail(it); invalid.value  = false },
        label = { if(enabled) Text(text = stringResource(id = R.string.text_input_email)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            if (email.value.isNotBlank() && enabled) {
                IconButton(onClick = { onChangeEmail("") }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        },
        singleLine = true,
        isError = invalid.value,
        supportingText = {
            if(invalid.value) Text("Проверьте email") else null
        },
        enabled = enabled
    )
}



@Preview(showBackground = true)
@Composable
fun EmailInputLayoutPreview() {
    ApplicationQuizForStudentsTheme {
        val email = remember { mutableStateOf("") }
        //EmailInputLayout(email,{email.value = ""})
    }
}
