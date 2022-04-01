import java.util.*;
import java.lang.*;

class Challenge {

  public static List<Integer> whoIsGoingHomeEarly(int n, int k) {
    final Vector<Integer> circle = new Vector<Integer>();
    final List<Integer> homeSick = new ArrayList<>();
    final int homeGoers = (int)Math.floor(n/2.0); // number of people going home
    final int workers = (int)Math.ceil(n/2.0); // number of people staying
    int pos = 0;
    
    if (n==0)
      return homeSick;

    for (int i=1; i<=n; i++) {
      circle.add(i);
    }
    
    if (k==0)
      return Collections.list(circle.elements());
    
    /* Analysis:
    Assuming, N!=0 and K!=0, for any values of N,K. There would be at least one person going home at each round. And at each round, the vector size shrinks by 1. This continues until the vector size is almost half of the initial number of 'N' workers. 
    Hence, Time Complexity: O(N) (asymptotically) where N = number of workers
    */
    while(circle.size() != workers) {
      pos = (pos+k)%circle.size();
      homeSick.add(circle.get(pos));
      circle.remove(pos);
      pos %= circle.size();
    }
    
    return homeSick;
  }
}
