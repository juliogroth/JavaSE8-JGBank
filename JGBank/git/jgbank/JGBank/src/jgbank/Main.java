package jgbank;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap; //for 1.3.1 Adaptation of the table of accounts
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException; //external JAR json.simple 1.1.1

public class Main {
	

	public static void main(String[] args) {
		//1.1.2.a)Declare an array (Collection) of clients - OK
		List<Clients> listClients = new ArrayList<>();
		
		//1.1.2.b)Load this table using a method - 
		// TEST - testar multiplas inserções / USAR clients 1.1.1:	
		/*
		listClients.add(Clients.addNewClient("jj", "gg"));
		listClients.add(Clients.addNewClient("ja", "ga"));
		listClients.add(Clients.addNewClient("jb", "gb"));
		*/
		// TEST - testar print unitário de um cliente / USAR clients 1.1.1:
		//System.out.println(listClients.get(0).toString());
		
		List <Inserctions> listInserctions = new ArrayList<>();
		
		int numberOfClients = 3; //##CHANGE HERE THE SIZE OF CLIENTS LIST ###
		
		//# 1st FOR
		for (int i=0; i<numberOfClients; i++ ) {
			listInserctions.add(Inserctions.generateClients(i));
			//TEST - testar funcionamento listInserctions
			//System.out.println(listIserctions.get(i).toString()); //show result of generate clients
		}
		
		//transform each name given by listInserctions in a client in listClients
		//USE clients 1.1.2
		//## 2nd FOR
		for (int i=0; i<listInserctions.size(); i++) {
			listClients.add(Clients.addNewClient(listInserctions.get(i)));
		}
		
		//1.1.2.c) Use another method to display the contents of the table - OK
		//foreach c in clients ...
		//##3rd FOR
		for (int i = 0; i<listClients.size(); i++) {
			System.out.println(listClients.get(i).toString());
		}
		System.out.println("\n");
		
		//1.2.3 - Creation of the tablea account Version 2
		/*Joined the lists of current accounts (listCurrentAccount) and savings accounts (listSavingsAccount) in the same
		 *  list of accounts (listAccount)
		*/
//		List <Accounts> listAccount = new ArrayList<>();
//				
//		for (int i = 0; i<listClients.size(); i++) {
//			listAccount.add(new CurrentAccount("Current", listClients.get(i)));
//			listAccount.add(new SavingsAccount("Saving", listClients.get(i)));
//		}
		
		List <Accounts> listAccount = readXMLAccounts(listClients);
		
		
//HashMap<Integer, String> accountsHash = new HashMap<Integer, String>();//for 1.3.1 Adaptation of the table of accounts
//colocando a classe Accounts como atributo do hash, assim tento não usar string como valor e conseguir usar os valores de Accounts
		
//TESTE - Ver saída listAccount		
//		for (int i=0; i<listAccount.size();i++) {
//			System.out.println(listAccount.get(i));
//		}
		
//1.3.1 Method to return a HashTabel
		HashMap<Integer, Accounts> accountsHash = new HashMap<Integer, Accounts>(); //for 1.3.1 Adaptation of the table of accounts
		createMapAccounts(listAccount , accountsHash);
		
//1.3.1 Create a method with a stream to display this Map in ascending order according to the balance
		sortAccounts(accountsHash);

		
//1.3.4 Creation of the flow array
//create array to list transfers.
//[COMMENT ; IDENTIFIER ; AMOUNT ; TARGET ;  EFFECT  ; DATEOFFLOW]
//-String -  - int -     -double-  -int-   -boolean-   - Date -
//-Cur/Sav-  - fromID-   -amount-  -toID-    -OK?-	   - hoje +2 -
		
//		String[] operation0 = new String[] {"debit",	"Current", 	"1",	"50",	 "1",	"false",	"2021-11-20"};
//		String[] operation1 = new String[] {"credit",	"Current",	"0",	"100.50","0",	"false",	"2021-11-20"}; //0 = all
//		String[] operation2 = new String[] {"credit", 	"Saving",	"0",	"1500",	 "0",	"false",	"2021-11-20"}; //0 = all
//		String[] operation3 = new String[] {"transfer",	"Current",	"1",	"50",	 "2",	"false",	"2021-11-20"};
		
		
//2 ACCOUNT MANAGMENT – Advanced
//2.1 JSON file of flows
//make a method to load a file (JSON format) and fill in the Flow array
	
		
		String[] operation0 = readJSON("operation0.json");
		String[] operation1 = readJSON("operation1.json");
		String[] operation2 = readJSON("operation2.json");
		String[] operation3 = readJSON("operation3.json");
		
		//System.out.println(operation0[0] +", "+operation0[1] +", "+operation0[2] +", "+operation0[3] +", "+operation0[4] +", "+ 
		//		operation0[5] +", "+operation0[6]);
		//System.out.println(operation1[0] +", "+operation1[1] +", "+operation1[2] +", "+operation1[3] +", "+operation1[4] +", "+ 
		//		operation1[5] +", "+operation1[6]);
		
		readOperationArray(listAccount, operation0);
		System.out.println("\n");
		sortAccounts(accountsHash);
		System.out.println("\n");
		
		readOperationArray(listAccount, operation1);
		sortAccounts(accountsHash);
		System.out.println("\n");
		
		readOperationArray(listAccount, operation2);
		sortAccounts(accountsHash);
		System.out.println("\n");
		
		readOperationArray(listAccount, operation3);
		sortAccounts(accountsHash);
		System.out.println("\n");
		
	}
	
	
	public static List<Accounts> readXMLAccounts(List<Clients> listImported) { 
		List<Clients> listClients = listImported;
		List<Accounts> accounts = new ArrayList<>();

//https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
		
		final String FILENAME = "C:\\Users\\Julio\\Desktop\\HN-Services\\12.JavaSE\\Entregavel\\JGBank\\accounts.XML"; //NAO COLOQUEI COMO METODO 
		//Path pathToFile = Paths.get("C:\\Users\\Julio\\Desktop\\HN-Services\\12.JavaSE\\Entregavel\\JGBank\\accounts.XML");
	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {

	          // optional, but recommended
	          // process XML securely, avoid attacks like XML External Entities (XXE)
	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

	          // parse XML file
	          DocumentBuilder db = dbf.newDocumentBuilder();

	          Document doc = db.parse(new File(FILENAME));

	          // optional, but recommended
	          // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	          doc.getDocumentElement().normalize();

	          System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
	          System.out.println("------");

	          // get <staff> » account
	          NodeList list = doc.getElementsByTagName("account");
		
	          for (int temp = 0; temp < list.getLength(); temp++) {
	        	  
	              Node node = list.item(temp);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {

	                  Element element = (Element) node;

	                  // get staff's attribute »account - o id já estava, nao alterei
	                  //String id = element.getAttribute("id");
	                  int id = Integer.parseInt(element.getAttribute("id"));
	          
	                  // get text
	                  String label = element.getElementsByTagName("label").item(0).getTextContent(); //firstname »» label
	                  String balance = element.getElementsByTagName("balance").item(0).getTextContent();//lastname »» balance
	                  String accountNumber = element.getElementsByTagName("accountNumber").item(0).getTextContent(); // »» accountNumber
	                  String clientNumber = element.getElementsByTagName("clientNumber").item(0).getTextContent(); // »» clientNumber
	                  String name = element.getElementsByTagName("name").item(0).getTextContent(); // »» name
	                  
//	                  String label = element.getElementsByTagName("label").item(0).getTextContent(); //firstname »» label
//	                  double balance = Double.parseDouble(element.getElementsByTagName("balance").item(0).getTextContent());//lastname »» balance
//	                  int accountNumber = Integer.parseInt(element.getElementsByTagName("accountNumber").item(0).getTextContent()); // »» accountNumber
//	                  int clientNumber = Integer.parseInt(element.getElementsByTagName("clientNumber").item(0).getTextContent()); // »» clientNumber
//	                  String name = element.getElementsByTagName("name").item(0).getTextContent(); // »» name
	                  
	                  
	                  System.out.println("Current Element :" + node.getNodeName()); //IGUAL
	                  System.out.println("1º id de account : " + id);
	                  System.out.println("label : " + label);
	                  System.out.println("balance : " + balance);
	                  System.out.println("accountNumber : " + accountNumber);
	                  System.out.println("clientNumber : " + clientNumber);
	                  System.out.println("name : " + name);
	                 
	                  int clientNumberINT = Integer.parseInt(clientNumber);
	                  if(label.equals("Current")) {
	                	  accounts.add(new CurrentAccount("Current", listClients.get(clientNumberINT-1)));
	                	  
	                	  ///System.out.println("CORRENTE " + listClients.get(temp));
	                  }else {
	                	  accounts.add(new SavingsAccount("Saving", listClients.get(clientNumberINT-1)));
	                  }                 
		        	  
	              }
	          }

	      } catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
	                  
	      } 
		
		
		return accounts;
	}
	
	
	
	

	private static String[] readJSON(String file) {
		String localFile;
		localFile = file;
		JSONParser parser = new JSONParser();
		//System.out.println(localFile);
		
		try {
			Object obj = parser.parse(new FileReader("C:\\Users\\Julio\\Desktop\\HN-Services\\12.JavaSE\\Entregavel\\JGBank\\"+localFile));
			JSONObject jsonObject = (JSONObject)obj;
			
			String[] testeoperation = {
					(String) jsonObject.get("operation"), 
					(String) jsonObject.get("accountType"),
					(String) jsonObject.get("fromId"),
					(String) jsonObject.get("ammount"),
					(String) jsonObject.get("toId"),
					(String) jsonObject.get("statusTransf"),
					(String) jsonObject.get("dateOfTransf")
			};
			
			//System.out.println("TESTE" + testeoperation[0] + testeoperation[3]);
			
			return testeoperation;
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;

		}

//Criar MAP das contas com clientes.	
	public static HashMap<Integer, Accounts> createMapAccounts(List<Accounts> listAccount, HashMap<Integer, Accounts> accountsHash) {
		for (int i=0; i<listAccount.size();i++) {
			accountsHash.put(listAccount.get(i).getAccountNumber(), listAccount.get(i));
		}	
//		System.out.println("VIA MÉTODO");
//		System.out.println("Account hash \n" + accountsHash);
//		System.out.println("\n");
//		
		return accountsHash;
	}


//Create a method with a stream to display this Map in ascending order according to the balance
//TESTE COM HASH + STREAM - EXIBIR EM ORDEM CRESCENTE 
	public static void sortAccounts(HashMap<Integer, Accounts> accountsHash) {
		//System.out.println("VIA MÉTODO");
		
		//System.out.println("Sorted Accounts");
		accountsHash.entrySet().stream()
		.sorted((p1,p2)-> ((Double)p1.getValue().getBalance()).compareTo(p2.getValue().getBalance()))
		.forEach(acount -> System.out.println(acount));
		
		//1.3.5  -PREDICATE FUNCTION and display a message NEGATIVE account;
		accountsHash.entrySet().stream()
		.filter(acount -> acount.getValue().getBalance()<0)
		.sorted((p1,p2)-> ((Double)p1.getValue().getBalance()).compareTo(p2.getValue().getBalance()))
		.forEach(acount -> System.out.println("\n ***Negative Acount *** » " + acount));
		
	}
		
	//1.3.4 Creation of the flow array
	//create method to load the list transfers.
	//[COMMENT ; IDENTIFIER ; AMOUNT ; TARGET ;  EFFECT  ; DATEOFFLOW]
	//-String -  - int -     -double-  -int-   -boolean-   - Date -
	//-Cur/Sav-  - fromID-   -amount-  -toID-    -OK?-	   - hoje +2 -	
	// Example: {"transfer",	"current",	"1",	"50",	"2",	"false",	"hoje"};
		public static void readOperationArray(List<Accounts> listAccount, String[] operationToDo) {
			String	operation 	= operationToDo[0];
			String	accountType = operationToDo[1];
			int		fromId		= Integer.parseInt(operationToDo[2]); //0 = ALL
			double	ammount 	= Double.parseDouble(operationToDo[3]);
			int		toId		= Integer.parseInt(operationToDo[4]); //0 = ALL
			Boolean statusTransf= Boolean.parseBoolean(operationToDo[5]); //False = not yet/error - True = success
			LocalDate dateOfTransf= LocalDate.parse(operationToDo[6]).plusDays(2);
			
//			String	operation 	= operationToDo[0];
//			String	accountType = operationToDo[1];
//			int		fromId		= Integer.parseInt(operationToDo[2]); //0 = ALL
//			double	ammount 	= Double.parseDouble(operationToDo[3]);
//			int		toId		= Integer.parseInt(operationToDo[4]); //0 = ALL
//			Boolean statusTransf= Boolean.parseBoolean(operationToDo[5]); //False = not yet/error - True = success
//			LocalDate dateOfTransf= LocalDate.parse(operationToDo[6]).plusDays(2);


			//PROCESSAMENTO DE OPERAÇÕES UMA A UMA
			if (operation .equals("transfer")) {
				//PARTE 1 - primeiro faz o débito do emissor
				Flow transaction1 = new Flow() {};
				transaction1.setComment("debit"); //primeiro faz o débito do emissor
				transaction1.setAmount(ammount);
				transaction1.setTarget(fromId); //faz um débito do emissor
				transaction1.setDateOfFlow(dateOfTransf);
				transaction1.setEffect(true);
				transaction1.setIdentifier(fromId);
				listAccount.get(fromId-1).setTransaction(transaction1);
				
				//PARTE 2 - Agora faz um crédito para o receptor
				Flow transaction2 = new Flow() {};
				transaction2.setComment("credit"); //segundo faz o credito no receptor
				transaction2.setAmount(ammount);
				transaction2.setTarget(toId); //faz faz o credito no receptor
				transaction2.setDateOfFlow(dateOfTransf);
				transaction2.setEffect(true);
				transaction2.setIdentifier(fromId);
				listAccount.get(toId-1).setTransaction(transaction2);
				
			} else {
				if(toId != 0) { 
					Flow transaction = new Flow() {};
					transaction.setComment(operation);
					transaction.setAmount(ammount);
					transaction.setTarget(toId);
					transaction.setDateOfFlow(dateOfTransf);
					transaction.setEffect(true);
					transaction.setIdentifier(fromId);
					
					listAccount.get(toId-1).setTransaction(transaction);
				} 
				//PROCESSAMENTO DE OPERAÇÕES EM BLOCO ( IDENTIFICADOR 0 PARA CONTA DESTINO)
				else { 
					for (int i=0; i<listAccount.size();i++) {
						if(listAccount.get(i).label .equals(accountType)) {
							Flow transaction = new Flow() {};
							transaction.setComment(operation);
							transaction.setAmount(ammount);
							transaction.setTarget(i);
							transaction.setDateOfFlow(dateOfTransf);
							transaction.setEffect(true);
							//transaction.setIdentifier(fromId);
							
							listAccount.get(i).setTransaction(transaction);
						} else {
							//System.out.println("i= "+ i+ " - conta alvo: "+accountType + " diferente de tipo de conta: "+ listAccount.get(i).label);
						}
						
					}
							
				}
			}
	}

//#### FORA DO MAIN ####

}