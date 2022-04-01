#include <bits/stdc++.h>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);


/*
 * Complete the 'goodSegment' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts following parameters:
 *  1. INTEGER_ARRAY badNumbers
 *  2. INTEGER lower
 *  3. INTEGER upper
 */

int goodSegment(vector<int> badNumbers, int lower, int upper)
{
    int max_segment = 0;
    int curr_segment = 0;
    int last_good_num = lower; // 3
    
    sort(badNumbers.begin(), badNumbers.end());
    // 7 15 22 37 49 60
    //               ^
    // 3
    // 48
    
    for (int i = 0; i < badNumbers.size(); i++)
    {
        if(badNumbers.at(i) <= upper) // 7, 15, 22, 37
        {
            curr_segment = badNumbers.at(i) - last_good_num; // 4, 7, 6, 14

            max_segment = max(max_segment, curr_segment);// 4, 7, 7, 14

            if(badNumbers.at(i) >= lower)// 7 >= 3, 15 >= 3, 22 >= 3, 37 >= 3
                last_good_num = badNumbers.at(i) + 1; // 8, 16, 23, 38
        }
    }
    
    // left side
    curr_segment = upper - last_good_num + 1; // 48 - 38 + 1 = 11
    return max_segment = max(max_segment, curr_segment); // max(14, 11) = 14
}
int main()
