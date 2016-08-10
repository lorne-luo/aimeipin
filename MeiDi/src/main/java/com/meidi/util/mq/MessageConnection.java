//package com.meidi.util.mq;
//
//import com.meidi.util.MdConstants;
//import org.apache.activemq.ActiveMQConnection;
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//public class MessageConnection {
//	private static ActiveMQConnectionFactory connectionFactory;
//
//	public static ActiveMQConnectionFactory getConnectionFactory() {
//		if (connectionFactory == null) {
//			String host = MdConstants.MQ_HOST;
//			connectionFactory = new ActiveMQConnectionFactory(
//					ActiveMQConnection.DEFAULT_USER,
//					ActiveMQConnection.DEFAULT_PASSWORD,
//					"failover:(tcp://" + host + "?wireFormat.maxInactivityDuration=0&keepAlive=true)?jms.prefetchPolicy.all=500&initialReconnectDelay=10000&useExponentialBackOff=false&maxReconnectAttempts=0&randomize=false");
//		}
//		return connectionFactory;
//	}
//
//}
