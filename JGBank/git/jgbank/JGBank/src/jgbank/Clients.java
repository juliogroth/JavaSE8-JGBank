package jgbank;

//1.1.1 Creation of the client class

public class Clients {
	private	int 	clientNumber = 0; //receberá o valor de cada iteração do controleId
	private	String 	firstName;
	private	String	name;
	private static int controlClientNumber = 0;
	
	
	@Override
	public String toString() {
		return "clientNumber= " + clientNumber + ", Name= " + firstName + " " + name;
	}
	
	public String toStringFullName() {
		return firstName + " " + name;
	}

/* retirado e continuou funcionando.
	//constructor
	public Clients(int clientNumber, String firstName, String name, int controlClientNumber) {
		super();
		this.clientNumber = clientNumber;
		this.controlClientNumber = controlClientNumber;
		this.firstName = firstName;
		this.name = name;
	}
*/	
//	retirado e continuou funcionando.
	public Clients() {
	}

	
	public int getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//### CLIENTS 1.1.1 ###- method used in 1.1.1
	/*
	public static Clients addNewClient(String firstName, String name) {
		Clients c = new Clients();
		//fazerlogica
		c.setClientNumber(++controlClientNumber); //fazer logica
		c.setFirstName(firstName);
		c.setName(name);
		
		return c;
	}
	*/
	
	//### CLIENTS 1.1.2 ###- method used in 1.1.2
	public static Clients addNewClient(Inserctions inserctions) {
		Clients c = new Clients();
		c.setClientNumber(++controlClientNumber); 
		c.setFirstName(inserctions.getFirstName());
		c.setName(inserctions.getName());
		
		return c;
	}
	
	

}
