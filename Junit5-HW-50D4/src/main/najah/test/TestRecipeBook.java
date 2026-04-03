package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecipeBook {

    private RecipeBook recipeBook;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    void setup() {
        recipeBook = new RecipeBook();
        
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        
        recipe2 = new Recipe();
        recipe2.setName("Tea");
    }

    @Test
    @DisplayName("Test Get Recipes Array Initialization with Timeout")
    void testGetRecipes() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            Recipe[] recipes = recipeBook.getRecipes();
            assertAll(
                () -> assertNotNull(recipes),
                () -> assertEquals(4, recipes.length)
            );
        });
    }

    @Test
    @DisplayName("Test Add Recipe - Valid and Invalid Cases")
    void testAddRecipe() {
        assertAll(
            () -> assertTrue(recipeBook.addRecipe(recipe1)),
            () -> assertFalse(recipeBook.addRecipe(recipe1)), 
            () -> assertTrue(recipeBook.addRecipe(recipe2))
        );

        Recipe recipe3 = new Recipe(); recipe3.setName("Latte");
        Recipe recipe4 = new Recipe(); recipe4.setName("Mocha");
        Recipe recipe5 = new Recipe(); recipe5.setName("Espresso");

        recipeBook.addRecipe(recipe3);
        recipeBook.addRecipe(recipe4);
        
        assertFalse(recipeBook.addRecipe(recipe5)); 
    }

    @Test
    @DisplayName("Test Delete Recipe")
    void testDeleteRecipe() {
        recipeBook.addRecipe(recipe1);
        
        assertAll(
            () -> assertEquals("Coffee", recipeBook.deleteRecipe(0)),
            () -> assertNull(recipeBook.deleteRecipe(1)),
            () -> assertThrows(ArrayIndexOutOfBoundsException.class, () -> recipeBook.deleteRecipe(-1))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 4, 5})
    @DisplayName("Parameterized Test for Invalid Indexes in Edit Recipe")
    void testEditRecipeInvalidIndexes(int invalidIndex) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("NewCoffee");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> recipeBook.editRecipe(invalidIndex, newRecipe));
    }

    @Test
    @DisplayName("Test Edit Recipe - Valid Cases")
    void testEditRecipe() {
        recipeBook.addRecipe(recipe1);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Hot Chocolate");

        assertAll(
            () -> assertEquals("Coffee", recipeBook.editRecipe(0, newRecipe)),
            () -> assertNull(recipeBook.editRecipe(1, newRecipe)),
            () -> assertEquals("", recipeBook.getRecipes()[0].getName())
        );
    }
}