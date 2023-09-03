package com.holden.workout531

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.holden.workout531.plates.CalculatePlatesView
import com.holden.workout531.plates.testPlateSet
import com.holden.workout531.utility.Modal
import com.holden.workout531.utility.viewModelWithLambda
import com.holden.workout531.workoutPlan.ForBeginnersInitializeView
import com.holden.workout531.workoutPlan.PRChartView
import com.holden.workout531.workoutPlan.PlanListView
import com.holden.workout531.workoutPlan.PlanRepository
import com.holden.workout531.workoutPlan.WorkoutPlan
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val viewModel: AppViewmodel = viewModelWithLambda { AppViewmodel(PlanRepository(context)) }
    LaunchedEffect(backStackEntry){
        drawerState.close()
    }
    var showForBeginnersView by remember { mutableStateOf(false) }
    var showPRView by remember { mutableStateOf(false) }
    val currentPlan by viewModel.workoutPlan.collectAsState()
    var calculatePlatesAmount: Double? by remember { mutableStateOf(null) }
    Box(modifier = Modifier.fillMaxSize()){
        ModalNavigationDrawer(
            drawerContent = {
                AppDrawer(
                    currentDestination = backStackEntry?.destination?.route?.routeToDestination(),
                    showForBeginnersView = { showForBeginnersView = true },
                    planList = viewModel.plans.collectAsState().value,
                    currentPlanIndex = viewModel.currentPlanIndex.collectAsState().value,
                    onSelectPlan = {
                        viewModel.setCurrentPlanIndex(context, it)
                        scope.launch{ drawerState.close() }
                    },
                    onDeletePlan = {
                        viewModel.deletePlan(context, it)
                    }
                )
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(currentPlan?.name ?: "5/3/1") },
                        navigationIcon = {
                            AppBarIcon(
                                backStackEntry = backStackEntry,
                                onBack = { navController.popBackStack() },
                                openDrawer = { scope.launch{ drawerState.open() } }
                            )
                        },
                        actions = {
                            if (currentPlan != null) {
                                Button(onClick = { showPRView = true }) {
                                    Text(text = "View PRs")
                                }
                            }
                        }
                    )
                }
            ) {
                Box(modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 10.dp)){
                    WorkoutNavHost(navController = navController, onShowCalculatePlates = {
                        calculatePlatesAmount = it
                    })

                }
            }
        }
    }
    if (showForBeginnersView) {
        Modal(onClose = { showForBeginnersView = false }) {
            ForBeginnersInitializeView(onCreatePlan = { plan ->
                viewModel.createPlan(context, plan)
                showForBeginnersView = false
                scope.launch { drawerState.close() }
            })
        }
    }
    LaunchedEffect(showPRView){
        if (currentPlan == null && showPRView){
            Toast.makeText(context, "Select a plan to see PR chart", Toast.LENGTH_SHORT).show()
        }
    }
    if (showPRView) {
        val plan = currentPlan
        if (plan != null) {
            Modal(onClose = { showPRView = false }) {
                PRChartView(chart = plan.prChartData())
            }
        }
    }
    if (calculatePlatesAmount != null){
        Modal(onClose = { calculatePlatesAmount = null }) {
            CalculatePlatesView(plateSet = testPlateSet, goalWeight = calculatePlatesAmount)
        }
    }
}

@Composable
fun AppBarIcon(backStackEntry: NavBackStackEntry?, onBack: ()->Unit, openDrawer: ()->Unit){
    when (backStackEntry?.destination?.route){
        Destination.Plan.name -> {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Back"
                )
            }
        }
        Destination.Detail.name -> {
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
fun AppDrawer(
    currentDestination: Destination?,
    showForBeginnersView: ()->Unit,
    planList: List<WorkoutPlan>,
    currentPlanIndex: Int?,
    onSelectPlan: (Int)->Unit,
    onDeletePlan: (Int)->Unit
){
    ModalDrawerSheet {
        when (currentDestination){
            Destination.Plan -> {
                Button(onClick = showForBeginnersView) {
                    Text(text = "New 5/3/1 plan")
                }
            }
            else -> Unit
        }
        Divider()
        PlanListView(
            plans = planList,
            currentIndex = currentPlanIndex,
            onSelect = onSelectPlan,
            onDelete = onDeletePlan
        )
    }
}