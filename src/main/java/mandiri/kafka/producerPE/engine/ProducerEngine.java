package mandiri.kafka.producerPE.engine;

import org.apache.kafka.common.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mandiri.kafka.producerPE.model.mutasiRDN;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ProducerEngine implements Runnable {

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    private Thread t;
    private final String threadName;
    private final mutasiRDN data;
    private final String topicName;
    private final String failedTopicName;
    private final String goodData;
    private final String badData;
    private volatile boolean activation = true;
    private long totalData;

    @Value("${dir.failed.producer}")
    private String dirFailedProducer;

    private final Logger logger = LoggerFactory.getLogger(ProducerEngine.class);

    public ProducerEngine(String threadName, mutasiRDN data, String topicName, String failedTopicName, KafkaTemplate<String, String> template) {
        this.threadName = threadName;
        this.data = data;
        this.topicName = topicName;
        this.failedTopicName = failedTopicName;
        System.out.println("Creating " +  data.toString() );
        this.goodData = data.toString();
        System.out.println("Creating " +  data.toInvalidJsonString());
        this.badData = data.toInvalidJsonString();
        System.out.println("Creating " +  threadName );
        this.kafkaTemplate = template;
    }



    public void run() {

        Date waktu = new Date();
        int hourNow;

        logger.info("Running " +  threadName );

        while(activation){
          hourNow = waktu.getHours();

            String sentData;

            if (getRandomNumber(1,50)==10){
                sentData = goodData;
                logger.info("goodData choosen to sent");
            }else{
                sentData = badData;
                logger.info("badData choosen to sent");
            }

            if(hourNow == 9){
                for(int i=0;i<4;i++){
                    try{
                        totalData++;
                        kafkaTemplate.send(topicName,sentData);
//                        sendDatatoKafka(topicName,sentData);
                        TimeUnit.MILLISECONDS.sleep(1000); //delay 1 detik dalam pengiriman jam 9 - 9:59
                        logger.info("Produce Data ke " + totalData + "di " +  threadName );
                    }catch (Exception e){
                        logger.error("Error while sending data to kafka broker with " + e.getMessage());
                        writeToFile(sentData);
                    }
                }
            }else if(hourNow > 9 && hourNow <= 12){
                if (getRandomNumber(1,2)==1){
                    try{
                        totalData++;
                        kafkaTemplate.send(topicName,sentData);
//                        sendDatatoKafka(topicName,sentData);
                        TimeUnit.MILLISECONDS.sleep(1000); //delay 1 detik dalam pengiriman jam 10 - 12:59
                        logger.info("Produce Data ke " + totalData + "di " +  threadName );
                    }catch (Exception e){
                        logger.error("Error while sending data to kafka broker with " + e.getMessage());
                        writeToFile(sentData);
                    }
                }
                else{
                    logger.error("Data not sent [hourNow > 9 && hourNow <= 13]");
                }
            }else if(hourNow > 12 && hourNow <= 14){
                if (getRandomNumber(1,2)==1){
                    try{
                        totalData++;
                        kafkaTemplate.send(topicName,sentData);
//                        sendDatatoKafka(topicName,sentData);
                        TimeUnit.MILLISECONDS.sleep(1000); //delay 1 detik dalam pengiriman jam 10 - 12:59
                        logger.info("Produce Data ke " + totalData + "di " +  threadName );
                    }catch (Exception e){
                        logger.error("Error while sending data to kafka broker with " + e.getMessage());
                        writeToFile(sentData);
                    }
                }
                else {
                    logger.error("Data not sent  [hourNow > 12 && hourNow <= 14]");
                }
            }
            else{
                if(getRandomNumber(1,10)==1){
                    try{
                        totalData++;
                        kafkaTemplate.send(topicName,sentData);
//                        sendDatatoKafka(topicName,sentData);
                        TimeUnit.MILLISECONDS.sleep(5000); //delay 1 detik dalam pengiriman jam lainnya
                        logger.info("Produce Data ke " + totalData + "di " +  threadName );
                    }catch (Exception e){
                        logger.error("Error while sending data to kafka broker with " + e.getMessage());
                        writeToFile(sentData);
                    }
                } else{
                    logger.error("Data not sent [10% probability]");
                }
            }
        }

    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void stop(){
        activation = false;
        logger.info("thread " + this.threadName + " stopped" );
    }

    private void writeToFile(String bodyReal) {
        try {
            if(!bodyReal.isEmpty()) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
                Files.write(Paths.get(dirFailedProducer+sdf1.format(new java.util.Date())+ "-failed-mansek.txt"), (bodyReal+ System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
            else{
                logger.error("Error...!!! write to failed PE file ...!!! Data null" );
            }
        }catch (IOException e3) {
            logger.error("Error...!!! write to failed PE file ...!!!" + e3.getMessage());
        }

    }


    private void sendToRetryTopic(String topicName, int partition ,String keyData,String sentData){
        try{
            kafkaTemplate.send(topicName,sentData);
            kafkaTemplate.send(topicName,partition,keyData,sentData);
            logger.info("Produce Failed Data : " + sentData + " + to topic : " +  topicName);

            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, sentData);
            future.completable();
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

//				public void onCompletion(RecordMetadata metadata, Exception e) {
//					if(e.getMessage() == "RecordTooLargeException" || e.getMessage() == "UnknownServerException") {
//						e.printStackTrace();
//					} else {
//						System.out.println("The offset of the record we just sent is: " + metadata.offset());
//					}
//				}


                @Override
                public void onFailure(Throwable ex) {

//					if (ex == InvalidTopicException){
//						logger.info("hallo");
//					}
//					if (ex.getCause() == InvalidTopicException){
//						logger.error("retries with non retryable exception");
//					}else{
//						logger.info("retries with retryable exception");
//					}
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    logger.info("sent message='{}' with offset={}", sentData,
                            result.getRecordMetadata().offset());
                }


//				@Override
//				public void onFailure(InvalidTopicException | OffsetMetadataTooLarge | RecordBatchTooLargeException | RecordTooLargeException | UnknownServerException nonRetryable) {
//					logger.error("unable to send message='{}'", sentData, nonRetryable);
//				}
            });

        }catch (InvalidTopicException | OffsetMetadataTooLarge | RecordBatchTooLargeException | RecordTooLargeException | UnknownServerException e3){
            logger.error("write to file...!!!"+" | Data  : " + sentData);
            writeToFile(sentData);
        }catch (Exception e){
            logger.error("Error while sending data to kafka broker with " + e.getMessage());
        }
    }


//    private void sendDatatoKafka(String topicName,String sentData) throws Exception {
//        try{
////            kafkaTemplate.send(topicName,sentData).get(10,TimeUnit.MILLISECONDS);
////            logger.info("Produce Data : " + sentData + " + to topic : " +  topicName);
//            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, sentData);
//            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//
//				public void onCompletion(RecordMetadata metadata, Exception e) {
//					if(e.getMessage() == "RecordTooLargeException" || e.getMessage() == "UnknownServerException") {
//						e.printStackTrace();
//					} else {
//						System.out.println("The offset of the record we just sent is: " + metadata.offset());
//					}
//				}
//
////                @Override
////                public void onFailure(Throwable ex) {
////
////                    logger.error("retries with non retryable exception" + ex.getMessage());
////
//////                    ex.getMessage() =
//////					if (ex == InvalidTopicException){
//////						logger.info("hallo");
//////					}
//////					if (ex.getCause() == InvalidTopicException){
//////						logger.error("retries with non retryable exception");
//////					}else{
//////						logger.info("retries with retryable exception");
//////					}
////                }
//
////                @Override
////                public void onSuccess(SendResult<String, String> result) {
////                    logger.info("sent message='{}' with offset={}", sentData,
////                            result.getRecordMetadata().offset());
////                }
//
//            });
//
//        }catch (InvalidTopicException | OffsetMetadataTooLarge | RecordBatchTooLargeException | RecordTooLargeException | UnknownServerException e3){
//            logger.error("write to file...!!!"+" | Data  : " + sentData);
//            writeToFile(sentData);
//        }catch (Exception e){
//            logger.error("Error while sending data to kafka broker with " + e.getMessage());
//        }
//    }


}
