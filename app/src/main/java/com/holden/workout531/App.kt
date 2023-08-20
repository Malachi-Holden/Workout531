package com.holden.workout531

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.holden.workout531.workoutPlan.ForBeginners531
import kotlinx.coroutines.launch

val testPlan = ForBeginners531(100.0, 100.0, 100.0, 100.0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val backStackEntry by navController.currentBackStackEntryAsState()
    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer()
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("5/3/1") },
                    navigationIcon = {
                        AppBarIcon(
                            backStackEntry = backStackEntry,
                            onBack = { navController.popBackStack() },
                            openDrawer = { scope.launch{ drawerState.open() } }
                        )
                    }
                )
            }
        ) {
            Box(modifier = Modifier
                .padding(it)
                .padding(horizontal = 10.dp)){
                WorkoutNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun AppBarIcon(backStackEntry: NavBackStackEntry?, onBack: ()->Unit, openDrawer: ()->Unit){
    val route = backStackEntry?.destination?.route
    when (route){
        Destination.ChoosePlan.routeString() -> {}
        Destination.Plan.routeString() -> {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Back"
                )
            }
        }
        Destination.Detail.routeString() -> {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(){
    ModalDrawerSheet {

    }
}