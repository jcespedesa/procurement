package com.trc.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trc.entities.PhoneAddress;
import com.trc.repositories.PhoneAddrRepository;
import com.trc.services.ExcelDataService;

@Service
public class ExcelDataServiceImpl implements ExcelDataService
{

	@Value("${app.upload.file:${user.home}}")
	public String EXCEL_FILE_PATH;

	@Autowired
	PhoneAddrRepository repo;

	Workbook workbook;
	
	
	@Override
	public List<PhoneAddress> getExcelDataAsList() 
	{

		List<String> list=new ArrayList<String>();

		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter=new DataFormatter();
		
		// Create the Workbook
//		try {
			
	//		workbook=WorkbookFactory.create(new File(EXCEL_FILE_PATH));
	//	} catch (EncryptedDocumentException | IOException e) {
	//		e.printStackTrace();
	//	}

		// Retrieving the number of sheets in the Workbook
		System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");

		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// Getting number of columns in the Sheet
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		System.out.println("-------Sheet has '"+noOfColumns+"' columns------");

		// Using for-each loop to iterate over the rows and columns
		for (Row row : sheet) {
			for (Cell cell : row) {
				String cellValue=dataFormatter.formatCellValue(cell);
				list.add(cellValue);
			}
		}

		// filling excel data and creating list as List<Invoice>
		List<PhoneAddress> invList=createList(list, noOfColumns);

		// Closing the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invList;
	}

	private List<PhoneAddress> createList(List<String> excelData, int noOfColumns) {

		ArrayList<PhoneAddress> invList=new ArrayList<PhoneAddress>();

		int i=noOfColumns;
		do 
		{
			PhoneAddress inv=new PhoneAddress();

			inv.setPhoneNumber(excelData.get(i));
			inv.setStreetNum(excelData.get(i+1));
			inv.setSuiteNum(excelData.get(i+2));
			inv.setStreetName(excelData.get(i+3));
			inv.setCity(excelData.get(i+4));
			inv.setState(excelData.get(i+5));
			inv.setZip(excelData.get(i+6));
			inv.setVonageKey(excelData.get(i+7));

			invList.add(inv);
			i=i+(noOfColumns);

		} while (i < excelData.size());
		return invList;
	}

	
	@Override
	public int saveExcelData(List<PhoneAddress> phonesList) 
	{
		phonesList=repo.saveAll(phonesList);
		return phonesList.size();
	}
}
