#include <bits/stdc++.h>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);


static map<char, int> roman_numerals = {   {'I', 1}, {'V', 5}, {'X', 10}, {'L', 50},
                                                    {'C', 100},
                                                    {'D', 500},
                                                    {'M', 1000}
                                                    /* C, D, M not required not still kept,
                                                    who knows what might go wrong :D*/
                                                };

// splits a string by a delimiter and returns a vector
vector<string> splitStringUtil(const string &str, const char &delimiter)
{
    stringstream s_stream(str);
    string token; vector<string> tokens;
    while (getline(s_stream, token, delimiter))
    {
        tokens.push_back(token);
    }
    return tokens;
}

// converts roman number string to integer
int parseOrdinalNumberUtil(const string &ordinal_str)
{
    int size_of_ordinal_str = (int) ordinal_str.size() - 1;
    int ordinal_number = roman_numerals[ordinal_str[size_of_ordinal_str]];

    for (int i = size_of_ordinal_str - 1; i >= 0; i--)
    {
        if (roman_numerals[ordinal_str[i]] < roman_numerals[ordinal_str[i+1]])
            ordinal_number = ordinal_number - roman_numerals[ordinal_str[i]];
        else
            ordinal_number = ordinal_number + roman_numerals[ordinal_str[i]];
    }
    return ordinal_number;
}

bool compareTo(const string &first, const string &second)
{
    // getting rid of the whitespace between firstName and ordinal
    // Constraint: 2 space separated values
    vector<string> _first = splitStringUtil(first, ' ');
    vector<string> _second = splitStringUtil(second, ' ');

    string firstName_from_first = _first[0];
    string ordinal_from_first = _first[1];
    string firstName_from_second = _second[0];
    string ordinal_from_second = _second[1];
    
    if (firstName_from_first < firstName_from_second)
        return true;
    if ((firstName_from_first == firstName_from_second) &&
    (parseOrdinalNumberUtil(ordinal_from_first) <= parseOrdinalNumberUtil(ordinal_from_second)))
        return true;
    return false;
}

// Complete the getSortedList function below.
vector<string> getSortedList(const vector<string> &names)
{
    auto &royal_names = const_cast<vector<string>&>(names);
    sort(royal_names.begin(), royal_names.end(), compareTo);
    return royal_names;
}

int main()
{
    ofstream fout(getenv("OUTPUT_PATH"));

    string names_count_temp;
    getline(cin, names_count_temp);

    int names_count = stoi(ltrim(rtrim(names_count_temp)));

    vector<string> names(names_count);

    for (int i = 0; i < names_count; i++) {
        string names_item;
        getline(cin, names_item);

        names[i] = names_item;
    }

    vector<string> res = getSortedList(names);

    for (int i = 0; i < res.size(); i++) {
        fout << res[i];

        if (i != res.size() - 1) {
            fout << "\n";
        }
    }

    fout << "\n";

    fout.close();

    return 0;
}

string ltrim(const string &str) {
    string s(str);

    s.erase(
        s.begin(),
        find_if(s.begin(), s.end(), not1(ptr_fun<int, int>(isspace)))
    );

    return s;
}

string rtrim(const string &str) {
    string s(str);

    s.erase(
        find_if(s.rbegin(), s.rend(), not1(ptr_fun<int, int>(isspace))).base(),
        s.end()
    );

    return s;
}
