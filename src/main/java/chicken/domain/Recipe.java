package chicken.domain;

import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;

import java.util.Objects;

/**
 * Chicken recipes, per country
 */
@Entity
public class Recipe {

    @Field
    private String country;

    @Field
    private String recipe;

    public Recipe() {
    }


    public Recipe(String country, String recipe) {
        this.country = country;
        this.recipe = recipe;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, recipe);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Recipe other = (Recipe) obj;

        return Objects.equals(this.country, other.country) && Objects.equals(this.recipe, other.recipe);
    }

    @Override
    public String toString() {
        return String.format("Recipe{country='%s', recipe='%s'}", country, recipe);
    }
}