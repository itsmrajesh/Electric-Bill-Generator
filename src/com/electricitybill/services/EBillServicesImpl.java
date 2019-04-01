package com.electricitybill.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.electricitybill.dao.EBillDao;
import com.electricitybill.dao.EBillDaoImpl;
import com.electricitybill.domain.Bill;
import com.electricitybill.domain.Customer;
import com.electricitybill.idgenerator.IDGenerator;

public class EBillServicesImpl implements EBillServices {
	private List<Customer> cList = new ArrayList<>();
	private Scanner sc = new Scanner(System.in);
	private EBillDao ebilldao = new EBillDaoImpl();
	private IDGenerator idg = new IDGenerator();
	{
		loadCustomersData();
		display();
	}

	@Override
	public List<Customer> searchCustomer(String str) {
		List<Customer> searchList = new ArrayList<>();
		for (Customer c : cList) {
			if (c.getCNumber().equals(str) || c.getCId().equals(str) || c.getCName().equals(str)) {
				searchList.add(c);
			}
		}
		return searchList;
	}

	public void display() {
		for (Customer l : cList) {
			System.out.println("Name " + l.getCName());
		}
	}

	@Override
	public void generateBill(String cNumber) {
		System.out.println("Bill Generation....");
		String billID = idg.getBillID();
		LocalDate date = LocalDate.now();
		System.out.println("date is " + date);
		double unitRate = 0;
		double billAmount;
		int unitsConsumed;
		int totalUnits = ebilldao.getTotalUnits(cNumber);
		if (totalUnits < 500) {
			unitRate = 4.45;
		} else {
			unitRate = 5.45;
		}
		System.out.println("Previous units " + totalUnits);
		int currentUnits = 0;
		while (true) {
			System.out.println("Enter units Consumed..");
			currentUnits = sc.nextInt();
			if (currentUnits < totalUnits) {
				System.out.println("Invalid units entry");
			} else {
				ebilldao.updateUnitsConsumed(cNumber, currentUnits);
				break;
			}
		}
		if (totalUnits == 0) {
			unitsConsumed = currentUnits;
		} else {
			unitsConsumed = currentUnits - totalUnits;
		}
		billAmount = unitsConsumed * unitRate;
		Bill bill = Bill.builder().cNumber(cNumber).bNumber(billID).units(unitsConsumed).billAmount(billAmount)
				.date(date).build();
		ebilldao.addBill(bill);

	}

	public void loadCustomersData() {
		System.out.println("Loading all Customers data..");
		cList = ebilldao.getAllCustomers();
	}

}
