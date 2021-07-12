package it.mariosantoro.SemanticHarmonySocialNetwork;

import java.io.Serializable;
import java.util.List;

import net.tomp2p.peers.PeerAddress;

public class User implements Serializable{
	private String _nickname;
	private  String  _profile_key;
	private PeerAddress peerAddress;
	private List<String> friends;
	
	public User() {}
	public User(String _nickname, PeerAddress peerAddress, String _profile_key) {
		this._nickname= _nickname;
		this.peerAddress=peerAddress;
		this._profile_key=_profile_key;
	}
	public String getNickname() {
		return _nickname;
	}
	
	public PeerAddress peerAddress() {
		return peerAddress;
	}
	
 
	public String getProfileKey() {
		return _profile_key;
	}
	public void setProfileKey(String _profile_key) {
		this._profile_key= _profile_key;
	}
	public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	public PeerAddress getPeerAddress() {
		return peerAddress;
	}
 
	
	
}
