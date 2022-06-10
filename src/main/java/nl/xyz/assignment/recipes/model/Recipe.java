package nl.xyz.assignment.recipes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Recipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPE_ID")
    Long id;

    @Column(name = "RECIPE_NAME")
    String recipeName;

    @Column(name = "CREATION_DT")
    private LocalDateTime creationDate;

    @Column(name = "VEG_INDICATOR")
    private Boolean isVegDish;

    @Column(name = "MAX_PEOPLE_SERVED")
    private Integer maxPeopleServed;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ingredient> ingredients;

    @Column(name = "COOKING_INSTRUCTIONS")
    private String cookingInstructions;
}
