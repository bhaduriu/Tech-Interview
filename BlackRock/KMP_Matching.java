// BlackRock: Question 2


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/*
interface pattern_Recognition:: interface so that external packages could use this to implement their logic

class string_Matching:: contains the entire business logic to implement KMP Pattern Matching Algorithm 
(private and protected members only, others may not like to know what we do here => abstracted)

class pattern_Matching:: parses the input string and starts finding matches for each blob by using string_Matching class 
(pattern_Matching inherits string_Matching as a sort of wrapper class)
(there is only one public method: start_Matching(), and people outside will just use this API)

class pattern_Matching implements pattern_Recognition
class pattern_Matching also extends string_Matching

class output_Parse_String: this guy's work is just to output the answer
*/


// interface because it could be exported as a library if some external guy wants to use our fancy Pattern Matching API
interface pattern_Recognition
{
  abstract String start_Matching();
}

// Using KMP (Knuth Morris Pratt) Algorithm for Pattern Matching
class string_Matching 
{
  String pattern, blob;
  int pattern_length, blob_length;
  int longest_prefix_suffix[];

  // No Constructor for blob because, everytime blob will change so not a good idea to assign blob to some initial values everytime 
  // Also, that would make us instantiate objects eveytime while passing a new blob => bad memory usage 
  string_Matching(String pat)
  {
    this.pattern = pat;
    this.pattern_length = pat.length();
    
    // longest_prefix_suffix is the longest prefix suffix values for pattern 
    longest_prefix_suffix = new int[pattern_length];
  }
  
  // initializing the class fields, because cannot use constructor (aforementioned reason)
  private void init_blob(String txt)
  {
    this.blob = txt;
    blob_length = txt.length();
    longest_prefix_suffix[0] = 0;
  }
  
  // calculate longest prefix suffix values for pattern 
  private void calculate_Longest_Prefix_Suffix() 
  {
    // length of the previous longest prefix suffix
    int previous_length = 0, from_index = 1; 

    // the loop calculates lps[i] for i = 1 to M-1
    while (from_index < pattern_length) 
    {
      if (pattern.charAt(from_index) == pattern.charAt(previous_length)) 
      {
        longest_prefix_suffix[from_index] = previous_length+1;
        from_index++;
      }
      else
      {
        if (previous_length != 0)
        {
          previous_length = longest_prefix_suffix[previous_length-1]; 
        }
        else
        {
          longest_prefix_suffix[from_index] = previous_length;
          from_index++;
        }
      }
    }
  }

  protected int find_Matches(String txt)
  {
    int pattern_index = 0, blob_index = 0, next_blob_index = 0;
    int result = 0;
    
    init_blob(txt);
    
    calculate_Longest_Prefix_Suffix();
    
    while (blob_index < blob_length)
    {
      if (pattern.charAt(pattern_index) == blob.charAt(blob_index))
      {
        pattern_index++; blob_index++;
      }
      
      if (pattern_index == pattern_length) // 1 pattern found
      { 
        // Iterate to find the next pattern
        pattern_index = longest_prefix_suffix[pattern_index-1];
        result++;
      }
      else if (blob_index < blob_length && pattern.charAt(pattern_index) != blob.charAt(blob_index)) // not found
      {
        if (pattern_index != 0)
        {
          pattern_index = longest_prefix_suffix[pattern_index-1]; 
        }
        else
        {
          blob_index++; 
        }
      }
    }
    return result;
  }
}

class pattern_Matching extends string_Matching implements pattern_Recognition
{
  String input, pattern;
  String[] blobs;
  
  pattern_Matching(String txt, String pat)
  {
    super(pat);
    this.input = txt;
    this.pattern = pat;
    find_Blobs();
  }
  
  private void find_Blobs()
  {
    blobs = input.split("\\|"); 
  }
  
  @Override
  public String start_Matching()
  {
    parse_Output_String output = new parse_Output_String(blobs.length);
    
    for(String each_blob : blobs)
    {
      int answer = 0;
      if (pattern != "")
      {
        answer = find_Matches(each_blob); 
      }
      output.parse_Output(answer);
    }
    
    return output.final_Answer();
  }
}

class parse_Output_String
{
  int size, index, sum; String str;
  String[] out_pattern;
  
  parse_Output_String(int count)
  {
    this.size = count;
    index = 0; str = ""; sum = 0;
    out_pattern = new String[count];
  }
  
  public void parse_Output(int frequency)
  {
    out_pattern[index] = Integer.toString(frequency);  
    index++; sum = sum + frequency;
  }
  
  public String final_Answer()
  {
    for (int i = 0; i < size; i++)
    {
      str = str + out_pattern[i] + "|";
    }
    
    str = str + Integer.toString(sum);
    return str;
  }
}

public class Main
{
  /**
   * Iterate through each line of input.
   */
  public static void main(String[] args) throws IOException {
    InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
    BufferedReader in = new BufferedReader(reader);
    String line;
    while ((line = in.readLine()) != null) {
      String[] splittedInput = line.split(";");
      String pattern = splittedInput[0];
      String blobs = splittedInput[1];
      Main.doSomething(pattern, blobs);
    }
  }
  
  public static void doSomething(String pattern, String blobs) {
    // Write your code here. Feel free to create more methods and/or classes
    pattern_Matching parse = new pattern_Matching(blobs, pattern);
    System.out.println(parse.start_Matching());
  }
}
