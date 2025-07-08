package com.sdiner01.foodie.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sdiner01.foodie.data.Recipe
import com.sdiner01.foodie.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(
    onRecipeClick: (Recipe) -> Unit,
    viewModel: RecipeViewModel = viewModel()
) {
    val recipes = viewModel.allRecipes.observeAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "My Recipes",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(recipes.value) { recipe ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onRecipeClick(recipe) }
                ) {
                    Text(
                        text = recipe.title,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
