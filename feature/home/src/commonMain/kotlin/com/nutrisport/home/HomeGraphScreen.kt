package com.nutrisport.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nutrisport.home.component.ButtomBar
import com.nutrisport.shared.Surface
import com.nutrisport.shared.navigation.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.nutrisport.home.domain.ButtomBarDestination

@Composable
fun HomeGraphScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
    val selectDestination by remember {
        derivedStateOf {
            val route = currentRoute.value?.destination?.route.toString()
            when{
                route.contains(ButtomBarDestination.ProductOverview.screen.toString())-> ButtomBarDestination.ProductOverview
                route.contains(ButtomBarDestination.Cart.screen.toString())-> ButtomBarDestination.Cart
                route.contains(ButtomBarDestination.Categories.screen.toString())-> ButtomBarDestination.Categories
                else -> ButtomBarDestination.ProductOverview
            }
        }
    }
    Scaffold(
        containerColor = Surface,
        topBar = {

        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = Screen.ProductOverview
            ){
                composable<Screen.ProductOverview> {  }
                composable <Screen.Cart>{  }
                composable <Screen.Catagory> {  }

            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier =
                    Modifier.padding(12.dp)
            ) {
                ButtomBar(
                    selected = selectDestination,
                    onSelect = { destination ->
                       navController.navigate(destination.screen){
                           launchSingleTop =true
                           popUpTo<Screen.ProductOverview>{
                               saveState = true
                               inclusive =false
                           }
                           restoreState =true
                       }
                    }
                )
            }
        }

    }

}