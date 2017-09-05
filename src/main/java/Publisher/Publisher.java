package Publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author : Pushkarraj Pujari
 * @Since : 05/09/2017
 */
public class Publisher {
    public final static String EXCHANGE_TYPE = "direct";
    public final static String EXCHANGE_NAME = "EX2";
    public final static String ROUTING_KEY = "r1";
    public static String message ;
    public static void main(String[] args) {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            Connection connection = connectionFactory.newConnection("Publisher");
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            for(int i = 0; i <100 ;i++){
                message = String.valueOf(i);
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                Thread.currentThread().sleep(100);
            }
            channel.close();
            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
