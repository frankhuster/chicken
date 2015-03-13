package chicken.service.impl;

import chicken.domain.Recipe;
import chicken.service.RecipeService;
import chicken.repository.RecipeDataService;

import org.motechproject.event.listener.EventRelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Implementation of the {@link chicken.service.RecipeService} interface. Uses
 * {@link chicken.repository.RecipeDataService} in order to retrieve and persist records.
 */
@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {

    private RecipeDataService recipeDataService;
    private Random randomGenerator;

    @Autowired
    public RecipeServiceImpl(RecipeDataService recipeDataService) {
        this.recipeDataService = recipeDataService;
        randomGenerator = new Random();

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
    public Recipe randomRecipe() {
        // This is obviously very inefficient, nobody in their right might would load an entire table in memory
        // to return a random record, but this is not the point of our chicken module!
        List<Recipe> recipes = recipeDataService.retrieveAll();
        int index = randomGenerator.nextInt(recipes.size());
        return recipes.get(index);
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
