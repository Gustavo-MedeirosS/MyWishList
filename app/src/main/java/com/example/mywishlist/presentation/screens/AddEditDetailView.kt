package com.example.mywishlist.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mywishlist.R
import com.example.mywishlist.data.Wish
import com.example.mywishlist.presentation.components.add_edit_view.WishTextField
import com.example.mywishlist.presentation.components.common.AppBarView
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if (id != 0L) {
        val wish = viewModel.getWishById(id).collectAsState(
            initial = Wish(
                id = 0L,
                title = "",
                description = ""
            )
        )

        viewModel.onWishTitleChange(newValue = wish.value.title)
        viewModel.onWishDescriptionChange(newValue = wish.value.description)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title = stringResource(
                    id = if (id != 0L) R.string.update_wish else R.string.add_wish
                ),
                onBackNavClicked = {
                    viewModel.onWishTitleChange("")
                    viewModel.onWishDescriptionChange("")
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(10.dp))
            WishTextField(
                label = stringResource(id = R.string.lbl_title),
                value = viewModel.wishTitleState
            ) { viewModel.onWishTitleChange(it) }
            Spacer(Modifier.height(10.dp))
            WishTextField(
                label = stringResource(id = R.string.lbl_description),
                value = viewModel.wishDescriptionState
            ) { viewModel.onWishDescriptionChange(it) }
            Spacer(Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black
                ),
                onClick = {
                    if (
                        viewModel.wishTitleState.isNotEmpty() &&
                        viewModel.wishDescriptionState.isNotEmpty()
                    ) {
                        if (id != 0L) {
                            viewModel.updateWish(
                                wish = Wish(
                                    id = id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish has been updated"
                        } else {
                            viewModel.addWish(
                                wish = Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish has been created"
                        }

                    } else {
                        snackMessage.value = "Enter fields to create a Wish"
                    }

                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = snackMessage.value)
                        viewModel.onWishTitleChange("")
                        viewModel.onWishDescriptionChange("")
                        navController.navigateUp()
                    }
                }) {
                Text(
                    text = stringResource(id = if (id != 0L) R.string.update_wish else R.string.add_wish),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }
        }
    }
}