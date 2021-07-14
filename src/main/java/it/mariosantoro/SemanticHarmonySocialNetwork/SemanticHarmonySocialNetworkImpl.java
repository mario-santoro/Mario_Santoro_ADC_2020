package it.mariosantoro.SemanticHarmonySocialNetwork;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.rpc.ObjectDataReply;
import net.tomp2p.storage.Data;

public class SemanticHarmonySocialNetworkImpl implements SemanticHarmonySocialNetwork{
	final private Peer peer;
	final private PeerDHT _dht;
	final private int DEFAULT_MASTER_PORT=4000;
	private String _nick_name="";


	public SemanticHarmonySocialNetworkImpl( int _id, String _master_peer, final MessageListener _listener) throws Exception
	{
		peer= new PeerBuilder(Number160.createHash(_id)).ports(DEFAULT_MASTER_PORT+_id).start();
		_dht = new PeerBuilderDHT(peer).start();	

		FutureBootstrap fb = peer.bootstrap().inetAddress(InetAddress.getByName(_master_peer)).ports(DEFAULT_MASTER_PORT).start();
		fb.awaitUninterruptibly();
		if(fb.isSuccess()) {
			peer.discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
		}else {
			throw new Exception("Error in master peer bootstrap.");
		}

		peer.objectDataReply(new ObjectDataReply() {

			public Object reply(PeerAddress sender, Object request) throws Exception {
				return _listener.parseMessage(request);
			}
		});
	}

	@Override
	public List<String> getUserProfileQuestions() {
		List<String> questions= new ArrayList<String>();


		questions.add("ti piace lo sport?\n");
		questions.add("ti piace il calcio?\n");
		questions.add("ti piace il tennis?\n");
		questions.add("ti piace il basket?\n");
		questions.add("ti piace la Juventus?\n");
		questions.add("ti piace l'Inter?\n");
		questions.add("ti piace il Milan?\n");                  

		return questions;
	}

	@Override
	public String createAuserProfileKey(List<Integer> _answer) {
		String _profile_key="";
		for(int i=0;i<_answer.size();i++) {
			_profile_key+=_answer.get(i);

		}

		return _profile_key;
	}

	@Override
	public boolean join(String _profile_key, String _nick_name) {
		try {
			this._nick_name=_nick_name;

			//creo oggetto utente assegnando nick_name, profile_key e indirizzo del peer 
			User u= new User(_nick_name, peer.peerAddress(), _profile_key);
			List<String> users= new ArrayList<String>(); //lista di nickname memorizzati nella rete
			List<String> friends= new ArrayList<String>();//lista di amici del nuovo utente
			//controllo che il nickname non sia già assegnato 
			FutureGet futureGet = _dht.get(Number160.createHash(_nick_name)).start();
			futureGet.awaitUninterruptibly();
		
			if( futureGet.isEmpty()) {	

				//recupero dalla dht la lista delgi utenti (loro nickname)
				FutureGet futureGet2 = _dht.get(Number160.createHash("users")).start().awaitUninterruptibly();
				//controllo che non sia vouta
				if(!futureGet2.isEmpty()) {		

					//converto oggetto futureGet in Lista di stringhe
					users = (List<String>) futureGet2.dataMap().values().iterator().next().object();

					//scorro array di utenti per trovare amici					 
					for(int i=0;i<users.size();i++) {

						//recupero il singlo utente tramite nickname
						FutureGet futureGet3 = _dht.get(Number160.createHash(users.get(i))).start().awaitUninterruptibly();

						User newFriend = (User) futureGet3.dataMap().values().iterator().next().object();
						// controllo distance hamming e invio messaggio	                    
						int distance= HammingDistance(_profile_key, newFriend.getProfileKey());	                      
						if(distance<3) {
							FutureDirect d = _dht.peer().sendDirect(newFriend.getPeerAddress()).object( "hai un nuovo amico: " + _nick_name).start().awaitUninterruptibly();
							newFriend.getFriends().add(_nick_name);
							friends.add(newFriend.getNickname());
							_dht.put(Number160.createHash(newFriend.getNickname())).data(new Data(newFriend)).start().awaitUninterruptibly();
						}


					}


				}

				//aggiunta del nuovo utente alla lista di utenti nella dht
				users.add(_nick_name);
				u.setFriends(friends);					  
				_dht.put(Number160.createHash("users")).data(new Data(users)).start().awaitUninterruptibly();
				_dht.put(Number160.createHash(_nick_name)).data(new Data(u)).start().awaitUninterruptibly();
				return true;
			}else {

				return false;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> getFriends() {	
		User u= null;
		List<String> users;
		try {
			//recuperiamo dalla rete il peer corrente
			FutureGet futureGet = _dht.get(Number160.createHash(_nick_name)).start().awaitUninterruptibly();
			u = (User) futureGet.dataMap().values().iterator().next().object();		

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u.getFriends();
	}

	private int HammingDistance(String profile_key_one, String profile_key_two) {
		int distance=0;

		for(int i=0;i<profile_key_one.length();i++){
			if(profile_key_one.charAt(i) != profile_key_two.charAt(i) ) {
				distance++;

			}

		}

		return distance;
	}

	public boolean sendMessage(String _friend_nick_name, String message) {			 	
		try {
			//recupero peer corrente dalla dht
			FutureGet futureGet = _dht.get(Number160.createHash(_nick_name)).start().awaitUninterruptibly();
			User sender = (User) futureGet.dataMap().values().iterator().next().object();
			//per controllare se il nickname inserito sia un suo amico 
			if(sender.getFriends().contains(_friend_nick_name)) {
				FutureGet futureGet2 = _dht.get(Number160.createHash(_friend_nick_name)).start().awaitUninterruptibly();
				User reciver = (User) futureGet2.dataMap().values().iterator().next().object();
				if(futureGet2.isSuccess()&& !futureGet2.isEmpty()) {
					FutureDirect futureDirect = _dht.peer().sendDirect(reciver.getPeerAddress()).object("hai un nuovo messaggio da " + _nick_name + ": " + message).start().awaitUninterruptibly();
					return true;				
				}else {
					return false;
				}

			}else {

				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}


	//da capire bene
	@SuppressWarnings("unchecked")
	public boolean leaveNetwork(){
		List<String> users;
		try {
		
			_dht.remove(Number160.createHash(_nick_name)).start().awaitUninterruptibly();			
			FutureGet futureGet = _dht.get(Number160.createHash("users")).start();
			futureGet.awaitUninterruptibly();		      
			users = (List<String>) futureGet.dataMap().values().iterator().next().object();
			users.remove(_nick_name);
			_dht.put(Number160.createHash("users")).data(new Data(users)).start().awaitUninterruptibly();	       
			

		 
			for(int i=0;i<users.size();i++) {
				//recupero il singolo utente tramite nickname
				FutureGet futureGet3 = _dht.get(Number160.createHash(users.get(i))).start().awaitUninterruptibly();
				User oldFriend = (User) futureGet3.dataMap().values().iterator().next().object();
			                
				if(oldFriend.getFriends().contains(_nick_name)) {
					FutureDirect d = _dht.peer().sendDirect(oldFriend.getPeerAddress()).object( "l'amico "+ _nick_name+ " ha lasciato la rete").start().awaitUninterruptibly();
					oldFriend.getFriends().remove(_nick_name);					
					_dht.put(Number160.createHash(oldFriend.getNickname())).data(new Data(oldFriend)).start().awaitUninterruptibly();
				}

			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		_dht.peer().shutdown().awaitUninterruptibly();
		return true;
	}

}