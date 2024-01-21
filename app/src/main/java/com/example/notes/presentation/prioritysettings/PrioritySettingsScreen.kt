package com.example.notes.presentation.prioritysettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notes.R

@Composable
fun PrioritySettingsScreen(
    viewModel: PrioritySettingsViewModel = hiltViewModel(),
    navController: NavController
) {

    val viewState = viewModel.viewState


    Scaffold(
        topBar = {
            PrioritySettingsTopBar(onNavigateUp = { navController.navigateUp() })
        },
        content = {
            ScaffoldContent(
                modifier = Modifier.padding(it)
                    .padding(horizontal = 16.dp),
                viewState = viewState,
                viewModel = viewModel
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritySettingsTopBar(onNavigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.set_interval_for_note_priority),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back)
                )
            }
        }
    )
}

@Composable
fun ScaffoldContent(
    modifier: Modifier = Modifier,
    viewState: PrioritySettingsViewState,
    viewModel: PrioritySettingsViewModel
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(28.dp))
        ModifyInterval(
            labelTitle = stringResource(R.string.high_priority_title),
            labelSubTitle = stringResource(R.string.days),
            currentInterval = viewState.priorityHighDaysInterval,
            updateInterval = viewModel::updateHighPriorityInterval
        )

        ModifyInterval(
            labelTitle = stringResource(R.string.medium_priority_title),
            labelSubTitle = stringResource(R.string.days),
            currentInterval = viewState.priorityMediumDaysInterval,
            updateInterval = viewModel::updateMediumPriorityInterval
        )

    }
}

@Composable
fun ModifyInterval(
    labelTitle: String,
    labelSubTitle: String,
    currentInterval: Int,
    updateInterval: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = labelTitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = labelSubTitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { updateInterval(currentInterval.minus(1)) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(
                        R.string.minus
                    )
                )
            }

            Text(
                text = "$currentInterval",
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(onClick = { updateInterval(currentInterval.plus(1)) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = stringResource(R.string.plus)
                )
            }
        }
    }

}
