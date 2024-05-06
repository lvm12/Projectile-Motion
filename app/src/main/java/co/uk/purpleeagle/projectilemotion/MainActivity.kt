package co.uk.purpleeagle.projectilemotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import co.uk.purpleeagle.projectilemotion.ui.theme.ProjectileMotionTheme
import co.uk.purpleeagle.projectilemotion.view.App
import co.uk.purpleeagle.projectilemotion.view.PmViewModel
import co.uk.purpleeagle.projectilemotion.view.PmViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: PmViewModel = PmViewModelFactory(navController).create(PmViewModel::class.java)
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize(),
            ) {
                LaunchedEffect(Unit) {
                    with(this@BoxWithConstraints){
                        viewModel.setDimensions(
                            this.maxHeight.value.toDouble() * 1.1,
                            this.maxWidth.value.toDouble() * 6
                        )
                    }
                }
                ProjectileMotionTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        App(navHost = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

