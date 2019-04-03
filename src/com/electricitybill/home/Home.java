package com.electricitybill.home;

import java.util.Scanner;

import com.electricitybill.dao.*;
import com.electricitybill.domain.Customer;
import com.electricitybill.idgenerator.IDGenerator;
import com.electricitybill.services.EBillServices;
import com.electricitybill.services.EBillServicesImpl;

public class Home {
	private static EBillDao ebilldao = new EBillDaoImpl();
	private static IDGenerator ido = new IDGenerator();
	private static EBillServices es = EBillServicesImpl.getInstance();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		String id = ido.getID();
		Customer c = Customer.builder().cNumber(id).cName("MV MANJU").cId("748541415707").mobile("7892660266")
				.cAddress("HYD").build();
		// es.addCustomer();
		// es.generateBill("BESCOM101");

		System.out.println("Welcome Electricity Bill Generation Application");
		System.out.println("1.Manager\n2.Customer");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Manager Section");
			System.out.println("Enter user name");
			String uName = sc.next();
			System.out.println("Enter Password");
			String password = sc.next();
			if (ebilldao.validateAdminDao(uName, password)) {
				System.out.println("1.Add Customer\n2.View All Customers\n3.Search Customer\n 4.Search Bill");
				int adminChoice = sc.nextInt();
				

			}
			break;

		}

	}

}
