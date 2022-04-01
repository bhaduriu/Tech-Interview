/*
Every such filter reduces the pollution of a factory by half. When a second (or subsequent) filter is applied, it again reduces by half the remaining pollution emitted after fitting the existing filter(s). For example, a factory that initially produces 6 units of pollution will generate 3 units with one filter, 1.5 units with two filters and 0.75 units with three filters.
You are given an array of N integers describing the amount of pollution produced by the factories. Your task is to find the minimum number of filters needed to decrease the total sum of pollution by at least half.
Write a function:
class Solution { public int solution(int[] A); }
which, given an array of integers A of length N, returns the minimum number of filters needed to reduce the total pollution by at least half.
Example: 1. Given A = [5, 19, 8, 1], your function should return 3. The initial total pollution is 5 + 19 + 8 + 1 = 33. We install two filters at the factory producing 19 units and one filter at the factory producing 8 units. Then the total pollution will be 5 + ((19 / 2)/ 2) + (8 / 2) + 1 = 5 + 4.75 + 4 + 1 = 14.75 which is less than 33 / 2 = 16.5, so we have achieved our goal.
Example: 2. Given A = [10, 10], your function should return 2, because we may install one filter at each factory.
Write an efficient algorithm.
*/
// you can use includes, for example:
#include <algorithm>
#include <iostream>
#include <queue>
#include <vector>
#include <iterator>
using namespace std;

// you can write to stdout for debugging purposes, e.g.
// cout << "this is a debug message" << endl;

int solution(vector<int> &A)
{
    // write your code in C++14 (g++ 6.2.0)
    priority_queue<float> pq;
    float total = 0.0f;
    int count = 0;
    for (auto i = A.begin(); i!=A.end(); i++)
    {
        total += *i;
        pq.push(*i);
    }
    float required = total/2;

    // Very Greedy, pick out max first.
    while(!pq.empty() && total > required)
    {
        float filter = pq.top();
        pq.pop();
        total -= filter/2;
        count++;
        pq.push(filter/2);
    }
    return count;
}
