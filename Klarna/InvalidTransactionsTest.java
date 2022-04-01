import java.util.Collection;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class SampleTest {

    @Test
    public void sampleTest1() {
        String[] input = {
                "john,20,800,stockholm",
                "john,50,100,beijing"
        };
        String[] expectedOutput = {
                "john,20,800,stockholm",
                "john,50,100,beijing"
        };

        assertListsEqual(expectedOutput, Underwriter.identifyInvalidTransactions(input));
    }

    @Test
    public void sampleTest2() {
        String[] input = {
                "john,20,200,stockholm",
                "john,50,200,stockholm"
        };
        String[] expectedOutput = {
                "john,20,200,stockholm",
                "john,50,200,stockholm"
        };

        assertListsEqual(expectedOutput, Underwriter.identifyInvalidTransactions(input));

    }


    @Test
    public void sampleTest3() {
        String[] input = {
                "john,20,150,stockholm",
                "john,20,300,stockholm"
        };
        String[] expectedOutput = {
                "john,20,150,stockholm",
                "john,20,300,stockholm"
        };

        assertListsEqual(expectedOutput, Underwriter.identifyInvalidTransactions(input));
    }

    @Test
    public void sampleTest4() {
        String[] input = {
                "john,20,150,stockholm",
                "john,30,300,stockholm"
        };
        String[] expectedOutput = {};

        assertListsEqual(expectedOutput, Underwriter.identifyInvalidTransactions(input));
    }

    @Test
    public void sampleTest5() {
        String[] input = {
        };
        String[] expectedOutput = {};

        assertListsEqual(expectedOutput, Underwriter.identifyInvalidTransactions(input));
    }

    private void assertListsEqual(String[] l1, String[] l2) {
        Collection<String> c1 = List.of(l2);
        Collection<String> c2 = List.of(l1);
        assertTrue(c1.containsAll(c2) && c2.containsAll(c1));
    }

}
