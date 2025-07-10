package com.sdiner01.foodie.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sdiner01.foodie.data.Recipe
import com.sdiner01.foodie.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(
    onRecipeClick: (Recipe) -> Unit,
    onAddRecipe: () -> Unit,
    viewModel: RecipeViewModel = viewModel()
) {
    val recipes = viewModel.allRecipes.observeAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddRecipe,
                containerColor = Color(0xFFFF9800) // Orange
            ) {
                Text("+", color = Color.White, fontSize = 24.sp)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFF3E0)) // light orange background
                .padding(16.dp)
        ) {
            // App Logo / Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🍴 Foodie",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800)
                )
            }

            Text(
                text = "Your Recipes",
                style = MaterialTheme.typography.titleMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (recipes.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No recipes yet!\nTap + to add your first recipe.",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn {
                    items(
                        items = recipes.value,
                        key = { it.id }
                    ) { recipe ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { onRecipeClick(recipe) },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = recipe.title,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFFF9800)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = recipe.category,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
