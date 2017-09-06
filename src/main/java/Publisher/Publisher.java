package Publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * @Author : Pushkarraj Pujari
 * @Since : 05/09/2017
 */
public class Publisher {
    /**
     * Stage 4 :- Branch TopicExchange Publisher
     * */
    public static Scanner scanner;
    public static String EXCHANGE_TYPE;
    public static String EXCHANGE_NAME;
    public static String ROUTING_KEY;
    public static String message;
    public static boolean flag = true;
    public static String response;

    static {
        scanner = new Scanner(System.in);
        EXCHANGE_TYPE = getInput("Enter Exchange_Type");
        EXCHANGE_NAME = getInput("Enter Exchange_Name");
    }
    public static void main(String[] args) {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            Connection connection = connectionFactory.newConnection(getInput("Enter Connection Name"));
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            while (flag){
                ROUTING_KEY = (EXCHANGE_TYPE.equalsIgnoreCase("fanout"))?"":getInput("Enter Routing_Key");
                message = getInput("Enter Your Message");
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                response = getInput("Do you wish to continue with messaging ..... ?[y/n]");
                flag = (response.equalsIgnoreCase("y")?true:false);
            }
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
