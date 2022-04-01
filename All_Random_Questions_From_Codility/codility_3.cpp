// you can use includes, for example:
#include <algorithm>
#include <unordered_set>
#include <string>
using namespace std;

// you can write to stdout for debugging purposes, e.g.
// cout << "this is a debug message" << endl;

// DFS?? 
/* DFS not required. I already know the edges and I could arrange them in increasing fashion into a set. Since, they are added to the set in a sorted order: if there is an edge missing from edge "i" to "i+1", then false.
*/
bool solution(int N, vector<int> &A, vector<int> &B) {
    // write your code in C++14 (g++ 6.2.0)
    unordered_set<string> edges;
    int M = 0;

    if (A.size() == B.size())
        M = A.size();
    else
        return false;

    // O(M) -> where M = size of A or B
    for(int i = 0; i < M; i++)
    {
        int in = min(A.at(i), B.at(i));
        int out = max(A.at(i), B.at(i));
        string str = to_string(in) + "->" + to_string(out);
        edges.insert(str);
    }

    //O(N) -> worst case when found, hash set lookups -> O(1)
    for(int i = 1; i < N; i++)
    {
        string str = to_string(i) + "->" + to_string(i+1);
        unordered_set<string>::const_iterator found = edges.find(str);
        if(found == edges.end())
            return false;
    }
    return true;
}
