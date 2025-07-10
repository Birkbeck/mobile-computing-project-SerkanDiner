package com.sdiner01.foodie.data

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    suspend fun update(recipe: Recipe) {
        recipeDao.update(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    suspend fun getRecipeById(id: Int): Recipe? {
        return recipeDao.getRecipeById(id)
    }
}
