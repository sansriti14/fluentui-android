package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Dialog
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity

class V2DialogActivity : V2DemoActivity() {
    override var demoActivityLink =
        "https://github.com/microsoft/fluentui-android/blob/master/FluentUI.Demo/src/main/java/com/microsoft/fluentuidemo/demos/V2DialogActivity.kt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateDialogActivityUI(this)
        }
    }

    @Composable
    private fun CreateDialogActivityUI(context: Context) {
        var showDialog by remember { mutableStateOf(false) }
        var dismissOnClickOutside by remember { mutableStateOf(false) }
        var dismissOnBackPress by remember { mutableStateOf(false) }
        val resources = LocalContext.current.resources
        Column(
            modifier = Modifier.padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ListItem.Item(
                text = resources.getString(R.string.dismiss_dialog_outside),
                subText = if (dismissOnClickOutside)
                    resources.getString(R.string.fluentui_enabled)
                else
                    resources.getString(R.string.fluentui_disabled),
                trailingAccessoryView = {
                    ToggleSwitch(
                        modifier = Modifier.testTag("outside press"),
                        onValueChange = {
                            dismissOnClickOutside = it
                        },
                        checkedState = dismissOnClickOutside
                    )
                }
            )
            ListItem.Item(
                text = resources.getString(R.string.dismiss_dialog_back),
                subText = if (dismissOnBackPress)
                    resources.getString(R.string.fluentui_enabled)
                else
                    resources.getString(R.string.fluentui_disabled),
                trailingAccessoryView = {
                    ToggleSwitch(
                        modifier = Modifier.testTag("back press"),
                        onValueChange = {
                            dismissOnBackPress = it
                        },
                        checkedState = dismissOnBackPress
                    )
                }
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    style = ButtonStyle.OutlinedButton,
                    text = resources.getString(R.string.show_dialog),
                    onClick = { showDialog = !showDialog }
                )
            }
            if (showDialog) {
                Dialog(
                    onDismiss = {
                        showDialog = !showDialog
                        Toast.makeText(
                            context,
                            resources.getString(R.string.dismiss_dialog),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    dismissOnClickedOutside = dismissOnClickOutside,
                    dismissOnBackPress = dismissOnBackPress
                ) {
                    Column(Modifier.padding(all = 16.dp)) {
                        Label(
                            text = resources.getString(R.string.dialog_description),
                            textStyle = FluentAliasTokens.TypographyTokens.Body1
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Button(
                                style = ButtonStyle.TextButton,
                                text = resources.getString(R.string.cancel),
                                onClick = {
                                    showDialog = false
                                    Toast.makeText(
                                        context,
                                        resources.getString(R.string.cancel),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                })
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                style = ButtonStyle.TextButton,
                                text = resources.getString(R.string.ok),
                                onClick = {
                                    showDialog = false
                                    Toast.makeText(
                                        context,
                                        resources.getString(R.string.ok),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        }
                    }
                }
            }
        }

    }
}