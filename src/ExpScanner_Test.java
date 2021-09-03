import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpScanner_Test {

    ExpScanner expScanner = new ExpScanner();

    @Test
    public void testCheckOperator()
    {
        char[] testOp = {'(',')','|','+','*'};
        for (char op: testOp) {
            assertTrue(expScanner.checkOperator(op));
        }

        assertFalse(expScanner.checkOperator('-'));
        assertFalse(expScanner.checkOperator('&'));
    }

    @Test
    public void testCheckAlphabet()
    {
        assertTrue(expScanner.checkAlphabet('a'));
        assertTrue(expScanner.checkAlphabet('Z'));
        assertTrue(expScanner.checkAlphabet(' '));

        assertFalse(expScanner.checkAlphabet('&'));
        assertFalse(expScanner.checkAlphabet('-'));
    }

    @Test
    public void testCheckInt()
    {
        assertFalse(expScanner.checkInt('a'));
        assertFalse(expScanner.checkInt('-'));

        assertTrue(expScanner.checkInt('0'));
        assertTrue(expScanner.checkInt('9'));
    }

    @Test
    public void testMatchRule()
    {
        assertTrue(expScanner.matchRule("abc123"));
        assertTrue(expScanner.matchRule("a|B"));
        assertTrue(expScanner.matchRule("a 12|B"));
        assertTrue(expScanner.matchRule("a(e|D)+|2*"));

        assertFalse(expScanner.matchRule("---"));
        assertFalse(expScanner.matchRule("--d"));
        assertFalse(expScanner.matchRule("d-d"));
    }

}
