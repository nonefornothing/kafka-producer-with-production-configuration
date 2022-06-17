package mandiri.kafka.producerPE.controller;

import mandiri.kafka.producerPE.engine.ProducerEngine;
import mandiri.kafka.producerPE.model.mutasiRDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerActivation {

    private final Logger logger = LoggerFactory.getLogger(ProducerActivation.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ProducerEngine G1;
    public ProducerEngine G2;
    public ProducerEngine G3;
    public ProducerEngine G4;
    public ProducerEngine B1;
    public ProducerEngine B2;

    @GetMapping("/activate/")
    public String activate() {

        logger.info("Start make dump data");

        //make sure that extref id and value date changed to date that you send data
        mutasiRDN goodData = new mutasiRDN("2204279941677444118500001               ","1220009752364","IDR","20220427","2635.75","402635.75","                                        ","1","C","N597","400000.00","                                                                                                                        ","22042799416774441185090843089           ","https://hotsapi.miraeasset.co.id:8443/notification");
        mutasiRDN oldData = new mutasiRDN("1001019941677444118500001               ","1220009752364","IDR","20100101","2635.75","402635.75","                                        ","1","C","N597","400000.00","                                                                                                                        ","10010199416774441185090843089           ","https://hotsapi.miraeasset.co.id:8443/notification");
        mutasiRDN invalidLengthData = new mutasiRDN("2204279941677444118500001","1220009752364","IDR","20220427","2635.75","402635.75","","1","C","N597","400000.00","","22042799416774441185090843089","https://hotsapi.miraeasset.co.id:8443/notification");

        logger.info("Done make dump data");

        logger.info("start produce data to Clean Topic");

        this.G1 = new ProducerEngine( "Thread-1", goodData , "CLEANTOPICPE", "FAILEDTOPICPE",kafkaTemplate);
//        this.G2 = new ProducerEngine( "Thread-2", goodData , "CLEANTOPICPE", "FAILEDTOPICPE",kafkaTemplate);
//        this.G3 = new ProducerEngine( "Thread-3", goodData , "CLEANTOPICPE", "FAILEDTOPICPE",kafkaTemplate);
//        this.G4 = new ProducerEngine( "Thread-4", goodData , "CLEANTOPICPE", "FAILEDTOPICPE",kafkaTemplate);
//        this.B1 = new ProducerEngine( "Thread-5", oldData , "CLEANTOPICPE", "FAILEDTOPICPE",kafkaTemplate);
//        this.B2 = new ProducerEngine( "Thread-6", invalidLengthData , "CLEANTOPICPE", "FAILEDTOPICPE",kafkaTemplate);

        G1.start();
//		G2.start();
//		G3.start();
//		G4.start();
//
//		B1.start();
//		B2.start();

        return "Producer PE ACTIVED";
    }

    @GetMapping("/deactivate/")
    public String deactivate() {
        try {
            G1.stop();
//            G2.stop();
//            G3.stop();
//            G4.stop();
//            B1.stop();
//            B2.stop();
        } catch (Exception e) {
            logger.error("error while deactivate thread producer , " + e.getMessage() );
        }
        return "Producer PE DEACTIVATED";
    }

}
