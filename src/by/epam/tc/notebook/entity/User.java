package by.epam.tc.notebook.entity;

/**
 * Created by Aliaksei Boltak on 08/10/2016.
 */

public class User {

	private int users_id;
	private String login;
	private String pass;
	private int role;
	private int block_status;

	public User(int user_id, String login, String pass, int role, int block_status) {
		this.users_id = user_id;
		this.login = login;
		this.pass = pass;
		this.role = role;
		this.block_status = block_status;
	}

	public User(String login, String pass, int role, int block_status) {
		this.login = login;
		this.pass = pass;
		this.role = role;
		this.block_status = block_status;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getBlock_status() {
		return block_status;
	}

	public void setBlock_status(int block_status) {
		this.block_status = block_status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + block_status;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		result = prime * result + role;
		result = prime * result + users_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (block_status != other.block_status)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (role != other.role)
			return false;
		if (users_id != other.users_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [users_id=" + users_id + ", login=" + login + ", pass=" + pass + ", role=" + role
				+ ", block_status=" + block_status + "]";
	}
}
