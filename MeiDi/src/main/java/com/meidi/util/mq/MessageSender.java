//package com.meidi.util.mq;
//
//
//import com.message.MQMessage;
//
//import java.io.Serializable;
//
//import javax.jms.ObjectMessage;
//import javax.jms.Queue;
//import javax.jms.QueueConnection;
//import javax.jms.QueueConnectionFactory;
//import javax.jms.QueueSender;
//import javax.jms.QueueSession;
//
//public class MessageSender {
//	// 连接工厂，JMS 用它创建连接
//	private QueueConnectionFactory connectionFactory;
//	// JMS 客户端到JMS Provider 的连接
//	private QueueConnection connection;
//	// 一个发送消息的线程
//	private QueueSession session;
//	// 消息的目的地;消息发送给谁.
//	private Queue destination;
//	// 消息发送者
//	private QueueSender sender;
//
//	private String queue;
//	private Serializable messageObject;
//
//	public MessageSender(String queue, Serializable msgObj) {
//		this.queue = queue;
//		this.messageObject = msgObj;
//	}
//
//	public MessageSender(String queue, MQMessage msgObj) {
//		this.queue = queue;
//		this.messageObject = msgObj;
//	}
//
//	public void execute() {
//		try {
//			this.connectionFactory = MessageConnection.getConnectionFactory();
//
//			this.connection = connectionFactory.createQueueConnection();
//
//			this.connection.start();
//
//			this.session = this.connection.createQueueSession(Boolean.TRUE.booleanValue(), 1);
//
//			this.destination = this.session.createQueue(this.queue);
//
//			this.sender = this.session.createSender(this.destination);
//
//			this.sender.setDeliveryMode(1);
//
//			ObjectMessage obj = this.session.createObjectMessage(this.messageObject);
//			this.sender.send(obj);
//			this.session.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			try {
//				if (this.connection != null)
//					this.connection.close();
//			} catch (Throwable localThrowable) {
//			}
//		} finally {
//			try {
//				if (this.connection != null)
//					this.connection.close();
//			} catch (Throwable localThrowable1) {
//			}
//		}
//	}
//
//}
