package model;

public class User {
	
	private Integer id;
	private String name;
	private String password;
	private String email;
	private String phone_number;
	private String address;
	private String gender;
	private String role;
	private String nationality;
	
	public User(Integer id, String username, String password, String email, String phone_number, String address,
			String gender, String role, String nationality) {
		super();
		this.id = id;
		this.name = username;
		this.password = password;
		this.email = email;
		this.phone_number = phone_number;
		this.address = address;
		this.gender = gender;
		this.role = role;
		this.nationality = nationality;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	
	public static User currentUser;
	

}
