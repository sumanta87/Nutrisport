package com.nutrisport.home

import ContentWithMessageBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nutrisport.home.component.ButtomBar
import com.nutrisport.home.component.CustomDrawer
import com.nutrisport.home.domain.ButtomBarDestination
import com.nutrisport.home.domain.CustomerDrawerState
import com.nutrisport.home.domain.isOpen
import com.nutrisport.home.domain.opposite
import com.nutrisport.shared.Alpha
import com.nutrisport.shared.BebasNeueFont
import com.nutrisport.shared.FrontSize
import com.nutrisport.shared.IconPrimary
import com.nutrisport.shared.Resources
import com.nutrisport.shared.Surface
import com.nutrisport.shared.SurfaceLighter
import com.nutrisport.shared.TextPrimary
import com.nutrisport.shared.Utill.getScreenWidth
import com.nutrisport.shared.navigation.Screen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphScreen(
    navigateToAuth:()-> Unit
) {
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
    val screenWidth = remember { getScreenWidth() }
    var drawerState by remember { mutableStateOf(CustomerDrawerState.closed) }
    val offsetValue by remember { derivedStateOf { (screenWidth/1.5).dp }  }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpen())offsetValue else 0.dp
    )

    val animatedBackground by animateColorAsState(
        targetValue = if (drawerState.isOpen())SurfaceLighter else Surface
    )
    val animatedScale by animateFloatAsState(
        targetValue =  if (drawerState.isOpen())0.9f else 1f
    )
    val animatedRadius by animateDpAsState(
        targetValue = if (drawerState.isOpen())20.dp else 0.dp
    )
    val viewModel = koinViewModel<HomeGraphViewModel>()
    val messageBarState = rememberMessageBarState()
   Box(
       modifier = Modifier
           .fillMaxSize()
           .background(animatedBackground)
           .systemBarsPadding()

   ){
       CustomDrawer(
           onProfileClick = {},
           onContactUsClick = {},
           onSignOutClick = {
               viewModel.signOut(
                   onSuccess = navigateToAuth,
                   onError = {message-> messageBarState.addError(message) }
               )
           },
           onAdminPanelClick = {}
       )
       Box(modifier = Modifier
           .fillMaxSize()
           .clip(RoundedCornerShape(size = animatedRadius))
           .offset(x = animatedOffset)
           .scale(scale = animatedScale)
           .shadow(
               elevation = 20.dp,
               shape = RoundedCornerShape(size = animatedRadius),
               ambientColor = Color.Black.copy(alpha = Alpha.DISABLE),
               spotColor = Color.Black.copy(alpha = Alpha.DISABLE)
           )){
           Scaffold(
               containerColor = Surface,
               topBar = {
                   CenterAlignedTopAppBar(
                       title = {
                           AnimatedContent(targetState = selectDestination){ destination->
                               Text(
                                   text = destination.title,
                                   fontFamily = BebasNeueFont(),
                                   fontSize = FrontSize.LARGE,
                                   color = TextPrimary
                               )
                           }
                       },
                       navigationIcon = {
                           AnimatedContent(
                               targetState = drawerState
                           ) { drawer ->
                             if (drawer.isOpen())
                             {
                                 IconButton(onClick = {drawerState = drawerState.opposite()}){
                                     Icon(
                                         painter = painterResource(Resources.Icon.Close),
                                         contentDescription = "Close Icon",
                                         tint = IconPrimary
                                     )
                                 }

                             }
                               else{
                                 IconButton(onClick = {drawerState = drawerState.opposite()}){
                                     Icon(
                                         painter = painterResource(Resources.Icon.Menu),
                                         contentDescription = "Menu Icon",
                                         tint = IconPrimary
                                     )
                                 }
                             }
                           }
                       },
                       colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                           containerColor = Surface,
                           scrolledContainerColor = Surface,
                           navigationIconContentColor = IconPrimary,
                           titleContentColor = TextPrimary,
                           actionIconContentColor = IconPrimary
                       )
                   )
               }
           ) { padding ->
               ContentWithMessageBar(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(
                       top = padding.calculateTopPadding(),
                       bottom = padding.calculateBottomPadding()
                   ),
                   messageBarState = messageBarState,
                   errorMaxLines = 2,
                   contentBackgroundColor = Surface
               ){
                   Column(modifier = Modifier.fillMaxSize()) {
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
       }
   }

}