package com.dirzaaulia.allaboutvalorant.ui.agent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.dirzaaulia.allaboutvalorant.data.model.Agent
import com.dirzaaulia.allaboutvalorant.utils.NetworkImage
import com.dirzaaulia.allaboutvalorant.utils.replaceIfNull

@Composable
fun AgentList(
  agents: List<Agent>?,
  navigateToAgent: (Int) -> Unit
) {

  if (agents?.isNotEmpty() == true) {
    LazyVerticalGrid(
      columns = Fixed(2),
    ) {
      itemsIndexed(agents) { index, agent ->
        Card(
          modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
              navigateToAgent(index)
            },
          shape = MaterialTheme.shapes.small
        ) {
          Column {
            NetworkImage(
              modifier = Modifier.size(256.dp),
              url = agent.displayIcon.replaceIfNull(),
              contentScale = ContentScale.FillHeight
            )
            Surface(
              modifier = Modifier.fillMaxWidth(),
            ) {
              Text(
                modifier = Modifier.padding(
                  horizontal = 16.dp,
                  vertical = 8.dp
                ) ,
                text = agent.displayName.replaceIfNull(),
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h4
              )
            }
          }
        }
      }
    }
  }
}