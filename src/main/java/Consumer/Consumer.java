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
    public static Scanner scanner;
    public static String EXCHANGE_TYPE;
    public static String EXCHANGE_NAME;
    public static String Queue_Name;
    public static MyConsumer myConsumer;
    public static String [] Routing_Key;
    static {
        scanner = new Scanner(System.in);
        EXCHANGE_TYPE = getInput("Enter Exchange_Type");
        EXCHANGE_NAME = getInput("Enter Exchange_Name");
    }
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
        return scanner.nextLine();
    }

    public static String [] getRoutingKey(){
        if(!EXCHANGE_TYPE.equalsIgnoreCase("fanout")){
            System.out.println("Enter Number of Routing key to be entered");
            Routing_Key = new String[scanner.nextInt()];
            scanner.nextLine();
            for(int i = 0;i<Routing_Key.length;i++){
                System.out.println("Enter RoutingKey ["+i+"]");
                Routing_Key[i] = scanner.nextLine();
            }
        }else{
            System.out.println(EXCHANGE_TYPE +" exchange doesn't require a routing key");
            Routing_Key = new String[1];
            Routing_Key[0] = "";
        }
        return Routing_Key;
    }
}
