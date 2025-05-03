package model;

public class Utilisateur {
	private String login;
	private int role;

	public Utilisateur(String login, int role) {
		this.login = login;
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}
