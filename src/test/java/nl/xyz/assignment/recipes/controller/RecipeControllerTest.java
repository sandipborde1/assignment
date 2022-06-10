package nl.xyz.assignment.recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.xyz.assignment.recipes.exception.RecipeNotFoundException;
import nl.xyz.assignment.recipes.model.RecipeVO;
import nl.xyz.assignment.recipes.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@WebMvcTest(controllers = RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    private RecipeVO recipeVO;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mapper.findAndRegisterModules();
        recipeVO = mockRecipe();
    }

    @Test
    public void whenProvidedRecipe_thenRecipeShouldBeCreated() throws Exception {
        Mockito.when(recipeService.createRecipe(recipeVO)).thenReturn(recipeVO);
        String request = mapper.writeValueAsString(recipeVO);
        this.mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/v1/create-recipe")
                .content(request).contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void whenProvidedRecipe_thenRecipeShouldBeUpdated() throws Exception {
        Mockito.when(recipeService.updateRecipe(recipeVO)).thenReturn(recipeVO);
        String request = mapper.writeValueAsString(recipeVO);
        this.mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "/api/v1/update-recipe")
                .content(request).contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenProvidedRecipeName_thenRecipeShouldBeDeleted() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, "/api/v1/delete-recipe/Paneer Masala")
                .contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(recipeService, Mockito.times(1)).deleteRecipeByRecipeName(recipeVO.getRecipeName());
    }

    @Test
    public void whenRequested_thenAllRecipesShouldFetched() throws Exception {
        Mockito.when(recipeService.getAllRecipes()).thenReturn(Sets.newSet(recipeVO));
        String request = mapper.writeValueAsString(recipeVO);
        this.mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/v1/get-all-recipes")
                .contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("[" + request + "]"));
    }

    @Test
    public void whenProvidedRecipeNotExist_thenShouldReturnNotFound() throws Exception {
        Mockito.when(recipeService.updateRecipe(Mockito.any(RecipeVO.class))).thenThrow(new RecipeNotFoundException());
        String request = mapper.writeValueAsString(recipeVO);
        this.mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "/api/v1/update-recipe")
                .content(request).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private RecipeVO mockRecipe() {
        RecipeVO recipeVO = new RecipeVO();
        recipeVO.setRecipeName("Paneer Masala");
        recipeVO.setCookingInstructions("Less spice");
        recipeVO.setCreationDate(LocalDateTime.now());
        recipeVO.setIsVegDish(true);
        recipeVO.setMaxPeopleServed(3);
        recipeVO.setIngredients(Sets.newSet("Red Chilli", "Salt"));
        return recipeVO;
    }
}
