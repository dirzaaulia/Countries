package com.dirzaaulia.allaboutvalorant.ui.agent

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue.Revealed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.dirzaaulia.allaboutvalorant.data.model.Ability
import com.dirzaaulia.allaboutvalorant.data.model.Agent
import com.dirzaaulia.allaboutvalorant.data.model.Role
import com.dirzaaulia.allaboutvalorant.utils.NetworkImage
import com.dirzaaulia.allaboutvalorant.utils.replaceIfNull
import java.io.IOException

@Composable
fun AgentDetail(agent: Agent) {

  val backdropScaffoldState = rememberBackdropScaffoldState(initialValue = Revealed)

  BackdropScaffold(
    appBar = { },
    scaffoldState = backdropScaffoldState,
    headerHeight = 85.dp,
    stickyFrontLayer = true,
    backLayerBackgroundColor = MaterialTheme.colors.background,
    frontLayerBackgroundColor = Color("#424242".toColorInt()).copy(alpha = 0.6f),
    backLayerContent = {
      NetworkImage(
        modifier = Modifier.fillMaxSize(),
        url = agent.fullPortraitV2.replaceIfNull(),
        contentScale = ContentScale.FillHeight
      )
    },
    frontLayerContent = {
      val agentDisplayIcon = agent.displayIconSmall
      val agentDisplayName = agent.displayName
      val role = agent.role
      val abilities = agent.abilities
      val audioUrl = agent.voiceLine?.mediaList?.get(0)?.wave
      val agentDescription = agent.description

      AgentDetailFrontLayer(
        agentDisplayIcon = agentDisplayIcon ,
        agentDisplayName = agentDisplayName,
        role = role,
        abilities = abilities,
        audioUrl = audioUrl,
        agentDescription = agentDescription
      )
    }
  )
}

@Composable
fun AgentDetailFrontLayer(
  agentDisplayIcon: String?,
  agentDisplayName: String?,
  role: Role?,
  abilities: List<Ability>?,
  audioUrl: String?,
  agentDescription: String?
) {

  val selectedAbility = remember { mutableStateOf(0) }

  LazyColumn(
    modifier = Modifier.padding(16.dp)
  ){
    item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
        ) {
          Card(
            modifier = Modifier
              .size(56.dp, 2.dp),
            shape = MaterialTheme.shapes.small,
            backgroundColor = Color.White,
            content = { }
          )
        }
    }
    item {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        NetworkImage(
          modifier = Modifier.size(56.dp),
          url = agentDisplayIcon.replaceIfNull(),
          contentScale = ContentScale.Fit
        )
        Text(
          modifier = Modifier
            .padding(start = 16.dp)
            .weight(1f),
          text = agentDisplayName.replaceIfNull(),
          textAlign = TextAlign.Center,
          color = Color.White,
          style = MaterialTheme.typography.h3,
        )
        NetworkImage(
          modifier = Modifier.size(56.dp),
          url = role?.displayIcon.replaceIfNull(),
          contentScale = ContentScale.Fit
        )
      }
    }
    item {
      Column(
        modifier = Modifier.padding(top = 24.dp)
      ) {
        Text(
          text = "Role",
          color = Color.White,
          style = MaterialTheme.typography.h5
        )
        Row(
          modifier = Modifier.padding(top = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          NetworkImage(
            modifier = Modifier.size(24.dp),
            url = role?.displayIcon.replaceIfNull(),
            contentScale = ContentScale.Fit
          )
          Text(
            modifier = Modifier.padding(start = 8.dp),
            text = role?.displayName.replaceIfNull(),
            color = Color.White
          )
        }
        Text(
          text = role?.description.replaceIfNull(),
          color = Color.White
        )
      }
    }
    item {
      Text(
        modifier = Modifier.padding(top = 24.dp) ,
        text = "Abilities",
        color = Color.White,
        style = MaterialTheme.typography.h5
      )
    }
    item {
      Row(
        modifier = Modifier.fillMaxWidth()
      ) {
        abilities?.forEachIndexed { index, ability ->
          val borderStrokeWidth = if (selectedAbility.value == index) {
            2.dp
          } else {
            0.dp
          }

          Column(
            modifier = Modifier
              .padding(top = 16.dp, start = 4.dp, end = 4.dp)
              .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = ability.slot.replaceIfNull(),
              color = Color.White,
              style = MaterialTheme.typography.overline
            )
            Card(
              modifier = Modifier
                .clickable {
                  selectedAbility.value = index
                },
              border = BorderStroke(borderStrokeWidth, Color.White),
              backgroundColor = Color.Transparent
            ) {
              Column(
                modifier = Modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                NetworkImage(
                  modifier = Modifier.size(124.dp),
                  url = ability.displayIcon.replaceIfNull(),
                  contentScale = ContentScale.Fit
                )
                Text(
                  text = ability.displayName.replaceIfNull(),
                  color = Color.White,
                  style = MaterialTheme.typography.caption
                )
              }
            }
          }
        }
      }
    }
    item {
      Row (
        modifier = Modifier.padding(top = 16.dp)
      ) {
        val abilityDescription = abilities?.get(selectedAbility.value)?.description
        Text(
          text = abilityDescription.replaceIfNull(),
          textAlign = TextAlign.Justify,
          color = Color.White,
          style = MaterialTheme.typography.caption
        )
      }
    }
    item {
      val context = LocalContext.current

      Column(
        modifier = Modifier.padding(top = 16.dp)
      ) {
        Text(
          text = "Voice Line",
          color = Color.White,
          style = MaterialTheme.typography.h5
        )
        OutlinedButton(
          modifier = Modifier.fillMaxWidth(),
          onClick = { audioUrl?.let { playAudio(context, it) } }
        ) {
          Text("Play Voice Line")
        }
      }
    }
    item {
      Column(
        modifier = Modifier.padding(top = 16.dp)
      ) {
        Text(
          text = agentDescription.replaceIfNull(),
          textAlign = TextAlign.Justify,
          color = Color.White,
          style = MaterialTheme.typography.h5
        )
      }
    }
  }
}

private fun playAudio(context: Context, audioUrl: String) {
  val audioAttributes = AudioAttributes.Builder()
    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
    .build()
  val mediaPlayer = MediaPlayer().apply {
    setAudioAttributes(audioAttributes)
  }

  try {
    mediaPlayer.reset()
    mediaPlayer.setDataSource(context, Uri.parse(audioUrl))
    mediaPlayer.prepareAsync()
    mediaPlayer.setOnPreparedListener { mp ->
      mp.start()
    }
    mediaPlayer.setOnCompletionListener {
    }
  } catch (exception: IOException) {
    exception.printStackTrace()
  }
}