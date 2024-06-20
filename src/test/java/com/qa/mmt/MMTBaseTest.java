package com.qa.mmt;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import com.qa.mmt.base.BaseInitializer;
import com.qa.mmt.data.excelData.BookingData;
import com.qa.mmt.pages.commonpages.CommonPage;
import com.qa.mmt.utils.ExcelUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MMTBaseTest extends BaseInitializer {

    ExcelUtils ex = new ExcelUtils();

    /**
     * For fetching excel data with module
     */
    public void setUpDataFromExcel() {
        ex.updateModuleTestSpecificRecordRunTestData(module);
        // Retrieve data for each sheet separately
        if (module.equalsIgnoreCase("TICKET_BOOKING")) {
            bData = getDataFromMMTTestSpecificRecordExcel(MMT_TestSpecificRecordData, "BookingData");
            logMessage("Excel data fetching started for module " + module);
        }
    }

    /**
     * For fetching excel data from sheets
     */
    public static List<?> getDataFromMMTTestSpecificRecordExcel(String path, String sheetName) {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetName(sheetName).build();
        if(sheetName.equalsIgnoreCase("BookingData")) {
            List<BookingData> details = Poiji.fromExcel(new File(path), BookingData.class, options);
            if (details.size() > 0) {
                List<BookingData> toBeExecuted = new ArrayList<>();
                for (BookingData i : details) {
                    if ("Yes".equalsIgnoreCase(i.getFlagRunTestExecution())) {
                        toBeExecuted.add(i);
                    }
                }
                return toBeExecuted;
            }
            return details;
        }
        return null;
    }


    /**
     * Validating messages with screenshots
     */
    public void validateAnyMessageWithScreenshots(String msg)
    {
        CommonPage cp = new CommonPage(driver);
        cp.getAnyMessage(msg);
        if (CommonPage.credentialMessage != null) {
            logAndReportWithScreenshot(CommonPage.credentialMessage);
        } else {
            logAndReportErrorWithScreenshot(msg + " - Message not displayed.");
        }
    }

    /**
     * Validating messages
     */
    public void validateAnyMessage(String msg)
    {
        CommonPage cp = new CommonPage(driver);
        cp.getAnyMessage(msg);
        if (CommonPage.credentialMessage != null) {
            logAndReport(CommonPage.credentialMessage);
        } else {
            logAndReportError(msg + " - Message not displayed.");
        }
    }
}
