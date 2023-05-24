package com.pcandroiddev.kafkaproducerwikimedia;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * This method reads the realtime data form wikimedia and sends it to the broker
     */
    public void sendRealTimeMessagesFromWikiMedia() {
        String topic = "wikimedia_recentchange";

        /* To read real time stream data from wikimedia, we use event source
           The event handler is handled by the WikimediaChangesHandler class
         */

        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
        String eventSourceUrl = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder eventSourceBuilder = new EventSource.Builder(eventHandler, URI.create(eventSourceUrl));
        EventSource eventSource = eventSourceBuilder.build();

        eventSource.start();

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}
