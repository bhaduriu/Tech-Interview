'''
As the provider of a news aggregation service, you aim to provide your customers with a system that is as easy to use as possible. There are many different news providers, and it is tedious for users to subscribe to all of them manually, so you want to provide a single subscription that manages all the news providers for each customer.
Each subscriber is only interested in a certain set of topics, and should only receive news about those topics.
Each news item has an interest score and each subscriber nominates their minimum interest score. A subscriber should not be sent news that has a score lower than the subscriber's minimum.
The system may have to handle a lot of data and subscriptions, so it must be robust and not overload subscribers with too much data. We then choose to decouple the news input feed from the output towards the subscribers, giving more control to the system.
Problem Statement
Complete the functions described below in the NewsProvider class. Keep in mind that:
• If any constraint is violated when performing an operation, the operation must fail.
• If a constraint is said to be "guaranteed", you may assume it is never violated.
• Timestamps are represented as number of seconds since the Unix Epoch (Jan 1, 1970 UTC), using floating-point values valid to the millisecond precision. They are guaranteed to be positive numbers and fit into 32 bits.
• Guaranteed input constraints:
• 1 < N< 215, where N is the total number of instructions given to the program.
AddSubscription(id: integer, minInterest: integer, maxNewsPerSecond: integer, topics:
list[string]) - bool:

AddSubscription (id: integers minInterest: integer, maxNewsPerSecond: integer, topics:
list[string]) → bool:
• Register a new subscription for upcoming news on certain topics. Returns true if the operation succeeds, and false otherwise.
• id is an unique identifier of the subscription. If a subscription with the same id already exists, the subscription must be updated with the new parameters.
• mininterest represents the minimum interest score desired (inclusive).
• maxNewsPerSecond represents the maximum number of news items this subscription can receive per second. This constraint is based on a rolling window, i.e. at any given timestamp t, no more than maxNewsPerSecond news items can be delivered within the time window It - 1.0, t].
• topics is a list of news topics this subscription should consider.
• Input constraints:
• 1s id < 2 to power 32.
ndeni
• 1 ≤ minInterest < 2 to power 32
• 1 ≤ maxNewsPerSecond < 2 to power 12.
• 1 = length(topics) < 2 to power 10
RemoveSubscription(id: integer) → bool:
• Removes an existing subscription from the system.
Returns true if the operation succeeds, and false otherwise,
• id is the unique identifier of the subscription to be removed, If it doesn't exist, the operation fails.
NewsReceived (id: integer, timestamp: float, interest:
integer, topics: list[string]) → bool:

NewsReceived (id: integer, timestamp: float, interest: integer, topics: list[string]). bool:
• Indicates news on given topics have been received at a given timestamp. Returns true if the operation succeeds, and false otherwise.
• id is an unique identifier of these news. If it has already been used, the operation fails.
• interest represents the interest score of these news.
• Input constraints:
• 1 = id < 2 to power 32.
• 1 s interest < 2 to power 32.
• 1 ≤ length(topics) < 2 to power 10.
Publish (timestamp: float, maxAge: float) → dict[int, list[int]]:
• Computes the news to published at the given timestamp. Returns a map of subscription ids by news ids, i.e. all subscriptions to be notified per news id. The output does not need to be sorted in any particular order. If the operation fails, returns an empty map.
• If needed, news must be prioritized first by highest interest score, then oldest timestamp and highest id.
• A subscriber must never receive the same news twice.
• maxAge represents the maximum age of news to be published at this time. It is represented as a number of seconds, using floating-point values valid to the millisecond precision.
• Guaranteed input constraints:
• timestamp is ever increasing for calls of this function.
• Input constraints:
• 0 < maxAge < 2 to power 32.
'''

