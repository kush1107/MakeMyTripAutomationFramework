package com.qa.luma.data.excelData;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("LoginUserData")
public class LoginUserData {

    @ExcelRow
    private int rowIndex;
    @ExcelCellName("TestCase Name")
    private String testCaseName;
    @ExcelCellName("Run Test Execution")
    private String flagRunTestExecution;

    @ExcelCellName("Username")
    private String username;

    @ExcelCellName("Password")
    private String password;
}
