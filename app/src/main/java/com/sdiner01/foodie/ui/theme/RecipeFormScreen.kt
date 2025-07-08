package com.sdiner01.foodie.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sdiner01.foodie.data.Recipe
import com.sdiner01.foodie.viewmodel.RecipeViewModel

@Composable
fun RecipeFormScreen(
    viewModel: RecipeViewModel = viewModel(),
    onSave: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Other") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Add New Recipe", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Instructions") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenuBox(category) { selected ->
            category = selected
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val recipe = Recipe(
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    category = category
                )
                viewModel.insert(recipe)
                onSave()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@Composable
fun DropdownMenuBox(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("Breakfast", "Brunch", "Lunch", "Dinner", "Desserts", "Other")
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
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
