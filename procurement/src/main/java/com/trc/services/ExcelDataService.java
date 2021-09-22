package com.trc.services;

import java.util.List;

import com.trc.entities.PhoneAddress;

public interface ExcelDataService 
{
	List<PhoneAddress> getExcelDataAsList();
	
	int saveExcelData(List<PhoneAddress> invoices);
}
