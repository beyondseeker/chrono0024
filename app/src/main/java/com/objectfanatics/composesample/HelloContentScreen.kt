package com.objectfanatics.composesample

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * XxxActivity 直下に必ず XxxScreen という XxxActivity の唯一の composable function を用意するという
 * ルールを採用してみた。
 *
 * 今回はほぼ単なるバケツリレーになってしまうので必然性が低いのだが、以下の点で意味があると考えている。
 * - Root となる composable に ViewModel が配置されるというルールを今後採用していきたい。
 *   ※ 元々、Activity や Fragment に対して ViewModel が存在するという点が気持ち悪いと思っていたのだが、
 *     まだ Composition の Root に対応する ViewModel が存在するというのであれば納得できる気がする。
 * - Root 以外の composable function に対しては、state と event handler を引数としたい。
 *   そのため、今回のように root の composable function しか存在しない場合でも
 *   XxxScreen の存在は意味があると思われる。
 */
@Composable
fun HelloContentScreen(viewModel: HelloContentScreenViewModel = viewModel()) {
    HelloContent(viewModel.nameStateFlow, viewModel::onNameChange)
}