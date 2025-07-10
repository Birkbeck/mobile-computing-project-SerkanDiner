# 🍲 Foodie — Recipe Manager Android App

**Coursework 2 – Mobile Computing – Birkbeck**

---

## 📱 About

**Foodie** is an Android application that allows users to:
- Create recipe entries with:
    - Title
    - List of ingredients
    - Cooking instructions
    - Category:  
      ➡️ Breakfast, Brunch, Lunch, Dinner, Desserts, or Other
- View, edit, and delete recipe entries.
- Store recipes locally using a Room database.
- Navigate between:
    - List view
    - Recipe detail view
    - Form for adding/editing recipes

---

## 🎨 Features

✅ Add new recipes  
✅ Edit existing recipes  
✅ Delete recipes  
✅ List all recipes in a clean, user-friendly UI  
✅ Save and load data from local Room database  
✅ Unit-tested and instrumented-tested code

---

## 🧪 Tests

This project includes:
- 📄 **Instrumented tests** (run on emulator/device) for:
    - `RecipeDaoTest`
    - `RecipeViewModelTest`  
      (both located in: `src/androidTest/java/com/sdiner01/foodie/`)

### 🧹 How to run tests

✅ Run **instrumented tests** (on emulator/device):
```bash
./gradlew connectedAndroidTest
