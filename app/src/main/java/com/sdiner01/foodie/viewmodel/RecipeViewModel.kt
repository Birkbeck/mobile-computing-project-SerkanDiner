package com.sdiner01.foodie.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sdiner01.foodie.data.Recipe
import com.sdiner01.foodie.data.RecipeDatabase
import com.sdiner01.foodie.data.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository
    val allRecipes: LiveData<List<Recipe>>

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDao)
        allRecipes = repository.allRecipes
    }

    fun insert(recipe: Recipe) = viewModelScope.launch {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) = viewModelScope.launch {
        repository.update(recipe)
    }

    fun delete(recipe: Recipe) = viewModelScope.launch {
        repository.delete(recipe)
    }

    fun getRecipeById(id: Int): LiveData<Recipe?> {
        val recipeLiveData = MutableLiveData<Recipe?>()
        viewModelScope.launch {
            recipeLiveData.postValue(repository.getRecipeById(id))
        }
        return recipeLiveData
    }
}
