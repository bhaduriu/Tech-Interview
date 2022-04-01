import java.util.*;
import java.math.*;

class MovingAverage {  
  private final LinkedList<Long> list = new LinkedList<Long>();
  private final int N;

  /* 9999999998 (highest number possible) when added for a period of 999998 times, yields a very Big number, outside the bounds of regular 64 bit varibles */
  private BigDecimal sum = BigDecimal.ZERO; 
   
  public MovingAverage(Integer N) {
    this.N = N;
  }
  
  private Double addUtil(){
    sum = sum.add(BigDecimal.valueOf(X));
    if (list.size() > N){
      sum = sum.subtract(new BigDecimal(list.remove()));
    }
    return average().doubleValue();
  }
  
  public Double add(Integer X){
    list.add(new Long(X));
    return addUtil();
  }

  // function overloaded to handle Long values, where X is greater than 2147483647
  public Double add(Long X){
    list.add(X);
    return addUtil();
  }
    
  private BigDecimal average(){
    if (list.isEmpty())
      return BigDecimal.valueOf(0.0);
    return sum.divide(BigDecimal.valueOf(list.size()), 16, RoundingMode.HALF_UP).stripTrailingZeros();
  }
}
