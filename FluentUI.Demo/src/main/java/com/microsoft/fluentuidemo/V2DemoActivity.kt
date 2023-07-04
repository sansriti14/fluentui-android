package com.microsoft.fluentuidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.Scaffold
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheet
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheetState
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheetValue
import com.microsoft.fluentui.tokenized.bottomsheet.rememberBottomSheetState
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Menu
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs
import kotlinx.coroutines.launch

enum class Controls {
    Params,
    ControlTokens
}

open class V2DemoActivity : ComponentActivity() {
    companion object {
        const val DEMO_TITLE = "demo_title"
    }

    private var content: @Composable () -> Unit = {}
    fun setActivityContent(content: @Composable () -> Unit) {
        this@V2DemoActivity.content = content
    }

    private var bottomBar: @Composable (RowScope.() -> Unit)? = null
    fun setBottomBar(bottomBar: @Composable (RowScope.() -> Unit)) {
        this@V2DemoActivity.bottomBar = bottomBar
    }

    private var bottomSheetContent: @Composable () -> Unit = {}
    fun setBottomSheetContent(bottomSheetContent: @Composable () -> Unit) {
        this@V2DemoActivity.bottomSheetContent = bottomSheetContent
    }

    open val appBarSize = AppBarSize.Medium

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val demoTitle = intent.getSerializableExtra(DEMO_TITLE) as String
        setContent {
            FluentTheme {
                AppTheme.SetStatusBarColor()

                val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Hidden)
                val scope = rememberCoroutineScope()

                Scaffold(
                    topBar = {
                        AppBar(
                            title = demoTitle,
                            navigationIcon = FluentIcon(
                                SearchBarIcons.Arrowback,
                                contentDescription = "Navigate Back",
                                onClick = { Navigation.backNavigation(this) }
                            ),
                            style = AppTheme.appThemeStyle.value,
                            appBarSize = appBarSize,
                            bottomBar = bottomBar,
                            rightAccessoryView = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_fluent_info_24_regular),
                                    contentDescription = "Control Token Icon",
                                    modifier = Modifier.clickable {
//                                        bottomSheetState = BottomSheetState(BottomSheetValue.Shown)
                                        scope.launch { bottomSheetState.expand() }
                                    },
                                    tint = if (AppTheme.appThemeStyle.value == FluentStyle.Neutral) {
                                        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                                            FluentTheme.themeMode
                                        )
                                    } else {
                                        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
                                            FluentTheme.themeMode
                                        )
                                    }
                                )

                                var expandedMenu by remember { mutableStateOf(false) }

                                Box {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_fluent_more_vertical_24_regular),
                                        contentDescription = "More",
                                        modifier = Modifier
                                            .padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
                                            .clickable { expandedMenu = true },
                                        tint = if (AppTheme.appThemeStyle.value == FluentStyle.Neutral) {
                                            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                                                FluentTheme.themeMode
                                            )
                                        } else {
                                            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
                                                FluentTheme.themeMode
                                            )
                                        }
                                    )
                                    Menu(
                                        opened = expandedMenu,
                                        onDismissRequest = { expandedMenu = false },
                                    ) {
                                        val scrollState = rememberScrollState()
                                        Column(
                                            modifier = Modifier.verticalScroll(scrollState)
                                        ) {
                                            ListItem.Item(
                                                text = "Brand",
                                                trailingAccessoryView = {
                                                    ToggleSwitch(
                                                        onValueChange = {
                                                            if (it) {
                                                                AppTheme.updateThemeStyle(
                                                                    FluentStyle.Brand
                                                                )
                                                            } else {
                                                                AppTheme.updateThemeStyle(
                                                                    FluentStyle.Neutral
                                                                )
                                                            }
                                                        },
                                                        checkedState = AppTheme.appThemeStyle.value == FluentStyle.Brand,
                                                    )
                                                },
                                                border = BorderType.Bottom,
                                                listItemTokens = CustomizedTokens.listItemTokens
                                            )

                                            ListItem.SectionHeader(
                                                title = "Choose your brand theme:",
                                                enableChevron = false,
                                                style = SectionHeaderStyle.Subtle,
                                                listItemTokens = CustomizedTokens.listItemTokens
                                            )

                                            Column(
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = FluentGlobalTokens.size(
                                                            FluentGlobalTokens.SizeTokens.Size160
                                                        )
                                                    ),
                                                verticalArrangement = Arrangement.spacedBy(
                                                    FluentGlobalTokens.size(
                                                        FluentGlobalTokens.SizeTokens.Size100
                                                    )
                                                )
                                            ) {
                                                AppTheme.SetAppTheme()
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    },

                    ) {
                    Box(
                        modifier = Modifier.padding(it)
                    ) {
                        var selectedControl by remember { mutableStateOf(Controls.Params) }
                        BottomSheet(
                            sheetContent = {
                                    val controlsList = listOf(
                                        PillMetaData(
                                            text = "Params",
                                            enabled = true,
                                            onClick = {
                                                selectedControl = Controls.Params
                                            }
                                        ),
                                        PillMetaData(
                                            text = "Control Tokens",
                                            enabled = true,
                                            onClick = {
                                                selectedControl = Controls.ControlTokens
                                            }
                                        )
                                    ) as MutableList<PillMetaData>

                                    PillTabs(
                                        style = FluentStyle.Neutral,
                                        metadataList = controlsList,
                                        selectedIndex = selectedControl.ordinal
                                    )

                                Column(
                                    modifier = Modifier
                                        .padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
                                        .verticalScroll(rememberScrollState())
                                ) {
                                    bottomSheetContent()
                                }
                            },
                            sheetState = bottomSheetState,
                        ) {
                            content()
                        }
                    }
                }
            }
        }
    }
}