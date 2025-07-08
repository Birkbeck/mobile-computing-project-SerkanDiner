package com.sdiner01.foodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.sdiner01.foodie.ui.RecipeFormScreen
import com.sdiner01.foodie.ui.RecipeListScreen
import com.sdiner01.foodie.viewmodel.RecipeViewModel
import com.sdiner01.foodie.ui.theme.FoodieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodieTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel: RecipeViewModel = viewModel()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            RecipeListScreen(
                onRecipeClick = {
                    // You can implement edit later, for now we just navigate to form
                    navController.navigate("form")
                }
            )
            FloatingActionButton(
                onClick = { navController.navigate("form") },
                modifier = androidx.compose.ui.Modifier.padding(16.dp)
            ) {
                Text("+")
            }
        }
        composable("form") {
            RecipeFormScreen(
                viewModel = viewModel,
                onSave = { navController.popBackStack() }
            )
        }
    }
}
