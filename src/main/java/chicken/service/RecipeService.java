package chicken.service;

import java.util.List;

import chicken.domain.Recipe;

/**
 * Service interface for CRUD on simple repository records.
 */
public interface RecipeService {

    void create(String country, String recipe);

    void add(Recipe record);

    Recipe findRecipeByCountry(String country);

    List<Recipe> getRecords();

    void delete(Recipe record);

    void update(Recipe record);
}
