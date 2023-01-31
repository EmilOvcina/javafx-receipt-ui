package application;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User
{
	//List of users, which the manager should manage for.
	private List<User> users;
	private Accountant accountant;
	
	/**
	 * @param id - User ID
	 * @param name - Name of the manager
	 * @param accountant - Account which the manager should receive handled receipts from.
	 */
	public Manager(int id, String name, Accountant accountant)
	{
		super(id, name);
		users = new ArrayList<>();
		this.accountant = accountant;
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
	/**
	 * @return accountant which the manager receives handled receipts from
	 */
	public Accountant getAccountant() {
		return accountant;
	}
	/**
	 * @param accountant - new accountant for the manager 
	 */
	public void setAccountant(Accountant accountant) {
		this.accountant = accountant;
	}
}
