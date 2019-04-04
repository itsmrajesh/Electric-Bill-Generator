package com.electricitybill.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Bill {
	private String cNumber;
	private String bNumber;
	private int units;
	private String billDate;
	private String dueDate;
	private String billPaidDate;
	private double billAmount;
	private String status;
}
