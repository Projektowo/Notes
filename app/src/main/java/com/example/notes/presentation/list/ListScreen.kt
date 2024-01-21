package com.example.notes.presentation.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notes.R
import com.example.notes.domain.model.DomainNotePriorityType
import com.example.notes.presentation.model.NoteOnView
import com.example.notes.presentation.navigation.Screens

@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    navController: NavController
) {

    val viewState = viewModel.viewState

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.DetailsScreen.route) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_button)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = {
            if (viewState.notes.isEmpty()) {
                EmptyNotes()
            } else {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    columns = StaggeredGridCells.Fixed(2)
                ) {
                    items(viewState.notes) { item ->
                        NoteItem(
                            note = item,
                            onItemClick = {
                                navController.navigate(Screens.DetailsScreen.routeWithId(it))
                            },
                            onDeleteNote = viewModel::deleteNote
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun EmptyNotes() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            painter = painterResource(id = R.drawable.ic_hourglass_empty),
            contentDescription = stringResource(R.string.empty_list),
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.create_a_note_and_it_will_show_up_here),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: NoteOnView,
    onItemClick: (id: Int) -> Unit,
    onDeleteNote: (id: Int) -> Unit
) {
    var isOptionsShown by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .combinedClickable(
                onClick = { onItemClick(note.id) },
                onLongClick = { isOptionsShown = true },
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        DropdownMenu(
            expanded = isOptionsShown,
            onDismissRequest = { isOptionsShown = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.delete)) },
                onClick = {
                    onDeleteNote(note.id)
                    isOptionsShown = false
                }
            )
        }

        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (note.priority.domainNotePriorityType != DomainNotePriorityType.UNKNOWN &&
                    note.priority.domainNotePriorityType != DomainNotePriorityType.LOW
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(note.priority.icon),
                        contentDescription = stringResource(R.string.priority_icon)
                    )
                }

                Canvas(modifier = Modifier.size(16.dp)) {
                    drawCircle(color = note.categoryColor)
                }
            }


            Text(
                text = note.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
