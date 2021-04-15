package slydm.geektimes.training.microprofile.reactive.streams;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/15 9:52
 */
public class ActiveMqClassicApiBaseDemo {


  @Test
  public void testProducerAndConsumerApiInVM() throws InterruptedException, JMSException {

    // Create a ConnectionFactory
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
    Connection connection = connectionFactory.createConnection();
    // Create a Connection
    connection.start();

    thread(new HelloWorldProducer(connection), false);
    thread(new HelloWorldProducer(connection), false);
    thread(new HelloWorldConsumer(connection), false);
    Thread.sleep(1000);
    thread(new HelloWorldConsumer(connection), false);
    thread(new HelloWorldProducer(connection), false);
    thread(new HelloWorldConsumer(connection), false);
    thread(new HelloWorldProducer(connection), false);
    Thread.sleep(1000);

    // Close a Connection
    connection.close();

  }


  @Test
  public void testProducerAndConsumerApi() throws InterruptedException, JMSException {

    // Create a ConnectionFactory
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
    Connection connection = connectionFactory.createConnection();
    // Create a Connection
    connection.start();

    thread(new HelloWorldProducer(connection), false);
    thread(new HelloWorldProducer(connection), false);
    thread(new HelloWorldConsumer(connection), false);
    Thread.sleep(1000);
    thread(new HelloWorldConsumer(connection), false);
    thread(new HelloWorldProducer(connection), false);
    thread(new HelloWorldConsumer(connection), false);
    thread(new HelloWorldProducer(connection), false);
    Thread.sleep(1000);

    // Close a Connection
    connection.close();

  }


  public static Thread thread(Runnable runnable, boolean daemon) {
    Thread brokerThread = new Thread(runnable);
    brokerThread.setDaemon(daemon);
    brokerThread.start();
    return brokerThread;
  }


  public static class HelloWorldProducer implements Runnable {

    private Connection connection;

    public HelloWorldProducer(Connection connection) {
      this.connection = connection;
    }

    public void run() {
      try {
        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("TEST.FOO");

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
        producer.send(message);

        // Clean up
        session.close();
      } catch (Exception e) {
        System.out.println("Caught: " + e);
        e.printStackTrace();
      }
    }
  }


  public static class HelloWorldConsumer implements Runnable, ExceptionListener {

    private Connection connection;

    public HelloWorldConsumer(Connection connection) {
      this.connection = connection;
    }


    public void run() {
      try {
        connection.setExceptionListener(this);

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("TEST.FOO");

        // Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer = session.createConsumer(destination);

        // Wait for a message
        Message message = consumer.receive(1000);

        if (message instanceof TextMessage) {
          TextMessage textMessage = (TextMessage) message;
          String text = textMessage.getText();
          System.out.println("Received: " + text);
        } else {
          System.out.println("Received: " + message);
        }

        consumer.close();
        session.close();
      } catch (Exception e) {
        System.out.println("Caught: " + e);
        e.printStackTrace();
      }
    }

    public synchronized void onException(JMSException ex) {
      System.out.println("JMS Exception occured.  Shutting down client.");
    }
  }

}
