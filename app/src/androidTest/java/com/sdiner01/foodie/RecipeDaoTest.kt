package com.sdiner01.foodie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sdiner01.foodie.data.Recipe
import com.sdiner01.foodie.data.RecipeDao
import com.sdiner01.foodie.data.RecipeDatabase
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipeDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RecipeDatabase
    private lateinit var dao: RecipeDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RecipeDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.recipeDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertRecipe_andGetAllRecipes_returnsInsertedRecipe() = runBlocking {
        val recipe = Recipe(
            title = "Test Recipe",
            ingredients = "Flour, Sugar",
            instructions = "Mix well",
            category = "Desserts"
        )

        dao.insert(recipe)

        val allRecipes = dao.getAllRecipes().getOrAwaitValue()
        assertEquals(1, allRecipes.size)
        assertEquals("Test Recipe", allRecipes[0].title)
        assertEquals("Desserts", allRecipes[0].category)
    }

    @Test
    fun deleteRecipe_removesRecipeFromDb() = runBlocking {
        val recipe = Recipe(
            title = "Test Recipe",
            ingredients = "Flour",
            instructions = "Mix",
            category = "Lunch"
        )

        dao.insert(recipe)

        var allRecipes = dao.getAllRecipes().getOrAwaitValue()
        assertEquals(1, allRecipes.size)

        dao.delete(allRecipes[0])

        allRecipes = dao.getAllRecipes().getOrAwaitValue()
        assertTrue(allRecipes.isEmpty())
    }
}
