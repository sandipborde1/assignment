package nl.xyz.assignment.recipes.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nl.xyz.assignment.recipes.exception.RecipeAlreadyExistsException;
import nl.xyz.assignment.recipes.exception.RecipeNotFoundException;
import nl.xyz.assignment.recipes.model.RecipeVO;
import nl.xyz.assignment.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @ApiOperation(value = "Create Recipe", notes = "Returns the success or failure http status")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Recipe created successfully"),
            @ApiResponse(code = 409, message = "The Recipe already exists")
    })
    @PostMapping(value = "/create-recipe")
    public ResponseEntity<?> createRecipe(
            @RequestBody RecipeVO recipeVO) throws RecipeAlreadyExistsException {
        recipeService.createRecipe(recipeVO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Recipe Created Successfully");

    }

    @ApiOperation(value = "Update Recipe", notes = "Returns the success or failure http status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Recipe updated successfully"),
            @ApiResponse(code = 404, message = "The Recipe not found")
    })
    @PutMapping(value = "/update-recipe")
    public ResponseEntity<?> updateRecipe(@RequestBody RecipeVO recipeVO) throws RecipeNotFoundException {
        recipeService.updateRecipe(recipeVO);
        return ResponseEntity.ok().body("Recipe Updated Successfully");
    }

    @ApiOperation(value = "Delete Recipe", notes = "Returns the success or failure http status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Recipe deleted successfully"),
            @ApiResponse(code = 404, message = "The Recipe not found")
    })
    @DeleteMapping(value = "/delete-recipe/{recipeName}")
    public ResponseEntity<?> deleteRecipe(@PathVariable String recipeName) {
        recipeService.deleteRecipeByRecipeName(recipeName);
        return ResponseEntity.ok().body("Recipe Deleted Successfully");
    }

    @ApiOperation(value = "Get all Recipes", notes = "Returns all recipes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @GetMapping(value = "/get-all-recipes")
    public ResponseEntity<?> getRecipes() {
        Set<RecipeVO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }
}
