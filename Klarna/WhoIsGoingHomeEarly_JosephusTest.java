import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class WhoIsGoingHomeEarlyTests {
    @Test
    public void example() {
        List<Integer> answer = Challenge.whoIsGoingHomeEarly(5, 3);
        List<Integer> expectedSolution = List.of(4, 3);

        assertTrue(answer.equals(expectedSolution));
    }
  
    @Test
    public void example2() {
        List<Integer> answer = Challenge.whoIsGoingHomeEarly(10, 7);
        List<Integer> expectedSolution = List.of(8, 6, 5, 7, 10);

        assertTrue(answer.equals(expectedSolution));
    }

    @Test // N=K
    public void example3() {
        List<Integer> answer = Challenge.whoIsGoingHomeEarly(7, 7);
        List<Integer> expectedSolution = List.of(1, 3, 6);

        assertTrue(answer.equals(expectedSolution));
    }

    @Test // N<K
    public void example4() {
        List<Integer> answer = Challenge.whoIsGoingHomeEarly(3, 5);
        List<Integer> expectedSolution = List.of(3);

        assertTrue(answer.equals(expectedSolution));
    }
    @Test // N>K
    public void example5() {
        List<Integer> answer = Challenge.whoIsGoingHomeEarly(7, 3);
        List<Integer> expectedSolution = List.of(4, 1, 6);

        assertTrue(answer.equals(expectedSolution));
    }
 
    @Test // N=K/2
    public void example6() {
        List<Integer> answer = Challenge.whoIsGoingHomeEarly(5, 10);
        List<Integer> expectedSolution = List.of(1, 4);

        assertTrue(answer.equals(expectedSolution));
    }

   @Test // N=0
    public void example7() {
        List<Integer> answer = Challenge.whoIsGoingHomeEarly(0, 10);
        List<Integer> expectedSolution = List.of();

        assertTrue(answer.equals(expectedSolution));
    }

    @Test // K=0
    public void example8() {
        List<Integer> answer = Challenge.whoIsGoingHomeEarly(6, 0);
        List<Integer> expectedSolution = List.of(1, 2, 3, 4, 5, 6);

        assertTrue(answer.equals(expectedSolution));
    }

}
