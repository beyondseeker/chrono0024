package com.objectfanatics.composesample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Root 以外の composable function に対しては、以下のような方式を思案中。
 * - Composable function は、immutable な state と event handler を受け取るようにする。
 * - state と event handler が複数ある場合は、interface を定義する。
 * 　例えば、Xxx という名前の composable function が多数の state と event handler を持つ場合、
 *   XxxState と XxxEventHandler という interface を定義してそれらを引数で受け取る形にしたほうが
 *   設計が明確になるように思える。
 * - 以下のような手順を採用すると、品質や保守性の点でよさそう。
 *    1. Composable function の視点で composable function 側に state と event handler の interface を作成する。
 *    2. 一次情報の視点で ViewModel を設計する。
 *    3. ViewModel に state と event handler を implements し、一次情報からの composable function の state 用の
 *      パイプラインの構築や、event handler の実装を行う。
 * - Compose の State ではなく、kotlin flow の StateFlow を利用したほうがよさそう。
 *   ViewModel 内部で情報を compose の State として扱うと kotlin flow の機能が使えなくなるため、一次情報からのデータ導出用の
 *   パイプラインを組むのが困難になりそう。
 * - Composable function 内部で `collectAsState()` を経由せず StateFlow から直接 value を取得してしまうような
 *   バグが混入しそうな件に関しては、AndroidStudio 上でコンパイルエラーとなるため、問題とはならなそう。
 */
@Composable
fun HelloContent(nameStateFlow: StateFlow<String>, onNameChange: (name: String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        val name = nameStateFlow.collectAsState().value

        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HelloContentPreview() {
    val nameStateFlow = MutableStateFlow("")

    HelloContent(nameStateFlow, nameStateFlow::value::set)
}