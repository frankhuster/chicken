package chicken.service.impl;

import chicken.domain.Recipe;
import chicken.service.RecipeService;
import chicken.repository.RecipeDataService;

import org.motechproject.event.listener.EventRelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link chicken.service.RecipeService} interface. Uses
 * {@link chicken.repository.RecipeDataService} in order to retrieve and persist records.
 */
@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {

    private RecipeDataService recipeDataService;

    @Autowired
    public RecipeServiceImpl(RecipeDataService recipeDataService) {
        this.recipeDataService = recipeDataService;

        List<Recipe> existingRecipes = recipeDataService.retrieveAll();
        if (existingRecipes.size() == 0) {
            recipeDataService.create(new Recipe("England", "Boiled Hen"));
            recipeDataService.create(new Recipe("France", "Coq au vin"));
            recipeDataService.create(new Recipe("Thailand", "Chicken Pad Thai"));
            recipeDataService.create(new Recipe("USA", "Fried Chicken"));
        }
    }

    @Override
    public void create(String country, String recipe) {
        recipeDataService.create(
                new Recipe(country, recipe)
        );
    }

    @Override
    public void add(Recipe record) {
        recipeDataService.create(record);
    }

    @Override
    public Recipe findRecipeByCountry(String country) {
        Recipe record = recipeDataService.findRecipeByCountry(country);
        if (null == record) {
            return null;
        }
        return record;
    }

    @Override
    public List<Recipe> getRecords() {
        return recipeDataService.retrieveAll();
    }

    @Override
    public void update(Recipe record) {
        recipeDataService.update(record);
    }

    @Override
    public void delete(Recipe record) {
        recipeDataService.delete(record);
    }
}
