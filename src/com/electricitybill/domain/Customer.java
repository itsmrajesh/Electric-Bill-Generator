package com.electricitybill.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Customer {
	private String cName;
	private String cNumber; // Auto generated
	private String cId; //Aadhar Number
	private String cAddress;
	private String mobile;
}
