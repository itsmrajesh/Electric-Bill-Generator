package com.electricitybill.home;

import java.util.Scanner;

import com.electricitybill.dao.*;
import com.electricitybill.domain.Customer;
import com.electricitybill.idgenerator.IDGenerator;
import com.electricitybill.services.EBillServices;
import com.electricitybill.services.EBillServicesImpl;

public class Home {
	private static EBillDao ebilldao;
	private static EBillServicesImpl es;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		es = EBillServicesImpl.getInstance();
		ebilldao = new EBillDaoImpl();
		System.out.println("Welcome Electricity Bill Generation Application");
		System.out.println("1.Manager \t 2.Customer \t 3.Exit");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Manager Section");
			System.out.println("Enter user name");
			String uName = sc.next();
			System.out.println("Enter Password");
			String password = sc.next();
			if (ebilldao.validateAdminDao(uName, password)) {
				adminOperations();
			}
			break;
		case 2:
			String cNumber;
			while (true) {
				System.out.println("Enter Customer Number ");
				cNumber = sc.next().toUpperCase();
				if (es.isCustomerPresent(cNumber)) {
					userOperations(cNumber);
					break;
				} else {
					System.out.println("Customer with CNUMBER - " + cNumber + " is not Present.... Try Again....");
				}
			}
			break;
		case 3:
			System.out.println("Terminiting the Application.....");
			System.exit(0);
		}

	}

	private static void userOperations(String cNumber) {
		while (true) {
			System.out.println("User Operations.......");
			System.out.println(
					"1.Pay Bill\n2.Show Total Due Bill\n3.Show All Bill Details\n4.Search Bill\n5.Pay Monthly Bill\n6.Exit");
			System.out.println("Enter Your Choice (1-6) : ");
			int userChoice = sc.nextInt();
			String billNumber;
			switch (userChoice) {
			case 1:
				/*
				 * System.out.println("Enter Bill Number : "); String billNumber = sc.next();
				 * es.payBill(cNumber, billNumber);
				 */
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
					"1.Add Customer\n2.View All Customers\n3.Search Customer\n4.Bill Generation\n5.Search Bill\n6.EXIT");
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
				System.out.println("Enter Bill Number :");
				String billNumber = sc.next().toUpperCase();
				es.searchBill(billNumber);
				break;
			case 6:
				System.out.println("Exiting Manager Mode......");
				Home.main(null);
				break;
			}
		}
	}

}
