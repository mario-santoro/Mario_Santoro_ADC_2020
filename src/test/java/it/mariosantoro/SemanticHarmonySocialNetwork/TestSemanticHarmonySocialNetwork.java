package it.mariosantoro.SemanticHarmonySocialNetwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class TestSemanticHarmonySocialNetwork {
	protected SemanticHarmonySocialNetworkImpl peer0, peer1, peer2, peer3;
	
	public TestSemanticHarmonySocialNetwork() throws Exception{
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
	    assertEquals("1110001", key2);
		
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
		boolean flag= peer0.join(key, "peer0");
		assertTrue(flag);		
	}
	
	
	@Test
	void testCaseGetFriends(TestInfo testInfo){
		
		
		List<Integer> answer0=new ArrayList<Integer>();
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		answer0.add(1);
		String key= peer0.createAuserProfileKey(answer0);
		peer0.join(key, "peer0");
	  
		List<Integer> answer1=new ArrayList<Integer>();
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		answer1.add(0);
		String key1= peer1.createAuserProfileKey(answer1);
		peer1.join(key1, "peer1");
		
		List<Integer> answer2=new ArrayList<Integer>();
		answer2.add(1);
		answer2.add(1);
		answer2.add(1);
		answer2.add(0);
		answer2.add(0);
		answer2.add(0);
		answer2.add(1);
		String key2=peer2.createAuserProfileKey(answer2);
		peer2.join(key2, "peer2");
		
		
		List<Integer> answer3=new ArrayList<Integer>();
		answer0.add(1);
		answer0.add(1);
		answer0.add(0);
		answer0.add(0);
		answer0.add(0);
		answer0.add(0);
		answer0.add(0);
		String key3= peer3.createAuserProfileKey(answer3);
		peer3.join(key3, "peer3");
		
		
	  List<String> friends = peer0.getFriends();
      List<String> friends1 = peer1.getFriends();
      List<String> friends2 = peer2.getFriends();
      List<String> friends3 = peer3.getFriends();
      
      //revisionare gli assilt
      assert(friends.contains("peer1"));
      assert(friends1.contains("master"));
      assert(friends3.contains("peer2"));
      assert(friends2.contains("peer3"));

      assert(!friends2.contains("master"));
      assert(!friends3.contains("master"));

      assert(!friends2.contains("peer1"));
      assert(!friends3.contains("peer1"));

      assert(!friends.contains("peer2"));
      assert(!friends.contains("peer3"));

      assert(!friends1.contains("peer2"));
      assert(!friends1.contains("peer3"));
		
	}
	
}
