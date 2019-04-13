package com.electricitybill.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.electricitybill.dao.EBillDao;
import com.electricitybill.dao.EBillDaoImpl;
import com.electricitybill.dbutil.DBUtil;
import com.electricitybill.domain.Bill;
import com.electricitybill.domain.Customer;
import com.electricitybill.idgenerator.IDGenerator;

public class EBillServicesImpl implements EBillServices {

	private EBillDao ebilldao = new EBillDaoImpl();
	private Scanner sc = new Scanner(System.in);
	private IDGenerator idg = new IDGenerator();

	private EBillServicesImpl() {

	}

	private static EBillServicesImpl obj;

	public static synchronized EBillServicesImpl getInstance() {
		if (obj == null) {
			return new EBillServicesImpl();
		}
		return obj;
	}

	private List<Customer> cList = new ArrayList<>();

	{
		loadCustomersData();
	}

	public void loadCustomersData() {
		// System.out.println("Loading Customers Data....");
		cList = ebilldao.getAllCustomers();
	}

	@Override
	public List<Customer> searchCustomer(String searchStr) {
		loadCustomersData();
		String str = searchStr.toLowerCase();
		List<Customer> searchList = new ArrayList<>();
		for (Customer c : cList) {
			if (c.getCNumber().equalsIgnoreCase(str) || c.getCId().equals(str)
					|| c.getCName().toLowerCase().equals(str)) {
				searchList.add(c);
			}
		}
		paintData(searchList);
		return searchList;
	}

	private void paintData(List<Customer> searchList) {
		if (searchList.isEmpty()) {
			System.out.println("No Customer found");
		} else {
			for (Customer customer : searchList) {
				paintCustomersData(customer);
			}
		}
	}

	private void paintCustomersData(Customer customer) {
		System.out.println("------------------------------");
		System.out.println("Name :-" + customer.getCName());
		System.out.println("Cnumber is :-" + customer.getCNumber());
		System.out.println("ID Number is :-" + customer.getCId());
		System.out.println("Mobile Number is :-" + customer.getMobile());
		System.out.println("Place is :- " + customer.getCAddress());
		System.out.println("-------------------------------");
	}

	@Override
	public void display() {
		loadCustomersData();
		System.out.println("----------------------------");
		System.out.println("Display All Customers Details....");
		for (Customer c : cList) {
			paintCustomersData(c);
		}

	}

	@Override
	public void generateBill(String cNumber) {
		System.out.println("Bill Generation....");
		String billID = idg.getBillID();
		LocalDate date = LocalDate.now();
		System.out.println("Today date is " + date);
		double unitRate = ebilldao.getUnitPrice();
		double billAmount;
		int unitsConsumed;
		int totalUnits = ebilldao.getTotalUnits(cNumber);
		if (totalUnits > 500) {
			unitRate += 1;
		}
		System.out.println("One Unit Price is :-" + unitRate);
		System.out.println("Previous units :- " + totalUnits);
		int currentUnits = 0;
		while (true) {
			System.out.println("Enter units Consumed..");
			currentUnits = sc.nextInt();
			if (currentUnits < totalUnits) {
				System.out.println("Invalid units entry");
			} else {
				break;
			}
		}
		if (totalUnits == 0) {
			unitsConsumed = currentUnits;
		} else {
			unitsConsumed = currentUnits - totalUnits;
		}
		billAmount = unitsConsumed * unitRate;
		Bill bill = Bill.builder().cNumber(cNumber).bNumber(billID).units(unitsConsumed).billAmount(billAmount).build();
		if (ebilldao.addBillDao(bill)) {
			ebilldao.updateUnitsConsumed(cNumber, currentUnits);
		}

	}

	@Override
	public boolean addCustomer() {
		String cNumber = idg.getID();
		System.out.println("Enter Customer Name :-");
		String cName = sc.nextLine();
		String cId;
		while (true) {
			System.out.println("Enter 12 Digit Aadhaar Number :- ");
			cId = sc.next();
			if (cId.length() != 12) {
				System.out.println("******Invalid  Aadhaar Number");
			} else if (cId.length() == 12) {
				break;
			}
		}
		System.out.println("Enter Address or city :-");
		String cAddress = sc.next();
		String mobile;
		while (true) {
			System.out.println("Enter Customer Phone Number :-");
			mobile = sc.next();
			if (mobile.length() != 10) {
				System.out.println("*****Invalid 10 Digit mobile number");
			} else if (mobile.length() == 10) {
				break;
			}
		}
		Customer customer = Customer.builder().cNumber(cNumber).cName(cName).cId(cId).mobile(mobile).cAddress(cAddress)
				.build();
		return ebilldao.addCustomerDao(customer);
	}

	@Override
	public void searchBill(String billNumber) {
		if (!billNumber.equals(" ")) {
			ebilldao.getBillByBillNumberDao(billNumber);
		} else {
			System.out.println("Invalid Bill" + " Number");
		}

	}

	public boolean isCustomerPresent(String cNumber) {
		loadCustomersData();
		for (Customer c : cList) {
			if (c.getCNumber().equalsIgnoreCase(cNumber)) {
				System.out.println("NAME :-" + c.getCName());
				System.out.println("Customer Number :-" + c.getCNumber());
				System.out.println("Mobile :-" + c.getMobile());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean payTotalBill(String cNumber) {
		double totalDueBill = ebilldao.getTotalBillDao(cNumber);
		System.out.println("Your total bill due is : " + totalDueBill);
		if (totalDueBill > 0.50) {
			System.out.println("Press 1 to Pay Bill  and Press 0 Exit :");
			int choice = sc.nextInt();
			if (choice == 1) {
				ebilldao.payTotalBill(cNumber);
			} else {
				System.out.println("Exiting Bill Paying Section....");
				return false;
			}
		}
		return false;
	}

	public boolean payBillByBillNumber(String cNumber, String billNumber) {
		if (!(cNumber.equals(" ") && billNumber.equals(" "))) {
			if (ebilldao.getBillByBillNumberDao(billNumber) && !(ebilldao.isBillPaid(billNumber))) {
				ebilldao.payBillByBillNumberDao(cNumber, billNumber);
				return true;
			}
		}
		return false;
	}
}
