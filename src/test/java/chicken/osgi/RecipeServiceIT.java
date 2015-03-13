package chicken.osgi;

import chicken.domain.Recipe;
import chicken.service.RecipeService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.motechproject.testing.utils.TestContext;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Verify that RecipeService is present & functional.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class RecipeServiceIT extends BasePaxIT {

    @Inject
    private RecipeService recipeService;

    @Test
    public void verifyServiceFunctional() {

        Recipe recipe = recipeService.findRecipeByCountry("nowhere");
        assertNull(recipe);
    }

    @Test
    public void testHelloWorldRecordService() throws Exception {
        Recipe recipe = new Recipe("country", "recipe");
        recipeService.add(recipe);

        Recipe record = recipeService.findRecipeByCountry(recipe.getCountry());
        assertEquals(recipe, record);

        List<Recipe> records = recipeService.getRecords();
        assertTrue(records.contains(recipe));

        recipeService.delete(recipe);
        record = recipeService.findRecipeByCountry(recipe.getCountry());
        assertNull(record);
    }

    @Test
    public void verifyPayloadRootEndpoint() throws IOException, InterruptedException {
        login();

        // TODO: Use a real SOAP client, not HTTP client
        // TODO: Some headers are not understood by the server
        HttpPost post;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("recipe-request.xml")) {
            String xml = IOUtils.toString(in);
            post = new HttpPost(String.format("http://localhost:%d/chicken/", TestContext.getJettyPort()));
            post.setEntity(new StringEntity(xml));
        }

        post.setHeader(HttpHeaders.CONTENT_TYPE, "application/soap+xml");

        // TODO: Do something more with the response
        String response = getHttpClient().execute(post, new BasicResponseHandler());
        assertNotNull(response);
        assertTrue(response.contains("env:Envelope"));
    }

    @Test
    public void verifyActionEndpoint() throws IOException, InterruptedException {
        login();

        // TODO: Use a real SOAP client, not HTTP client
        // TODO: Some headers are not understood by the server
        HttpPost post;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("random-recipe-request.xml")) {
            String xml = IOUtils.toString(in);
            post = new HttpPost(String.format("http://localhost:%d/chicken/", TestContext.getJettyPort()));
            post.setEntity(new StringEntity(xml));
        }

        post.setHeader(HttpHeaders.CONTENT_TYPE, "application/soap+xml");

        // TODO: Do something more with the response
        String response = getHttpClient().execute(post, new BasicResponseHandler());
        assertNotNull(response);
        assertTrue(response.contains("env:Envelope"));
    }

}
