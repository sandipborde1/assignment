package nl.xyz.assignment.recipes.service;

import nl.xyz.assignment.recipes.exception.RecipeAlreadyExistsException;
import nl.xyz.assignment.recipes.exception.RecipeNotFoundException;
import nl.xyz.assignment.recipes.model.RecipeVO;

import java.util.Set;

public interface RecipeService {

    /**
     * This method should create new recipe
     *
     * @param recipeVO
     * @return
     * @throws RecipeAlreadyExistsException
     */

    RecipeVO createRecipe(RecipeVO recipeVO) throws RecipeAlreadyExistsException;

    /**
     * This method should fetch all recipes
     *
     * @return
     */
    Set<RecipeVO> getAllRecipes();

    /**
     * This method should update the existing recipe
     *
     * @param recipeVO
     * @return
     * @throws RecipeAlreadyExistsException
     */
    RecipeVO updateRecipe(RecipeVO recipeVO) throws RecipeNotFoundException;

    /**
     * This method should delete the recipe by recipe name
     *
     * @param recipeName
     */
    void deleteRecipeByRecipeName(String recipeName) throws RecipeNotFoundException;
}
