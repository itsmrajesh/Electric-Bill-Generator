package com.electricitybill.dao;

import com.electricitybill.dbutil.DBUtil;
import com.electricitybill.domain.Bill;
import com.electricitybill.domain.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class EBillDaoImpl implements EBillDao {
	private DBUtil dbutil = DBUtil.obj;
	private Connection con;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;

	@Override
	public boolean addCustomer(Customer c) {
		String addUserQuery = "INSERT INTO CUSTOMERS VALUES (?,?,?,?,?)";
		try {
			con = dbutil.getConnection();
			pst = con.prepareStatement(addUserQuery);
			pst.setString(1, c.getCNumber());
			pst.setString(2, c.getCName());
			pst.setString(3, c.getCId());
			pst.setString(4, c.getMobile());
			pst.setString(5, c.getCAddress());
			int i = pst.executeUpdate();
			if (i == 1) {
				System.out.println("user Added Sucesfully with id : " + c.getCNumber());
				String cNumber = c.getCNumber();
				initUserBill(cNumber);
				return true;
			} else {
				System.out.println("Duplicate User not added with Aadhar Number : " + c.getCId());
			}

		} catch (Exception e) {
			e.getMessage();
		}

		return false;
	}

	private void initUserBill(String cNumber) {
		String initUserQuery = "INSERT INTO TOTALUNITS (CNUMBER) VALUES ( ? )";
		try {
			con = dbutil.getConnection();
			pst = con.prepareStatement(initUserQuery);
			pst.setString(1, cNumber);
			int i = pst.executeUpdate();
			if (i > 0) {
				System.out.println("Init Succesfull........");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCustomersCount() {
		String countQuery = "SELECT COUNT(CNUMBER) FROM CUSTOMERS";
		int count = 0;
		try {
			con = dbutil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(countQuery);
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Customer> getAllCustomers() {
		String viewQuery = "SELECT * FROM CUSTOMERS";
		List<Customer> list = new ArrayList<>();
		try {
			con = dbutil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(viewQuery);
			while (rs.next()) {
				list.add(Customer.builder().cId(rs.getString(1)).cName(rs.getString(2)).cId(rs.getString(3))
						.mobile(rs.getString(4)).cAddress(rs.getString(5)).build());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getBillCount() {
		String billCountQuery = "SELECT COUNT(BILLNUMBER) FROM BILL";
		int billCount = 0;
		try {
			con = dbutil.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(billCountQuery);
			while (rs.next()) {
				billCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billCount;
	}

	@Override
	public int getTotalUnits(String Cnumber) {
		String unitsConsumedQuery = "SELECT TOTALUNITS FROM TOTALUNITS WHERE CNUMBER = ?";
		int units = 0;
		try {
			con = dbutil.getConnection();
			pst = con.prepareStatement(unitsConsumedQuery);
			pst.setString(1, Cnumber);
			rs = pst.executeQuery();
			while (rs.next()) {
				units = rs.getInt("totalunits");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return units;
	}

	@Override
	public boolean updateUnitsConsumed(String Cnumber, int units) {
		String updateUnitsQuery = "UPDATE TOTALUNITS SET TOTALUNITS = ? WHERE CNUMBER = ? ";
		try {
			con = dbutil.getConnection();
			pst = con.prepareStatement(updateUnitsQuery);
			pst.setInt(1, units);
			pst.setString(2, Cnumber);
			int i = pst.executeUpdate();
			if (i > 0) {
				System.out.println("Total units updated....");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public boolean addBill(Bill b) {
		String addBillQuery = "INSERT INTO BILL (CNUMBER,BILLNUMBER,UNITS,BILLAMOUNT) VALUES (?,?,?,?)";
		try {
			con = dbutil.getConnection();
			pst = con.prepareCall(addBillQuery);
			pst.setString(1, b.getCNumber());
			pst.setString(2, b.getBNumber());
			pst.setInt(3, b.getUnits());
			// pst.setDate(4, (Date)b.getDate());
			pst.setDouble(4, b.getBillAmount());
			int i = pst.executeUpdate();
			if (i > 0) {
				System.out.println("Bill added successfully ....for : " + b.getBNumber());
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean validateAdmin(String uName, String password) {
		String checkQuery ="SELECT USERNAME FROM ADMIN WHERE USERNAME = ? and PASSWORD = ?";
		try {
			con = dbutil.getConnection();
			pst = con.prepareStatement(checkQuery);
			pst.setString(1, uName);
			pst.setString(2, password);
			rs=pst.executeQuery();
			if(rs.next()) {
				System.out.println("Welcome Admin....");
				return true;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Invalid login  ");
		return false;
	}

}
