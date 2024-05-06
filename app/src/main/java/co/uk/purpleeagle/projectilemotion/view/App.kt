package co.uk.purpleeagle.projectilemotion.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun App(
    navHost: NavHostController,
    viewModel: PmViewModel
) {
    val world by viewModel.world.collectAsState()
    NavHost(navHost, "start"){
        composable("start"){
            SelectScreen (
                start = viewModel::start,
                setDetails = viewModel::setDetails,
            )
        }
        composable("simulation"){
                SimulationScreen(
                    world = world,
                    stop = viewModel::stop,
                    pause = viewModel::pause,
                    resume = viewModel::resume,
                )
        }
    }
}