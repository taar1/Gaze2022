package net.gazeapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import net.gazeapp.R
import net.gazeapp.data.model.Contact
import net.gazeapp.ui.composables.toolbar.CollapsingToolbar
import net.gazeapp.ui.composables.toolbar.ToolbarState

private val MinToolbarHeight = 96.dp
private val MaxToolbarHeight = 176.dp


@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    TODO() //Return a 'ToolbarState' implementation and preserve its internal state.
}

@Composable
fun Catalog(
    contacts: List<Contact>,
    columns: Int,
    onPrivacyTipButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val toolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberToolbarState(toolbarHeightRange)
    val listState = rememberLazyListState()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                toolbarState.scrollTopLimitReached =
                    listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                toolbarState.scrollOffset = toolbarState.scrollOffset - available.y
                return Offset(0f, toolbarState.consumed)
            }
        }
    }

    // TODO: https://medium.com/kotlin-and-kotlin-for-android/collapsing-toolbar-in-jetpack-compose-lazycolumn-version-f1b0a7924ffe#af8d
    // TODO bei "scrollstate" weiterfahren - oder was anderem aus der auswahl:
//
//    Continue by clicking on the scroll flag you’re interested in:
//
//    ScrollState
//    EnterAlwaysState
//    EnterAlwaysCollapsedState
//    ExitUntilCollapsedState

    Box(modifier = modifier.nestedScroll(nestedScrollConnection)) {
        LazyCatalog(
            animals = contacts,
            columns = columns,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationY = toolbarHeightRange.last.toFloat() },
            listState = listState,
            contentPadding = PaddingValues(bottom = if (toolbarState is FixedScrollFlagState) MinToolbarHeight else 0.dp)
        )
        CollapsingToolbar(
            backgroundImageResId = R.drawable.toolbar_background,
            progress = toolbarState.progress,
            onPrivacyTipButtonClicked = onPrivacyTipButtonClicked,
            onSettingsButtonClicked = onSettingsButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                .graphicsLayer { translationY = toolbarState.offset }
        )
    }
}