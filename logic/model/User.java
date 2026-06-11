package logic.model;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2235214154810229240L;
	private int id;
	private String type;
	private String username;
	private String password;
	
	public User(int id_user, String type, String username, String password, int admin_id) {
		super();
		this.id = id_user;
		this.type = type;
		this.username = username;
		this.password = password;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public int getId() {
		return id;
	}

	
	
}
