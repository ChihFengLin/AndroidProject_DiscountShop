package model;

public class Consumer extends Login {
	private String email;
	private String username;
	private String password;

	/* Constructor */
	public Consumer() {
		super();
	}

	public Consumer(String Email, String UserName, String PassWord) {
		super();
		this.email = Email;
		this.username = UserName;
		this.password = PassWord;
	}

	public void setEmail(String Email) {
		this.email = Email;
	}

	public void setUsername(String UserName) {
		this.username = UserName;
	}

	public void setPassword(String PassWord) {
		this.password = PassWord;
	}

	public String getEmail() {
		return this.email;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
