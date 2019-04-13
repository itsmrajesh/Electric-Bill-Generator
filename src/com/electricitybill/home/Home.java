package com.electricitybill.home;

import java.util.Scanner;

import com.electricitybill.dao.*;
import com.electricitybill.domain.Customer;
import com.electricitybill.idgenerator.IDGenerator;
import com.electricitybill.services.EBillServices;
import com.electricitybill.services.EBillServicesImpl;

public class Home {
	private static EBillDao ebilldao = new EBillDaoImpl();
	private static EBillServicesImpl es = EBillServicesImpl.getInstance();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Welcome Electricity Bill Generation Application");
		System.out.println("1.Manager \t 2.Customer \t 3.Bescom Employee \t 4.Exit");
		int choice = sc.nextInt();
		String uName;
		String password;
		switch (choice) {
		case 1:
			System.out.println("Manager Section");
			System.out.println("Enter user name");
			uName = sc.next();
			System.out.println("Enter Password");
			password = sc.next();
			if (ebilldao.validateAdminDao(uName, password)) {
				adminOperations();
			}
			break;
		case 2:
			String cNumber;
			while (true) {
				System.out.println("Enter Customer Number or Enter 0 to Exit");
				cNumber = sc.next().toUpperCase();
				if (!cNumber.equals("0")) {
					if (es.isCustomerPresent(cNumber)) {
						userOperations(cNumber);
						break;
					} else {
						System.out.println("Customer with CNUMBER - " + cNumber + " is not Present.... Try Again....");
					}
				} else {
					System.out.println("Exiting Customer Mode....");
					Home.main(null);
					break;
				}
			}
			break;
		case 3:
			System.out.println("Welcome Employee");
			System.out.println("Enter user name ");
			uName = sc.next();
			System.out.println("Enter Password ");
			password = sc.next();
			if (uName.equalsIgnoreCase("EMP101") && password.equals("ebill")) {
				employeeOperations();
				break;
			} else {
				System.out.println("Invalid Login");
				Home.main(null);
				break;
			}
		case 4:
			System.out.println("Terminiting the Application.....");
			System.exit(0);
			break;
		}

	}

	private static void employeeOperations() {
		while (true) {
			System.out.println("Employee Operations....");
			System.out.println(
					"1.Generate Bill\n2.Search Bill\n3.Pay Customer Bill\n4.Get Total Due Of Customer\n5.EXIT");
			int empChoice = sc.nextInt();
			switch (empChoice) {
			case 1:
				String cNumber;
				while (true) {
					System.out.println("Generate Bill ");
					System.out.println("Enter Customer Number ");
					cNumber = sc.next().toUpperCase();
					if (es.isCustomerPresent(cNumber)) {
						es.generateBill(cNumber);
						break;
					} else {
						System.out.println("Customer with CNUMBER - " + cNumber + " is not Present.... Try Again....");
					}
				}
				break;
			case 2:
				System.out.println("Search Bill....");
				System.out.println("Enter Bill Number :");
				String billNumber = sc.next().toUpperCase();
				es.searchBill(billNumber);
				break;
			case 3:
				System.out.println("Pay total Bill Due....");
				System.out.println("Enter Customer Number ");
				cNumber = sc.next().toUpperCase();
				if (es.isCustomerPresent(cNumber)) {
					es.payTotalBill(cNumber);
					break;
				} else {
					System.out.println("Customer with CNUMBER - " + cNumber + " is not Present.... Try Again....");
				}
				break;
			case 4:
				System.out.println("Enter Customer Number ");
				cNumber = sc.next().toUpperCase();
				System.out.println("Total Due Bill..");
				System.out.println("Total Pending bill is in Rupess :- " + ebilldao.getTotalBillDao(cNumber));
				break;
			case 5:
				System.out.println("Exiting Employee Mode.....");
				Home.main(null);
				break;
			}
		}
	}

	private static void userOperations(String cNumber) {
		while (true) {
			System.out.println("User Operations.......");
			System.out.println(
					"1.Pay Total Bill\n2.Show Total Due Bill\n3.Show All Bill Details\n4.Search Bill\n5.Pay Monthly Bill\n6.Exit");
			System.out.println("Enter Your Choice (1-6) : ");
			int userChoice = sc.nextInt();
			String billNumber;
			switch (userChoice) {
			case 1:
				es.payTotalBill(cNumber);
				break;
			case 2:
				System.out.println("Total Due Bill..");
				System.out.println("Total Pending bill is in Rupess :- " + ebilldao.getTotalBillDao(cNumber));
				break;
			case 3:
				System.out.println("All Bill Details......");
				ebilldao.showBillDetails(cNumber);
				break;
			case 4:
				System.out.println("Search Bill....");
				System.out.println("Enter Bill Number :");
				billNumber = sc.next().toUpperCase();
				es.searchBill(billNumber);
				break;
			case 5:
				System.out.println("Enter Bill Number : ");
				billNumber = sc.next();
				es.payBillByBillNumber(cNumber, billNumber);
				break;
			case 6:
				System.out.println("Exiting customer Mode......");
				Home.main(null);
				break;
			}
		}

	}

	private static void adminOperations() {
		while (true) {
			System.out.println("------------------------");
			System.out.println(
					"1.Add Customer\n2.View All Customers\n3.Search Customer\n4.Bill Generation\n5.Search Bill\n6.Update Unit Price\n7.EXIT");
			System.out.println("Enter your Choice ");
			int adminChoice = sc.nextInt();
			switch (adminChoice) {
			case 1:
				es.addCustomer();
				break;
			case 2:
				es.display();
				break;

			case 3:
				System.out.println("Enter Customer Number or Name or ID");
				String str = sc.next();
				es.searchCustomer(str);
				break;
			case 4:
				String cNumber;
				while (true) {
					System.out.println("Generate Bill ");
					System.out.println("Enter Customer Number ");
					cNumber = sc.next().toUpperCase();
					if (es.isCustomerPresent(cNumber)) {
						es.generateBill(cNumber);
						break;
					} else {
						System.out.println("Customer with CNUMBER - " + cNumber + " is not Present.... Try Again....");
					}
				}

				break;
			case 5:
				System.out.println("Search Bill....");
				System.out.println("Enter Bill Number to search :- ");
				String billNumber = sc.next().toUpperCase();
				es.searchBill(billNumber);
				break;
			case 6:
				System.out.println("Update Unit Price ");
				System.out.println("Current Unit Price is :- " + ebilldao.getUnitPrice());
				System.out.println("Enter Unit Price to Update :-");
				double updatedUnitPrice = sc.nextDouble();
				ebilldao.updateUnitPrice(updatedUnitPrice);
				break;
			case 7:
				System.out.println("Exiting Manager Mode......");
				Home.main(null);
				break;
			}
		}
	}

}
