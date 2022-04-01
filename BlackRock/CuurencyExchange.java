// BlackRock: Question 1

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// interface because it could be exported as a library if some external guy wants to use our function
interface Currency
{
  // 12 elements in currency, constant will never change
  static final double[] currency = { 50, 20, 10, 5, 2, 1, 0.5, 0.2, 0.1, 0.05, 0.02, 0.01 };
  static final String[] currency_in_String = {"Fifty Pounds", "Twenty Pounds", "Ten Pounds", "Five Pounds", 
                                              "Two Pounds", "One Pound", "Fifty Pence", "Twenty Pence", "Ten Pence",
                                              "Five Pence", "Two Pence", "One Pence"};
  // TODO: might need to use something like C++ stl::unordered_map <Double, String>

  String calculate_Change_in_Currency();
}

class Change implements Currency
{
  double price, cash_given;
  int [] currency_in_pennies;
  int[] cash_counter;

  Change(double purchase_Price, double cash)
  {
    this.price = purchase_Price;
    this.cash_given = cash;

    currency_in_pennies = new int[12];
    cash_counter = new int[12];

    convert_Currency_Floating_To_Pennies();
  } 

  private int convert_Double_to_Int(double money)
  {
    Double new_Data = new Double(money * 100);
    return new_Data.intValue();
  }
  
  private void convert_Currency_Floating_To_Pennies()
  {
    for (int i = 0; i < 12; i++)
    {
      currency_in_pennies[i] = convert_Double_to_Int(currency[i]);
    }
  }

  private String convert_Money_to_String()
  {
    String str_out = "";
    for (int i = 0; i < 12; i++)
    {
      if (cash_counter[i] != 0)
      {
        for (int j = 1; j <= cash_counter[i]; j++)
        {
          str_out = str_out + currency_in_String[i] + ", ";
        }
      }
    }
    
    return str_out.substring(0, str_out.length() - 2);
  }

  @Override
  public String calculate_Change_in_Currency()
  {
    String str_out = "";
    
    double change = cash_given < price ? -1 : cash_given - price;
    
    if (change == -1)
       return "ERROR";
    
    change = convert_Double_to_Int(change);
    
    // calculations
    for (int i = 0; i < 12; i++)
    {
      if (change >= currency_in_pennies[i])
      {
        cash_counter[i] = (int) change/currency_in_pennies[i];
        change -= (cash_counter[i] * currency_in_pennies[i]);
      }
    }
    
    // to string
    return convert_Money_to_String();
  }
}

public class Main {
  /**
   * Iterate through each line of input.
   */
  public static void main(String[] args) throws IOException {
    InputStreamReader reader = new InputStreamReader(System.in);
    BufferedReader in = new BufferedReader(reader);

    try {
        double purchasePrice = Double.parseDouble(in.readLine());
        double cash = Double.parseDouble(in.readLine());
        Main.calculateChange(purchasePrice, cash);
    } catch (Exception e) {
        System.out.println(e);
    }
  }

  public static void calculateChange(double purchasePrice, double cash) {
    Change output = new Change(purchasePrice, cash);
    System.out.println(output.calculate_Change_in_Currency());
  }

}