class Subscription:
    def __init__(self, id: int, min_interest: int, max_news_per_second: int, topics: list[str]):
        self.id = id
        self.min_interest = min_interest
        self.max_news_per_second = max_news_per_second
        self.topics = set(topics)
        self.news_timestamps = []
        self.news_ids = set()

    def update(self, min_interest, max_news_per_second, topics):
        self.min_interest = min_interest
        self.max_news_per_second = max_news_per_second
        self.topics = set(topics)

    def potential_news(self, timestamp):
        # user cam receive potential news 
        # count the news items in last 1 sec and return true/false if its less than max_news_per_second
        limit = timestamp - 1.0
        count = 0
        for t in self.news_timestamps:
            if t > limit:
                count += 1
        return count < self.max_news_per_second

    def sub_news_map(self, timestamp, news_id):
        # user news id map
        self.news_timestamps.append(timestamp)
        self.news_ids.add(news_id)


class News:
    def __init__(self, id: int, timestamp: float, interest: int, topics: list[str]):
        self.id = id
        self.timestamp = timestamp
        self.interest = interest
        self.topics = set(topics)

    def sortpriority(self):
        # custom sort logic based on priorities, highest interst, oldest timestamp, highest id
        return (-self.interest, self.timestamp, -self.id)


class NewsProvider:
    def __init__(self):
        self.subscriptions = {} # dict = (id, Subscription)
        self.news = {} # dict = (id, News)

    def AddSubscription(self, id: int, min_interest: int, max_news_per_second: int, topics: list[str]) -> bool:
        if id < 1 or id >= 2 ** 32:
            return False
        if min_interest < 1 or min_interest >= 2 ** 32:
            return False
        if max_news_per_second < 1 or max_news_per_second >= 2 ** 12:
            return False
        if len(topics) < 1 or len(topics) >= 2 ** 10:
            return False

        if id in self.subscriptions:
            self.subscriptions[id].update(min_interest, max_news_per_second, topics)
        else:
            self.subscriptions[id] = Subscription(id, min_interest, max_news_per_second, topics)
        return True

    def RemoveSubscription(self, id: int) -> bool:
        if id not in self.subscriptions:
            return False
        del self.subscriptions[id]
        return True

    def NewsReceived(self, id: int, timestamp: float, interest: int, topics: list[str]) -> bool:
        if id < 1 or id >= 2 ** 32:
            return False
        if interest < 1 or interest >= 2 ** 32:
            return False
        if len(topics) < 1 or len(topics) >= 2 ** 10:
            return False
        if id in self.news:
            return False

        self.news[id] = News(id, timestamp, interest, topics)
        return True

    def Publish(self, timestamp, max_age):
        if max_age <=0 or max_age >= 2 ** 32:
            return {}

        limit = timestamp - max_age

        potential_news_item = []
        for i in self.news.values():
            if i.timestamp >= limit:
                potential_news_item.append(i)

        potential_news_item.sort(key=lambda item: item.sortpriority())

        result = {}
        for news in potential_news_item:
            for sub in self.subscriptions.values():
                if news.id in sub.news_ids:
                    continue
                if not (sub.topics & news.topics): # NOT ( set AND topics) intersection
                # if there are no common topics
                    continue
                if news.interest < sub.min_interest:
                    continue
                if not sub.potential_news(timestamp):
                    continue

                sub.sub_news_map(timestamp, news.id)
                if news.id not in result:
                    result[news.id] = []
                result[news.id].append(sub.id)

        return result
    

np = NewsProvider()
print(f"subscribed={np.AddSubscription(1, 5, 2, ['radio', 'television'])}")
print(f"subscribed={np.AddSubscription(2, 7, 3, ['cable'])}")
print(f"news_received={np.NewsReceived(10, 100, 4, ['television'])}")
print(f"news_received={np.NewsReceived(11, 100, 5, ['television', 'cable'])}")
print(f"news_received={np.NewsReceived(12, 150, 7, ['radio', 'streaming'])}")
result = np.Publish(200, 100)
print("publish:")
for news_id, sub_ids in sorted(result.items()):
    print(f"  - news={news_id} to {sorted(sub_ids)}")