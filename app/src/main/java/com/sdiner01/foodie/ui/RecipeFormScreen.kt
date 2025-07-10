package com.sdiner01.foodie.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.sdiner01.foodie.data.Recipe
import com.sdiner01.foodie.viewmodel.RecipeViewModel

@Composable
fun RecipeFormScreen(
    viewModel: RecipeViewModel = viewModel(),
    recipeId: Int = -1,
    onSave: () -> Unit
) {
    val existingRecipe by if (recipeId != -1) {
        viewModel.getRecipeById(recipeId).observeAsState()
    } else {
        mutableStateOf(null)
    }

    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Other") }

    LaunchedEffect(existingRecipe) {
        existingRecipe?.let {
            title = it.title
            ingredients = it.ingredients
            instructions = it.instructions
            category = it.category
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            if (recipeId == -1) "Add New Recipe" else "Edit Recipe",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Instructions") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            "Category",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        DropdownMenuBox(category) { selected ->
            category = selected
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val recipe = Recipe(
                    id = existingRecipe?.id ?: 0,
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    category = category
                )

                if (existingRecipe == null) {
                    viewModel.insert(recipe)
                } else {
                    viewModel.update(recipe)
                }

                onSave()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
        ) {
            Text("Save", color = Color.White)
        }
    }
}

@Composable
fun DropdownMenuBox(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("Breakfast", "Brunch", "Lunch", "Dinner", "Desserts", "Other")
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedCategory)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}
