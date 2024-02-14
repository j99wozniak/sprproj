package jwoz.sprproj;

import jwoz.sprproj.db.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorTest {

    @Test
    public void testSimpleSingle() {
        boolean result = Validator.ValidateIfSingleWord("SingleWord");
        Assertions.assertTrue(result);
    }

    @Test
    public void testComplexSingle() {
        boolean result = Validator.ValidateIfSingleWord("th1sIs;Sti|_|_5i^/6LeW<>rd");
        Assertions.assertTrue(result);
    }

    @Test
    public void testSimpleMulti() {
        boolean result = Validator.ValidateIfSingleWord("Multiple words");
        Assertions.assertFalse(result);
    }

    @Test
    public void testComplexMulti() {
        boolean result = Validator.ValidateIfSingleWord("""
                 
                 IsthisMultiplewords?""");
        Assertions.assertFalse(result);
        result = Validator.ValidateIfSingleWord("""
                IsthisMultiplewords?
                """);
        Assertions.assertFalse(result);
        result = Validator.ValidateIfSingleWord("""
                IsthisMultiple words?""");
        Assertions.assertFalse(result);
        result = Validator.ValidateIfSingleWord("IsthisMultiple words?");
        Assertions.assertFalse(result);
    }

}
