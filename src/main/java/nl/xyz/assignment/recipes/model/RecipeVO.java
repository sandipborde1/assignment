package nl.xyz.assignment.recipes.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
public class RecipeVO implements Serializable {
    private String recipeName;
    @ApiModelProperty(example = "dd-MM-yyyy HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime creationDate;
    private Boolean isVegDish;
    private Integer maxPeopleServed;
    private Set<String> ingredients;
    private String cookingInstructions;
}
