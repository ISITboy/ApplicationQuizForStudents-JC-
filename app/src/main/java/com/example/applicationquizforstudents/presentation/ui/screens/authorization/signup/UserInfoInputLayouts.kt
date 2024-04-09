@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.applicationquizforstudents.presentation.ui.screens.authorization.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.applicationquizforstudents.R

@Composable
fun NameInputLayout(name: State<String>, enabled: Boolean = true,onChangeSurname:(String)->Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.8f),
        value = name.value,
        onValueChange = { onChangeSurname(it) },
        label = { if(enabled)Text(text = stringResource(id = R.string.text_input_name)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        enabled = enabled
    )
}

@Composable
fun SurnameInputLayout(
    surname: State<String>,
    invalid: MutableState<Boolean>,
    enabled: Boolean = true,
    onChangeSurname:(String)->Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.8f),
        value = surname.value,
        onValueChange = { onChangeSurname(it); invalid.value = false },
        label = { if(enabled)Text(text = stringResource(id = R.string.text_input_surname)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        isError = invalid.value,
        enabled = enabled
    )
}

