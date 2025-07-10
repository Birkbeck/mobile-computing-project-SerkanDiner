package com.sdiner01.foodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.sdiner01.foodie.ui.RecipeFormScreen
import com.sdiner01.foodie.ui.RecipeListScreen
import com.sdiner01.foodie.ui.RecipeDetailScreen
import com.sdiner01.foodie.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel: RecipeViewModel = viewModel()

    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(navController = navController, startDestination = "list") {

            // Home screen
            composable("list") {
                RecipeListScreen(
                    viewModel = viewModel,
                    onAddRecipe = {
                        navController.navigate("form")
                    },
                    onRecipeClick = { recipe ->
                        navController.navigate("detail/${recipe.id}")
                    }
                )
            }

            // Add/edit form
            composable(
                route = "form?recipeId={recipeId}",
                arguments = listOf(
                    navArgument("recipeId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1
                RecipeFormScreen(
                    viewModel = viewModel,
                    recipeId = recipeId,
                    onSave = { navController.popBackStack() }
                )
            }

            // Recipe detail screen
            composable("detail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
                RecipeDetailScreen(
                    recipeId = id,
                    viewModel = viewModel,
                    onDelete = { navController.popBackStack() },
                    onEdit = { recipeId ->
                        navController.navigate("form?recipeId=$recipeId")
                    }
                )
            }
        }
    }
}
