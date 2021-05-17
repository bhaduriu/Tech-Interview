#include <map>
#include <set>
#include <list>
#include <cmath>
#include <ctime>
#include <deque>
#include <queue>
#include <stack>
#include <string>
#include <bitset>
#include <cstdio>
#include <limits>
#include <vector>
#include <climits>
#include <cstring>
#include <cstdlib>
#include <fstream>
#include <numeric>
#include <sstream>
#include <iostream>
#include <algorithm>
#include <unordered_set>
#include <ctype.h>
#include <memory>

#define NODES 26

using namespace std;

class ErrorTree
{
public:
    ErrorTree(const string&);
    ~ErrorTree();
    bool checkForErrors();
    string lexicographicSExpression(const char&);
    char getRoot();

private:
    class Impl;
    unique_ptr<Impl> impl_;
};

class ErrorTree::Impl
{
    public:
        Impl(const string&);
        ~Impl() = default;
        bool checkForErrors();
        string lexicographicSExpression(const char&);
        char getRoot();

    private:
        string _input;
        bool _graph[NODES][NODES];
        unordered_set<char> _nodes;
        bool _visited[NODES];
        vector <int>_adjacency_list[NODES];
        int _num_roots;
        char _root;
    
        void resetBoolArrays();
        bool isE1();
        void fillGraph(const int&, const int&);
        void fillNode(const char&, const char&);
        bool isE2();
        bool isE3();
        bool IsCycle(const char&);
        bool isE4();
        bool isE5();
};

ErrorTree::Impl::Impl(const string &input)
{
    this->_input = input;
    _num_roots = 0;
    resetBoolArrays();
}

ErrorTree::ErrorTree(const string& input)
    : impl_(new Impl(input)) { }

ErrorTree::~ErrorTree() = default;

bool ErrorTree::Impl::checkForErrors()
{
    bool result = false;

    result = isE1();
    if (result)
    {
        cout << "E1" << endl;
        return result;
    }

    result = isE2();
    if (result)
    {
        cout << "E2" << endl;
        return result;
    }

    result = isE3();
    if (result)
    {
        cout << "E3" << endl;
        return result;
    }
    
    result = isE4();
    if (result)
    {
        cout << "E4" << endl;
        return result;
    }
    
    result = isE5();
    if (result)
    {
        cout << "E5" << endl;
        return result;
    }

    return result;
}

bool ErrorTree::checkForErrors()
{
    return impl_->checkForErrors();
}

string ErrorTree::Impl::lexicographicSExpression(const char &root)
{
    // no children => left and right empty
    string first_child = "", second_child = "";
    
    for(int i = 0; i < NODES; i++)
    {
        if(_graph[root-'A'][i])
        {
            first_child = lexicographicSExpression((char)(i+'A'));
            for(int j = i + 1; j < NODES; j++)
            {
                if(_graph[root-'A'][j])
                {
                    second_child = lexicographicSExpression((char)(j+'A'));
                    break;
                }
            }
            break;
        }
    }

    return string("(") + root + first_child + second_child + string(")");
}

string ErrorTree::lexicographicSExpression(const char &node)
{
    return impl_->lexicographicSExpression(node);
}

char ErrorTree::Impl::getRoot()
{
    return this->_root;
}

char ErrorTree::getRoot()
{
    return impl_->getRoot();
}

void ErrorTree::Impl::resetBoolArrays()
{
    for (int i = 0; i < NODES; i++)
    {
        for (int j = 0; j < NODES; j++)
        {
            _graph[i][j] = false;
        }
    }
    for (int i = 0; i < NODES; i++)
    {
        _visited[i] = false;
    }
}

// O(N) where, N = input_length + 2 (const)
bool ErrorTree::Impl::isE1()
{
    bool result = false;
    
    /* adding whitespace at the beginning and end of the string, so that
     it makes us easy to check of input errors */
    string temp_string = string(" ") + _input + string(" ");
    
    for (int i = 0; i < temp_string.length()-1; i+=6)
    {
        // _(A,B)_
        if (!(isspace(temp_string[i+0]) && temp_string[i+1] == '(' && isalpha(temp_string[i+2]) && temp_string[i+3] == ',' && isalpha(temp_string[i+4]) && temp_string[i+5] == ')' && isspace(temp_string[i+6])))
        {
            return true;
        }
    }

    return result;
}

void ErrorTree::Impl::fillGraph(const int &parent, const int &child)
{
    _graph[parent][child] = true;
    _adjacency_list[parent].push_back(child);
}

void ErrorTree::Impl::fillNode(const char &parent_node, const char &child_node)
{
    _nodes.insert(parent_node);
    _nodes.insert(child_node);
}

// O(N) where N = input_length
bool ErrorTree::Impl::isE2()
{
    bool result = false;
    int parent, child;

    for(int i = 1; i < _input.length(); i+=6)
    {
        parent = _input[i] - 'A'; child = _input[i+2] - 'A';
        
        //duplicate edge
        if(_graph[parent][child])
        {
            return true;
        }
        
        fillGraph(parent, child);
        fillNode(_input[i], _input[i+2]);
    }
    
    return result;
}

/* O(N) where N is the number of Nodes */
bool ErrorTree::Impl::isE3()
{
    bool result = false;
    
    for(int i = 0; i < NODES; i++)
    {
        int children = (int)_adjacency_list[i].size();
        if(children > 2)
        {
            return true;
        }
    }

    return result;
}

//true means there is a cycle, false means no cycle
bool ErrorTree::Impl::IsCycle(const char &node)
{
    bool result = false;

    // node has already been visited found a cycle
    // base condition
    if(_visited[node-'A'])
    {
        return true;
    }

    _visited[node-'A'] = true;

    for(int i = 0; i < NODES; i++)
    {
        if(_graph[node-'A'][i])
        {
            if(IsCycle((i+'A')))
            {
                return true;
            }
        }
    }

    return result;
}

/* check for both E4 and E5, Large time taken for E4
 compensates the need to go through E5 again */
bool ErrorTree::Impl::isE4()
{
    bool result = false; _root = ' ';
            
    for(auto x : _nodes)
    {
        for(int i = 0; i < NODES; i++)
        {
            if(_graph[i][x-'A'])
                break;

            if(i==25)
            {
                _num_roots++;
                _root = x;
                
                if(IsCycle(x))
                {
                    return true;
                }
            }
        }
    }

    if(_num_roots==0)
    {
        // cycle possible, if no root
        return true;
    }
    
    return result;
}

bool ErrorTree::Impl::isE5()
{
    return _num_roots > 1;
}


int main()
{
    string input = "(A,C) (A,D) (B,D) (B,E)";
    //getline(cin, input);

    ErrorTree et(input);

    //If errors false, printout lexicographically smallest S-Expression
    if (!et.checkForErrors())
    {
        char root = et.getRoot();
        cout << et.lexicographicSExpression(root) << endl;
    }

    return 0;
}
