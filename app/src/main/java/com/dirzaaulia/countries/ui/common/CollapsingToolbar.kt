package com.dirzaaulia.countries.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

private val headerHeight = 244.dp
private val toolbarHeight = 56.dp

private val paddingMedium = 16.dp

private val titlePaddingStart = 16.dp
private val titlePaddingEnd = 16.dp

private const val titleFontScaleStart = 1f
private const val titleFontScaleEnd = 0.66f

@Composable
fun CollapsingToolbar(
  title: String,
  imageUrl: String,
  isPlaceholder: Boolean = false,
  bodyContent: @Composable () -> Unit
) {
  val scroll: ScrollState = rememberScrollState(0)

  val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
  val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

  Surface {
    Box(
      modifier = Modifier.fillMaxSize()
    ) {
      Header(
        scroll = scroll,
        headerHeightPx = headerHeightPx,
        imageUrl = imageUrl,
        isPlaceholder = isPlaceholder
      )
      Body(
        scroll = scroll,
        content = bodyContent,
        isPlaceholder = isPlaceholder
      )
      Toolbar(
        scroll = scroll,
        headerHeightPx = headerHeightPx,
        toolbarHeightPx = toolbarHeightPx
      )
      Title(
        scroll = scroll,
        headerHeightPx = headerHeightPx,
        toolbarHeightPx = toolbarHeightPx,
        title = title
      )
    }
  }
}

@Composable
private fun Header(
  scroll: ScrollState,
  headerHeightPx: Float,
  imageUrl: String,
  isPlaceholder: Boolean
) {
  Box(modifier = Modifier
    .placeholder(
      visible = isPlaceholder,
      color = MaterialTheme.colorScheme.surfaceVariant,
      highlight = PlaceholderHighlight.shimmer()
    )
    .fillMaxWidth()
    .height(headerHeight)
    .graphicsLayer {
      translationY = -scroll.value.toFloat() / 2f // Parallax effect
      alpha = (-1f / headerHeightPx) * scroll.value + 1
    }
  ) {
    NetworkImage(
      modifier = Modifier.fillMaxSize(),
      url = imageUrl
    )

    Box(
      Modifier
        .fillMaxSize()
        .background(
          brush = Brush.verticalGradient(
//            colors = listOf(Color.Transparent, Color(0xAA000000)),
            colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surfaceVariant),
            startY = 3.5f * headerHeightPx / 4 // Gradient applied to wrap the title only
          )
        )
    )
  }
}

@Composable
private fun Body(
  scroll: ScrollState,
  isPlaceholder: Boolean,
  content: @Composable () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .statusBarsPadding()
      .verticalScroll(
        state = scroll,
        enabled = !isPlaceholder
      )
  ) {
    Spacer(modifier = Modifier.height(headerHeight))
    content()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float) {
  val toolbarBottom = headerHeightPx - toolbarHeightPx
  val showToolbar by remember {
    derivedStateOf {
      scroll.value >= toolbarBottom
    }
  }

  AnimatedVisibility(
    visible = showToolbar,
    enter = fadeIn(animationSpec = tween(300)),
    exit = fadeOut(animationSpec = tween(300))
  ) {
    TopAppBar(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding(),
      title = {},
      colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = Color.Transparent
      )
    )
  }
}

@Composable
private fun Title(
  scroll: ScrollState,
  headerHeightPx: Float,
  toolbarHeightPx: Float,
  title: String
) {
  var titleHeightPx by remember { mutableStateOf(0f) }
  var titleWidthPx by remember { mutableStateOf(0f) }

  Column {
    Text(
      text = title,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      style = MaterialTheme.typography.displaySmall,
      modifier = Modifier
        .statusBarsPadding()
        .graphicsLayer {
          val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
          val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

          val scaleXY = lerp(
            titleFontScaleStart.dp,
            titleFontScaleEnd.dp,
            collapseFraction
          )

          val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

          val titleYFirstInterpolatedPoint = lerp(
            headerHeight - titleHeightPx.toDp() - paddingMedium,
            headerHeight / 2,
            collapseFraction
          )

          val titleXFirstInterpolatedPoint = lerp(
            titlePaddingStart,
            (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
            collapseFraction
          )

          val titleYSecondInterpolatedPoint = lerp(
            headerHeight / 2,
            toolbarHeight / 2 - titleHeightPx.toDp() / 2,
            collapseFraction
          )

          val titleXSecondInterpolatedPoint = lerp(
            (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
            titlePaddingEnd - titleExtraStartPadding,
            collapseFraction
          )

          val titleY = lerp(
            titleYFirstInterpolatedPoint,
            titleYSecondInterpolatedPoint,
            collapseFraction
          )

          val titleX = lerp(
            titleXFirstInterpolatedPoint,
            titleXSecondInterpolatedPoint,
            collapseFraction
          )

          translationY = titleY.toPx()
          translationX = titleX.toPx()
          scaleX = scaleXY.value
          scaleY = scaleXY.value
        }
        .onGloballyPositioned {
          titleHeightPx = it.size.height.toFloat()
          titleWidthPx = it.size.width.toFloat()
        }
    )
    Row(modifier = Modifier.height(104.dp)) {

    }
  }
}