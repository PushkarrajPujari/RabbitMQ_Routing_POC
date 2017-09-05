package Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * @Author : Pushkarraj Pujari
 * @Since : 05/09/2017
 */
public class Consumer {
    /**
     * Stage 2 - Consumer Direct exchange
     * */
    public final static String EXCHANGE_TYPE = "direct";
    public final static String EXCHANGE_NAME = "EX2";
    public final static String ROUTING_KEY = "r1";
    public static String Queue_Name;
    public static MyConsumer myConsumer;

    public static void main(String[] args) {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.newConnection(getInput("Enter Connection Name"));
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME,EXCHANGE_TYPE);
            Queue_Name = channel.queueDeclare().getQueue();
            channel.queueBind(Queue_Name,EXCHANGE_NAME,ROUTING_KEY);
            myConsumer = new MyConsumer(channel);
            channel.basicConsume(Queue_Name,true,myConsumer);
            getInput("Press Enter to Exit .....");
            channel.close();
            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static String getInput(String string){
        System.out.println(string);
        return new Scanner(System.in).nextLine();
    }
}
