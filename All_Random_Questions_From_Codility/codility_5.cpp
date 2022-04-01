#include <bits/stdc++.h>

using namespace std;


/*
 * Complete the function below.
 */
 
 
/*
             i
        001101
            ^
        curr = 2, 1, 2, 1
                        ^ 
        
        res = 0 + min(0, 2) = 0; 0 + min(2, 2) = 2; 2 + min(2, 1) = 3; 
        pr = 2, 2, 1
        curr = 1, 1, 1(r) 
        
        return res + min(1,1) = 4
*/ 
int counting(string s) 
{
    int result = 0, previous = 0, current = 1;
    for (int i = 1; i < s.length(); i++)
    {
        if(s.at(i-1) != s.at(i)) // checking dissimilar pairs
        {
            result = result + min(previous, current);
            previous = current;
            current = 1; //reset
        }
        else
        {
            current++;
        }
    }

    return result + min(previous, current);
}

int main()