package nl.xyz.assignment.recipes.repository;

import nl.xyz.assignment.recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeServiceRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByRecipeName(String recipeName);
}
