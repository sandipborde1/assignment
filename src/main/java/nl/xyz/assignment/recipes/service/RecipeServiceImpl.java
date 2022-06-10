package nl.xyz.assignment.recipes.service;

import nl.xyz.assignment.recipes.exception.RecipeAlreadyExistsException;
import nl.xyz.assignment.recipes.exception.RecipeNotFoundException;
import nl.xyz.assignment.recipes.model.Recipe;
import nl.xyz.assignment.recipes.model.RecipeVO;
import nl.xyz.assignment.recipes.repository.RecipeServiceRepository;
import nl.xyz.assignment.recipes.utils.RecipeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeServiceRepository recipeServiceRepository;


    @Autowired
    private RecipeUtils recipeUtils;

    @Override
    public RecipeVO createRecipe(RecipeVO recipeVO) throws RecipeAlreadyExistsException {
        Optional<Recipe> recipeDbOptional = recipeServiceRepository.findByRecipeName(recipeVO.getRecipeName());
        if (recipeDbOptional.isPresent()) {
            throw new RecipeAlreadyExistsException();
        } else {
            Recipe recipe = recipeServiceRepository.save(recipeUtils.mapRecipeToEntity(recipeVO));
            return recipeUtils.mapEntityToRecipe(recipe);
        }
    }

    @Override
    public RecipeVO updateRecipe(RecipeVO recipeVO) throws RecipeNotFoundException {
        Optional<Recipe> recipeDbOptional = recipeServiceRepository.findByRecipeName(recipeVO.getRecipeName());
        if (recipeDbOptional.isPresent()) {
            Recipe recipe = recipeUtils.mapRecipeToEntity(recipeVO);
            recipe.setId(recipeDbOptional.get().getId());
            recipe = recipeServiceRepository.save(recipe);
            return recipeUtils.mapEntityToRecipe(recipe);
        } else {
            throw new RecipeNotFoundException();
        }
    }

    @Override
    public Set<RecipeVO> getAllRecipes() {
        return recipeServiceRepository.findAll().stream().map(recipe ->
                recipeUtils.mapEntityToRecipe(recipe)).collect(Collectors.toSet());
    }

    @Override
    public void deleteRecipeByRecipeName(String getRecipeName) throws RecipeNotFoundException {
        Optional<Recipe> recipeDbOptional = recipeServiceRepository.findByRecipeName(getRecipeName);
        if (recipeDbOptional.isPresent()) {
            recipeServiceRepository.deleteById(recipeDbOptional.get().getId());
        } else {
            throw new RecipeNotFoundException();
        }
    }

}
