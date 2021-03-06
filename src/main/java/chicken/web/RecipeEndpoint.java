package chicken.web;

import chicken.recipes.GetRecipeRequest;
import chicken.recipes.GetRecipeResponse;
import chicken.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.addressing.server.annotation.Action;

@Endpoint
public class RecipeEndpoint {
    private RecipeService recipeservice;

    @Autowired
    public RecipeEndpoint(RecipeService recipeservice) {
        this.recipeservice = recipeservice;
    }

    @PayloadRoot(namespace = "urn:chicken:recipes", localPart = "getRecipeRequest")
    @ResponsePayload
    public GetRecipeResponse getRecipe(@RequestPayload GetRecipeRequest request) {
        GetRecipeResponse response = new GetRecipeResponse();
        response.setRecipe(recipeservice.findRecipeByCountry(request.getCountry()).getRecipe());
        return response;
    }

    @Action(value = "urn:chicken:recipes:GetRandomRecipeRequest")
    @ResponsePayload
    public GetRecipeResponse getRandomRecipe() {
        GetRecipeResponse response = new GetRecipeResponse();
        response.setRecipe(recipeservice.randomRecipe().getRecipe());
        return response;
    }
}
