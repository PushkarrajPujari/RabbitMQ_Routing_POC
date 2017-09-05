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
     * Stage 2 - Consumer Topic exchange
     * */
    public final static String EXCHANGE_TYPE = "topic";
    public final static String EXCHANGE_NAME = "EX3";
    public static String Queue_Name;
    public static MyConsumer myConsumer;
    public static String [] Routing_Key;
    public static void main(String[] args) {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.newConnection(getInput("Enter Connection Name"));
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME,EXCHANGE_TYPE);
            Queue_Name = channel.queueDeclare().getQueue();
            for(String routingKey:getRoutingKey()){
                channel.queueBind(Queue_Name,EXCHANGE_NAME,routingKey);
            }
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

    public static String [] getRoutingKey(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Number of Routing key to be entered");
        Routing_Key = new String[scanner.nextInt()];
        scanner.nextLine();
        for(int i = 0;i<Routing_Key.length;i++){
            System.out.println("Enter RoutingKey ["+i+"]");
            Routing_Key[i] = scanner.nextLine();
        }
        return Routing_Key;
    }
}
