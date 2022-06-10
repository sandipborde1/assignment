package nl.xyz.assignment.recipes.utils;

import nl.xyz.assignment.recipes.model.Ingredient;
import nl.xyz.assignment.recipes.model.Recipe;
import nl.xyz.assignment.recipes.model.RecipeVO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeUtils {


    public Recipe mapRecipeToEntity(RecipeVO recipeVO) {
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeVO.getRecipeName());
        recipe.setCreationDate(recipeVO.getCreationDate());
        recipe.setCookingInstructions(recipeVO.getCookingInstructions());
        recipe.setIsVegDish(recipeVO.getIsVegDish());
        recipe.setMaxPeopleServed(recipeVO.getMaxPeopleServed());
        recipe.setIngredients(this.mapIngredientToEntity(recipeVO.getIngredients(), recipe));
        return recipe;
    }

    public Set<Ingredient> mapIngredientToEntity(Set<String> ingredients, Recipe recipe) {

        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredients.forEach(ingredient -> {
            Ingredient ingredientDB = new Ingredient();
            ingredientDB.setIngredient(ingredient);
            ingredientDB.setRecipe(recipe);
            ingredientSet.add(ingredientDB);
        });
        return ingredientSet;
    }

    public RecipeVO mapEntityToRecipe(Recipe recipe) {
        RecipeVO recipeVO = new RecipeVO();
        recipeVO.setRecipeName(recipe.getRecipeName());
        recipeVO.setCreationDate(recipe.getCreationDate());
        recipeVO.setCookingInstructions(recipe.getCookingInstructions());
        recipeVO.setIsVegDish(recipe.getIsVegDish());
        recipeVO.setMaxPeopleServed(recipe.getMaxPeopleServed());
        recipeVO.setIngredients(mapEntityToIngredient(recipe.getIngredients()));
        return recipeVO;
    }

    public Set<String> mapEntityToIngredient(Set<Ingredient> ingredients) {
        if (!CollectionUtils.isEmpty(ingredients)) {
            Set<String> ingredientSet = new HashSet<>();
            ingredients.stream().forEach(e -> ingredientSet.add(e.getIngredient()));
            return ingredientSet;
        }
        return null;
    }
}
