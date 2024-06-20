package com.qa.mmt.data.excelData;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("BookingData")
public class BookingData {

        @ExcelRow
        private int rowIndex;
        @ExcelCellName("TestCase Name")
        private String testCaseName;
        @ExcelCellName("Run Test Execution")
        private String flagRunTestExecution;

        @ExcelCellName("From Place")
        private String fromPlace;
        @ExcelCellName("To Place")
        private String toPlace;
        @ExcelCellName("Travel Date Day")
        private String travelDateDay;
        @ExcelCellName("Travel Date Month")
        private String travelDateMonth;
        @ExcelCellName("Train Class")
        private String trainClass;




}
