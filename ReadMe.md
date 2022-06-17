### REFERENSI ###


## Producer Config
https://newrelic.com/blog/best-practices/kafka-best-practices
https://granulate.io/optimizing-kafka-performance/
https://strimzi.io/blog/2020/10/15/producer-tuning/
https://towardsdatascience.com/10-configs-to-make-your-kafka-producer-more-resilient-ec6903c63e3f

https://www.confluent.io/blog/error-handling-patterns-in-kafka/

Create topic automatically  if return null topic exception

##ERROR HANDLING WHILE PRODUCING DATA
* https://serkansakinmaz.medium.com/error-handling-in-kafka-producer-edfc05bcbbbf
* https://stackoverflow.com/questions/61654578/kafka-producer-callback-exception

Note

Exception that can be throw while produce data already handling in retries config , so no need to handle it manually
so if non retries thrown , then it's not caused poison pill for application

##Producer not need to flush after produce data
Producer not need to flush after produce data because its can block next message for send to broker
flush can be used for prevent data loss , if you call it , before you shutdown the producer
so if you just shutdown the service by force, than data loss can be happen
but if you call flush programmatically , then data loss can not be happen
If you implement flush , you are effectively implementing a sync producer
(which you shouldn't, see here: https://github.com/edenhill/librdkafka/wiki/FAQ#why-is-there-no-sync-produce-interface).

##Testing block with no message loss
* https://jack-vanlightly.com/blog/2018/9/14/how-to-lose-messages-on-a-kafka-cluster-part1
* https://jack-vanlightly.com/blog/2018/9/18/how-to-lose-messages-on-a-kafka-cluster-part-2