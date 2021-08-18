import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpScannerTest {

    ExpScanner expScanner = new ExpScanner();

    @Test
    public void testCheckOperator()
    {
        char[] testOp = {'(',')','|','+','*',' '};
        for (char op: testOp) {
            assertTrue(expScanner.checkOperator(op));
        }

        assertFalse(expScanner.checkOperator('&'));
    }

    @Test
    public void testCheckAlphabet()
    {
        assertTrue(expScanner.checkAlphabet('a'));
        assertTrue(expScanner.checkAlphabet('Z'));
    }
}
