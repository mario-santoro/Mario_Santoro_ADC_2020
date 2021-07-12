package it.mariosantoro.SemanticHarmonySocialNetwork;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;



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
	
	@Test
	void testCaseGetUserProfileQuestions(TestInfo testInfo){
		List<String> q0= peer0.getUserProfileQuestions();
		List<String> q1=peer1.getUserProfileQuestions();
		List<String> q2=peer2.getUserProfileQuestions();
		List<String> q3=peer3.getUserProfileQuestions();
		assertNotNull(q0);
		assertNotNull(q1);
		assertNotNull(q2);
		assertNotNull(q3);
	    assertEquals(7, q0.size());
	    assertEquals(7, q1.size());
	    assertEquals(7, q2.size());
	    assertEquals(7, q3.size());
	    assert(q0.equals(q1));
	    assert(q0.equals(q2));
	    assert(q0.equals(q3));
	 
	}
	
	@Test
	void testCaseCreateAUserProfileKey(TestInfo testInfo){
		
		
		List<Integer> answer0=new ArrayList<Integer>();
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		String key= peer0.createAuserProfileKey(answer0);
		assertNotNull(key);
		assertEquals(7, key.length());
	    assertEquals("1111111", key);
		
		List<Integer> answer1=new ArrayList<Integer>();
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		String key1=peer1.createAuserProfileKey(answer1);
		assertNotNull(key1);
		assertEquals(7, key1.length());
	    assertEquals("0000000", key1);
		
		List<Integer> answer2=new ArrayList<Integer>();
		answer2.add(1);
		answer2.add(1);
		answer2.add(1);
		answer2.add(0);
		answer2.add(0);
		answer2.add(0);
		answer2.add(1);
		String key2=peer2.createAuserProfileKey(answer2);
		assertNotNull(key2);
		assertEquals(7, key2.length());
	    assertEquals("1110000", key2);
		
	}
	
	
	@Test
	void testCaseJoin(TestInfo testInfo){
		
		
		List<Integer> answer0=new ArrayList<Integer>();
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		String key= peer0.createAuserProfileKey(answer0);
		assertNotNull(key);
		assertEquals(7, key.length());
	    assertEquals("1111111", key);
		
		List<Integer> answer1=new ArrayList<Integer>();
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		String key1=peer1.createAuserProfileKey(answer1);
		assertNotNull(key1);
		assertEquals(7, key1.length());
	    assertEquals("0000000", key1);
		
		List<Integer> answer2=new ArrayList<Integer>();
		answer2.add(1);
		answer2.add(1);
		answer2.add(1);
		answer2.add(0);
		answer2.add(0);
		answer2.add(0);
		answer2.add(1);
		String key2=peer2.createAuserProfileKey(answer2);
		assertNotNull(key2);
		assertEquals(7, key2.length());
	    assertEquals("1110000", key2);
		
	}
	
	/*@Test
	void testCasePublishToTopic(TestInfo testInfo){
		assertTrue(peer3.publishToTopic("Alice", "peer 0 send on topic Alice!"));
	}
	//TODO to remove it!
	void testCaseGeneral(TestInfo testInfo){
	
		try {
			
			peer1.createTopic("Alice");
			peer1.subscribetoTopic("Alice");
			peer2.subscribetoTopic("Alice");
			peer3.subscribetoTopic("Alice");
			
			peer1.createTopic("Bob");
			peer1.subscribetoTopic("Bob");
			peer2.subscribetoTopic("Bob");
						
			
			peer0.publishToTopic("Alice", "peer 0 send on topic Alice!");
			
			peer2.unsubscribeFromTopic("Alice");
			
			peer2.leaveNetwork();
			
			peer0.publishToTopic("Alice", "peer 0 send on topic Alice!");
			peer0.publishToTopic("Alice", "peer 0 send on topic Alice!");
			
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
*/
}