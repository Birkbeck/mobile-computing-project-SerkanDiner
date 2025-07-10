package com.sdiner01.foodie.ui

import androidx.compose.foundation.background
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
import com.sdiner01.foodie.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    viewModel: RecipeViewModel = viewModel(),
    onDelete: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val recipe by viewModel.getRecipeById(recipeId).observeAsState()

    if (recipe == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Recipe not found", color = Color.Red)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF3E0))
                .padding(16.dp)
        ) {

            Text(
                text = recipe!!.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Category: ${recipe!!.category}",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            Text(
                "Ingredients",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.DarkGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                recipe!!.ingredients,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                "Instructions",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.DarkGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                recipe!!.instructions,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onEdit(recipe!!.id) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                ) {
                    Text("Edit", color = Color.White)
                }

                Button(
                    onClick = {
                        viewModel.delete(recipe!!)
                        onDelete()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete", color = Color.White)
                }
            }
        }
    }
}
