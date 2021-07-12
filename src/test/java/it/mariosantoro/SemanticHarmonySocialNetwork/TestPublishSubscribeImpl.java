package it.mariosantoro.SemanticHarmonySocialNetwork;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestPublishSubscribeImpl {

	protected SemanticHarmonySocialNetworkImpl peer0, peer1, peer2, peer3;
	
	public TestPublishSubscribeImpl() throws Exception{
		class MessageListenerImpl implements MessageListener{
			int peerid;
			public MessageListenerImpl(int peerid)
			{
				this.peerid=peerid;
			}
			public Object parseMessage(Object obj) {
				System.out.println(peerid+"] (Direct Message Received) "+obj);
				return "success";
			}
			
		}
		 peer0 = new SemanticHarmonySocialNetworkImpl(0, "127.0.0.1", new MessageListenerImpl(0));	
		 peer1 = new SemanticHarmonySocialNetworkImpl(1, "127.0.0.1", new MessageListenerImpl(1));
		 peer2 = new SemanticHarmonySocialNetworkImpl(2, "127.0.0.1", new MessageListenerImpl(2));
		 peer3 = new SemanticHarmonySocialNetworkImpl(3, "127.0.0.1", new MessageListenerImpl(3));
		
	}
	
	

}