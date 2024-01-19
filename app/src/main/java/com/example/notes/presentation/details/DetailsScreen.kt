package com.example.notes.presentation.details

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notes.R
import com.example.notes.presentation.model.PriorityOnView
import com.example.notes.presentation.navigation.Screens
import com.example.notes.presentation.util.clickableNoRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(), navController: NavController,
) {
    val viewState = viewModel.viewState
    var showSheet by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    BackHandler(enabled = true) {
        if (viewState.hasChanges) {
            openDialog = true
        } else {
            navController.navigateUp()
        }
    }

    if (openDialog) {
        ExitAlertDialog(
            onSave = {
                viewModel.updateNote()
                openDialog = false
            },
            onCancel = {
                openDialog = false
                navController.navigateUp()
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DetailsTopBar(
                onBackClick = { onBackPressedDispatcher?.onBackPressed() },
                onOptionsClick = { showSheet = true }
            )
        },
        floatingActionButton = {
            if (viewState.hasChanges) {
                FloatingActionButton(
                    onClick = { viewModel.updateNote() }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.add_button)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->

            if (showSheet) {
                OptionsSheetContent(
                    viewState = viewState,
                    categories = viewModel.getCategories(),
                    priorities = viewModel.getPriorities(),
                    onCategoryClick = viewModel::updateCategory,
                    onPriorityClick = viewModel::updatePriority,
                    onDismiss = { showSheet = false },
                    onPriorityInfoClick = {
                        navController.navigate(Screens.PrioritySettingsScreen.route)
                    }
                )
            }
            Column(modifier = Modifier.padding(paddingValues)) {

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewState.noteOnView.title,
                    onValueChange = viewModel::updateTitle,
                    maxLines = 1,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    placeholder = { Text(text = stringResource(R.string.title)) },
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )

                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = viewState.noteOnView.content,
                    onValueChange = viewModel::updateContent,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    placeholder = { Text(text = stringResource(R.string.content)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    )
                )
            }
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBackClick: () -> Unit,
    onOptionsClick: () -> Unit
) {
    TopAppBar(title = {}, navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.go_back)
            )
        }
    }, actions = {
        IconButton(onClick = onOptionsClick) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.options)
            )
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsSheetContent(
    viewState: DetailsViewState,
    categories: List<Color>,
    priorities: List<PriorityOnView>,
    onCategoryClick: (Color) -> Unit,
    onPriorityClick: (PriorityOnView) -> Unit,
    onPriorityInfoClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = stringResource(R.string.select_category_color),
                style = TextStyle(fontSize = 24.sp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(categories) {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .size(42.dp)
                            .border(
                                width = 3.dp,
                                color = if (viewState.noteOnView.categoryColor == it)
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    Color.Transparent,
                                shape = CircleShape
                            )
                            .background(
                                color = it,
                                shape = CircleShape
                            )
                            .clickableNoRipple { onCategoryClick(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.select_priority),
                    style = TextStyle(fontSize = 24.sp)
                )
                IconButton(onClick = onPriorityInfoClick) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(R.string.info)
                    )
                }
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(priorities) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(48.dp)
                                .clickableNoRipple { onPriorityClick(it) },
                            painter = painterResource(it.icon),
                            contentDescription = stringResource(R.string.priority_icon)
                        )
                        Text(
                            text = LocalContext.current.getString(it.title),
                            fontWeight = if (viewState.noteOnView.priority == it)
                                FontWeight.Bold
                            else
                                FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExitAlertDialog(onSave: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = stringResource(R.string.unsaved_changes),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(text = "Do you want to save changes?", fontSize = 16.sp)
        },
        confirmButton = {
            TextButton(
                onClick = onSave
            ) {
                Text(
                    stringResource(R.string.save),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text(
                    stringResource(R.string.cancel),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        },
    )
}


