package jgbank;

//1.1.2 - Creation of main class for tests

public class Inserctions {
	private String name;
	private String firstName;
	
	
	public Inserctions( String name, String firstName) {
		this.name = name;
		this.firstName = firstName;
	}
	
	public Inserctions() {

	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	public static Inserctions generateClients(int numberOfClients) {
		Inserctions inser = new Inserctions();
		numberOfClients++;
		inser.setFirstName("firstName" + numberOfClients);
		inser.setName("name" + numberOfClients);
		
		return inser;
	}

	@Override
	public String toString() {
		return "Inserctions [name=" + name + ", firstName=" + firstName + "]";
	}
	
	
	
	
	

}
