package chicken.osgi;

import chicken.domain.Recipe;
import chicken.service.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    public void testHelloWorldRecordService() throws Exception {
        Recipe testRecord = new Recipe("testName", "test message");
        recipeService.add(testRecord);

        Recipe record = recipeService.findRecipeByCountry(testRecord.getCountry());
        assertEquals(testRecord, record);

        List<Recipe> records = recipeService.getRecords();
        assertTrue(records.contains(testRecord));

        recipeService.delete(testRecord);
        record = recipeService.findRecipeByCountry(testRecord.getCountry());
        assertNull(record);
    }
}
