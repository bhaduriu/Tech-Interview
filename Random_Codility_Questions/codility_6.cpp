#include <iostream>
#include <algorithm>
#include <vector>
#include <list>
using namespace std;

class Graph
{
	int city; //vertices
	list<int> *adj; // adjacenct lists

public:
	Graph(int city)
	{
	    this->city = city;
	    adj = new list<int>[city];
    	}
    
	void dfs(int city, vector<bool> &visited)
	{
    	visited[city] = true;
    
    	for (auto i = adj[city].begin(); i != adj[city].end(); ++i)
    	{
    	    if (!visited[*i])
    		{
    		    dfs(*i, visited);
    		}
    	}
	}
	
	void addroad(int city1, int city2)
	{
	    adj[city2].push_back(city1); // NOTE: swapped!!
    	}
	
	int findRome()
	{
    	vector <bool> visited(city, false);
    
    	int v = 0;
    
    	for (int i = 0; i < city; i++)
    	{
    		if (visited[i] == false)
    		{
    			dfs(i, visited);
    			v = i;
    		}
    	}
    
    	fill(visited.begin(), visited.end(), false);
    	dfs(v, visited);
    	
    	for (int i=0; i<city; i++)
    	{
    	    if (visited[i] == false)
    		{
    		    return -1;
    		}
    	}
    
    	return v;
	}
};

int solution(vector<int> &A, vector<int> &B) {
    
    int unique_vertices[200001]; int count = 0;
    for (int i=0;i<200001;++i)
    {
        unique_vertices[i] = 0;
    }
    for (int i = 0; i<A.size(); ++i)
    {
        unique_vertices[A[i]] = 1;
    }
    for (int i = 0; i<B.size(); ++i)
    {
        unique_vertices[B[i]] = 1;
    }
    
    for (int i = 0; i<200001; ++i)
    {
        if(unique_vertices[i] == 1)
        {
            count++;
        }
    }

    Graph g(count);

    for (int i = 0; i<A.size(); ++i)
    {
        g.addroad(A[i], B[i]);
    }
	
    return g.findRome();
}

int main()
{
	vector <int> A = {1, 2, 3};
	vector <int> B = {0, 0, 0};
// 	vector <int> A = {0, 5, 2, 4, 1};
// 	vector <int> B = {2, 2, 3, 3, 3};

	cout << solution(A, B) << endl;

	return 0;
}
