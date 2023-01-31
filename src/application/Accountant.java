package application;

import java.util.ArrayList;
import java.util.List;

public class Accountant extends User
{
	//List of users, which the accounant should account for.
	private List<User> users;
	
	/**
	 * @param id - User ID
	 * @param name - Name of the accountant
	 */
	public Accountant(int id, String name) {
		super(id, name);
		users = new ArrayList<>();
	}
	/**
	 * @param user - User to be added under the accountant
	 */
	public void addUser(User user) {
		users.add(user);
	}
	/**
	 * @param user - User to be removed from the accountant
	 */
	public void removeUser(User user) {
		users.remove(user);
	}
	/**
	 * @return list of users the accountant accounts for
	 */
	public List<User> getUsers() {
		return users;
	}

}
