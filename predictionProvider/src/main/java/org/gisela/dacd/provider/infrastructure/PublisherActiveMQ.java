package org.gisela.dacd.provider.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.gisela.dacd.provider.utils.InstantTypeAdapter;
import org.gisela.dacd.provider.application.Publisher;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.gisela.dacd.provider.domain.Weather;
import java.time.Instant;

public class PublisherActiveMQ implements Publisher {
    private static final String url = "tcp://localhost:61616";
    private Connection connection;

    @Override
    public void start() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publish(Weather event) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        String jsonEvent = gson.toJson(event);
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("prediction.Weather");
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(jsonEvent);
            producer.send(message);
            System.out.println("JCG printing@@ '" + message.getText() + "'");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(){
        try {
            connection.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
