int solution(vector<int> A) {
    int res = 0;
    for (int i = 1; i < A.size(); i++) {
        int len = 0;
        int diff = A.at(i) - A.at(i - 1);
        for (int j = i + 1; j < A.size(); j++) {
            if (A.at(j) - A.at(j - 1) != diff)
		break;
            len++;
        }
        res += (len * (len + 1)/2);
        i += len;
    }
    return res < 1000000000 ? res : -1;
}
