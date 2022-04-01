import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ExampleTest {
  
    @Test
    public void instructionsExampleTest() {
        MovingAverage movingAverage = new MovingAverage(3);
        assertEquals(Double.valueOf(3.0) , movingAverage.add(3));
        assertEquals(Double.valueOf(3.5) , movingAverage.add(4));
        assertEquals(Double.valueOf(4.0) , movingAverage.add(5));
        assertEquals(Double.valueOf(4.333333333333333) , movingAverage.add(4));
    }

    @Test
    public void instructionsExampleTest2() {
        MovingAverage movingAverage = new MovingAverage(2);
        assertEquals(Double.valueOf(3.0) , movingAverage.add(3));
        assertEquals(Double.valueOf(3.5) , movingAverage.add(4));
        assertEquals(Double.valueOf(4.5) , movingAverage.add(5));
        assertEquals(Double.valueOf(5.5) , movingAverage.add(6));
    }
  
  @Test
    public void instructionsExampleTest3() {
        MovingAverage movingAverage = new MovingAverage(1);
        assertEquals(Double.valueOf(3.0) , movingAverage.add(3));
        assertEquals(Double.valueOf(4.0) , movingAverage.add(4));
        assertEquals(Double.valueOf(5.0) , movingAverage.add(5));
        assertEquals(Double.valueOf(6.0) , movingAverage.add(6));
    }
  
   @Test
    public void instructionsExampleTest4() {
        MovingAverage movingAverage = new MovingAverage(5);
        assertEquals(Double.valueOf(1.0) , movingAverage.add(1));
        assertEquals(Double.valueOf(1.5) , movingAverage.add(2));
        assertEquals(Double.valueOf(2.0) , movingAverage.add(3));
        assertEquals(Double.valueOf(2.5) , movingAverage.add(4));
        assertEquals(Double.valueOf(3.0) , movingAverage.add(5));
        assertEquals(Double.valueOf(4.0) , movingAverage.add(6));
        assertEquals(Double.valueOf(5.0) , movingAverage.add(7));
        assertEquals(Double.valueOf(6.0) , movingAverage.add(8));
        assertEquals(Double.valueOf(7.0) , movingAverage.add(9));
        assertEquals(Double.valueOf(8.0) , movingAverage.add(10));
    }
  
   @Test // To handle larger numbers
    public void instructionsExampleTest5() {
        MovingAverage movingAverage = new MovingAverage(3);
        assertEquals(Double.valueOf(1.0) , movingAverage.add(1));
        assertEquals(Double.valueOf(1.5) , movingAverage.add(2));
        assertEquals(Double.valueOf(2.0) , movingAverage.add(3));
        assertEquals(Double.valueOf(3333333334.333333333333333) , movingAverage.add(9999999998L));
    }

     @Test // To handle larger numbers
    public void instructionsExampleTest6() {
        MovingAverage movingAverage = new MovingAverage(2);
        assertEquals(Double.valueOf(9.999999998E9) , movingAverage.add(9999999998L));
        assertEquals(Double.valueOf(9.9999999975E9) , movingAverage.add(9999999997L));
        assertEquals(Double.valueOf(9.9999999965E9) , movingAverage.add(9999999996L));
        assertEquals(Double.valueOf(9.9999999955E9) , movingAverage.add(9999999995L));
        assertEquals(Double.valueOf(9.9999999945E9) , movingAverage.add(9999999994L));
    }
}
