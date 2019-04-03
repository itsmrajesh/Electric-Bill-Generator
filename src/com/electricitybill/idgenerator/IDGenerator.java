package com.electricitybill.idgenerator;

import com.electricitybill.dao.EBillDao;
import com.electricitybill.dao.EBillDaoImpl;

public class IDGenerator {
	private EBillDao ebilldao = new EBillDaoImpl();
	private static String prefix = "BESCOM";
	private static int count = 100;

	private static String billPrefix = "BLRBES";
	private static String sufix = "KA";

	public String getID() {
		int c = ebilldao.getCustomersCount();
		int totalCount = count;
		if (c == 0) {
			return prefix + (totalCount+1);
		} else {
			return prefix + (totalCount + c + 1);
		}

	}

	public String getBillID() {
		int c = ebilldao.getBillCount();
		int totalCount = count;
		if (c == 0) {
			return billPrefix + (totalCount+1) + sufix;
		} else {
			return billPrefix + (totalCount + c+1) + sufix;
		}
	}
}