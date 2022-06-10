package nl.xyz.assignment.recipes.service;

import nl.xyz.assignment.recipes.exception.RecipeAlreadyExistsException;
import nl.xyz.assignment.recipes.model.Ingredient;
import nl.xyz.assignment.recipes.model.Recipe;
import nl.xyz.assignment.recipes.model.RecipeVO;
import nl.xyz.assignment.recipes.repository.RecipeServiceRepository;
import nl.xyz.assignment.recipes.utils.RecipeUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class RecipeServiceImplTest {

    @Mock
    private RecipeServiceRepository serviceRepository;

    @Mock
    private RecipeUtils recipeUtils;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    public void whenProvidedRecipe_thenRecipeShouldBeCreated() {
        Recipe recipeDb = mockRecipe();
        RecipeVO recipeReq = mockRecipeVO();
        Mockito.when(serviceRepository.findByRecipeName(recipeReq.getRecipeName())).thenReturn(Optional.empty());
        Mockito.when(recipeUtils.mapRecipeToEntity(recipeReq)).thenReturn(recipeDb);
        Mockito.when(serviceRepository.save(recipeDb)).thenReturn(recipeDb);
        Mockito.when(recipeUtils.mapEntityToRecipe(recipeDb)).thenReturn(recipeReq);
        RecipeVO recipeVO = recipeService.createRecipe(recipeReq);
        assertThat(recipeVO.getRecipeName()).isEqualTo(recipeReq.getRecipeName());

    }

    @Test
    public void whenProvidedRecipe_thenRecipeShouldBeUpdated() {
        Recipe recipeDb = mockRecipe();
        RecipeVO recipeReq = mockRecipeVO();
        recipeReq.setMaxPeopleServed(2);
        Mockito.when(serviceRepository.findByRecipeName(recipeReq.getRecipeName())).thenReturn(Optional.of(recipeDb));
        Mockito.when(recipeUtils.mapRecipeToEntity(recipeReq)).thenReturn(recipeDb);
        Mockito.when(serviceRepository.save(recipeDb)).thenReturn(recipeDb);
        Mockito.when(recipeUtils.mapEntityToRecipe(recipeDb)).thenReturn(recipeReq);
        RecipeVO recipeVO = recipeService.updateRecipe(recipeReq);
        assertThat(recipeVO.getRecipeName()).isEqualTo(recipeReq.getRecipeName());
        assertThat(recipeVO.getMaxPeopleServed()).isNotEqualTo(recipeDb.getMaxPeopleServed());
    }

    @Test
    public void whenRequested_thenAllRecipesShouldBeFetched() {
        Mockito.when(serviceRepository.findAll()).thenReturn(new ArrayList<>());
        Set<RecipeVO> recipeVOs = recipeService.getAllRecipes();
        assertThat(recipeVOs.size()).isEqualTo(0);
    }

    @Test
    public void whenRequested_thenRecipesShouldBeDeleted() {
        Recipe recipeDb = mockRecipe();
        recipeDb.setId(1L);
        RecipeVO recipeReq = mockRecipeVO();
        Mockito.when(serviceRepository.findByRecipeName(recipeReq.getRecipeName())).thenReturn(Optional.of(recipeDb));
        recipeService.deleteRecipeByRecipeName(recipeReq.getRecipeName());
        Mockito.verify(serviceRepository, Mockito.times(1)).deleteById(1L);
    }


    @Test
    public void whenProvidedRecipe_thenRecipeShouldBeCreated_Exception_Already_Exists() {
        Recipe recipeDb = mockRecipe();
        RecipeVO recipeReq = mockRecipeVO();
        Mockito.when(serviceRepository.findByRecipeName(recipeReq.getRecipeName())).thenReturn(Optional.of(recipeDb));
        Mockito.when(recipeUtils.mapRecipeToEntity(recipeReq)).thenReturn(recipeDb);
        assertThrows(RecipeAlreadyExistsException.class,
                () -> recipeService.createRecipe(recipeReq));
    }

    private RecipeVO mockRecipeVO() {
        RecipeVO recipeVO = new RecipeVO();
        recipeVO.setRecipeName("Paneer Masala");
        recipeVO.setCookingInstructions("Less spice");
        recipeVO.setCreationDate(LocalDateTime.now());
        recipeVO.setIsVegDish(true);
        recipeVO.setMaxPeopleServed(3);
        recipeVO.setIngredients(Sets.newSet("Red Chilli", "Salt"));
        return recipeVO;
    }


    private Recipe mockRecipe() {
        Recipe recipe = new Recipe();
        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        ingredient1.setIngredient("Red Chilli");
        ingredient1.setIngredient("Salt");
        recipe.setRecipeName("Paneer Masala");
        recipe.setCookingInstructions("Less spice");
        recipe.setCreationDate(LocalDateTime.now());
        recipe.setIsVegDish(true);
        recipe.setMaxPeopleServed(3);
        ingredient1.setRecipe(recipe);
        ingredient2.setRecipe(recipe);
        recipe.setIngredients(Sets.newSet(ingredient1, ingredient2));
        return recipe;
    }
}
