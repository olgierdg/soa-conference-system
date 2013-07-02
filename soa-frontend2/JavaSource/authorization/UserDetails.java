package authorization;

import org.jboss.security.auth.spi.Users.User;

public class UserDetails extends User {
	String firstName;
	String secondName;
	String email;
	String phoneNumber;
	short age;
	
	public UserDetails() {
		super();
		
	}
	
	public UserDetails(String name) {
		super(name);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}
}
