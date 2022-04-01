import java.util.*;

class Underwriter {
  private final String name, city;
  private final int time, amount;
  
  /* Singleton Design with Private Constructor => Nobody outside this class will create objects, since we already have a static method to get the final result without instantiating objects */
  private Underwriter(String name, int time, int amount, String city)
  {
    this.name = name;
    this.time = time;
    this.amount = amount;
    this.city = city;
  }
   
  public static String[] identifyInvalidTransactions(String[] transactions) {
    final List<String> result = new ArrayList<>();
    final Underwriter[] allTransactions = new Underwriter[transactions.length];
    
    if (transactions.length == 0)
      return result.toArray(new String[0]);

    for (int i = 0; i < transactions.length; i++) {
      String[] items = transactions[i].split(",");
      allTransactions[i] = new Underwriter(items[0], Integer.parseInt(items[1]), Integer.parseInt(items[2]), items[3]);
    }
    
    /* Analysis:
    Assuming, N = total number of transactions.
    When all the transactions have the same names with amount < $2000 (which is majority of the tests in this question), then the time taken is O(N^2) ==> Worst Case (Need to iterate through all transactions)
    However, this could be reduced if I use a map data structure to store those transactions with the same name. For Eg: Map<String, String[]> Key: Name, Value: Transactions
    Using map, will reduce the time complexity to O(N) only when the transactions have unique names (this seems to be rarity with the tests in this question) => Best Case 
    However using this approach I would need an extra space of O(N) for using the Map. So I believe this little optimization doesn't bring us any benefit, because the Worst case time complexity would still be O(N^2). 
    Hence, I decided to let it be O(N^2) for both Best and Worst cases without using an extra space.
    */

    for (int i = 0; i < transactions.length; i++) {

      // amount exceeds $2000
      if (allTransactions[i].amount > 2000)
        result.add(transactions[i]);
      
      for (int j = i + 1; j < transactions.length; j++) {

        // all transactions with same name block
        if (allTransactions[i].name.equals(allTransactions[j].name)) {
          
          // all transactions within 60 mins block
          if (Math.abs(allTransactions[i].time - allTransactions[j].time) <= 60)
          {
            // same exact price
            if (allTransactions[i].amount == allTransactions[j].amount) {
              System.out.println("hier");
              result.add(transactions[i]);
              result.add(transactions[j]);
              break;
            }
            
            // different city
            if (!allTransactions[i].city.equals(allTransactions[j].city)) {
              result.add(transactions[i]);
              result.add(transactions[j]);
              break;
            }
          } // close 60 mins block
          
          // same exact time
          if (allTransactions[i].time == allTransactions[j].time) {
            result.add(transactions[i]);
            result.add(transactions[j]);
            break;
          }
        
        } // close same name block
      } // j
    } // i
    
    return result.toArray(new String[result.size()]);
  }
}
