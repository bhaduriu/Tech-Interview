'''
You are designing a word processor and implementing a autocomplete predict API. Given a huge training data
(list of lists), write a python code to calculate bigram frequency for this predict API.
These API should be fast enough to handle millions of transactions
'''

class Predictor:
    def __init__(self, training_data):
        self.res = {}
       
        fd = {}
        for item in training_data:
            item_length = len(item)
            i = 0
            while i+1 < item_length:
                if item[i] not in fd:
                    fd[item[i]] = [item[i+1]]
                else:
                    fd[item[i]].append(item[i+1])
                i+=1

        fdx = {}
        for i, j in fd.items():
            temp_dict = {}
            for items in j:
                if items not in temp_dict:
                    temp_dict[items] = 1
                else:
                    temp_dict[items] += 1
            fdx[i] = []
            for key, val in temp_dict.items():
                fdx[i].append((key,val))
       
        for i, j in fdx.items():
            self.res[i] = sorted(j)
   
    def predict(self, x):
        if x in self.res:
            print (self.res[x][0][0])
           
if __name__=="__main__":
    training_data = [
        ["I", "am", "Sam"],
        ["Sam", "and", "I", "can", "am"],
        ["Sam", "and", "I", "like", "green", "eggs"],
        ["Sam", "friends"]
    ]
    predict = Predictor(training_data)
    predict.predict("I")
    predict.predict("and")
    predict.predict("Sam")