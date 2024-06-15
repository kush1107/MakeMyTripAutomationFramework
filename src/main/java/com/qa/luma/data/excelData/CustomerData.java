package com.qa.luma.data.excelData;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("CustomerData")
public class CustomerData {

        @ExcelRow
        private int rowIndex;
        @ExcelCellName("TestCase Name")
        private String testCaseName;
        @ExcelCellName("Run Test Execution")
        private String flagRunTestExecution;

        @ExcelCellName("First Name")
        private String firstName;
        @ExcelCellName("LastName")
        private String lastName;
        @ExcelCellName("Email_ID")
        private String emailAddress;
        @ExcelCellName("Password")
        private String password;
        @ExcelCellName("Confirmation Password")
        private String confirmationPassword;



}
