package Consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Author : Pushkarraj pujari
 * @Since : 05/09/2017
 */
public class MyConsumer extends DefaultConsumer {
    private Channel channel;
    private String message;

    public MyConsumer(Channel channel){
        super(channel);
        this.channel = channel;
    }

    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        this.message = new String(body);
        show();
    }

    public void show(){
        System.out.println("Message Received = "+ message);
    }
}
