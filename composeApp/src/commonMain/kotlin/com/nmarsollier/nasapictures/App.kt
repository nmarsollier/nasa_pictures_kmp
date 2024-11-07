package com.nmarsollier.nasapictures

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import coil3.compose.LocalPlatformContext
import com.nmarsollier.nasapictures.common.navigation.AppNavActions
import com.nmarsollier.nasapictures.common.navigation.AppNavigationHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    val koin = getKoin()
    val navController = rememberNavController()
    val context = LocalPlatformContext.current

    remember(navController) {
        AppNavActions(navController).also { mavActions ->
            koin.loadModules(
                listOf(
                    module {
                        single { mavActions }
                        single<coil3.PlatformContext> { context }
                    }
                ),
                allowOverride = true
            )
        }
    }

    AppNavigationHost()
}