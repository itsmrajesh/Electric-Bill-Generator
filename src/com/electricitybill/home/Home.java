package com.electricitybill.home;

import com.electricitybill.dao.*;
import com.electricitybill.domain.Customer;
import com.electricitybill.idgenerator.IDGenerator;
import com.electricitybill.services.EBillServices;
import com.electricitybill.services.EBillServicesImpl;

public class Home {
	private static EBillDao ebill = new EBillDaoImpl();
	private static IDGenerator ido = new IDGenerator();
	private static EBillServices es = new EBillServicesImpl();
	public static void main(String[] args) {
		String id = ido.getID();
		Customer c = Customer.builder().cNumber(id).cName("MAJILI").cId("748541415708").mobile("7892660266")
				.cAddress("HYD").build();
		//System.out.println(ebill.addUser(c));
		es.generateBill("BESCOM103");
		
	}

}
