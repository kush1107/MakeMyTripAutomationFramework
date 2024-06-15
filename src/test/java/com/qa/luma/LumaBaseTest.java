package com.qa.luma;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import com.qa.luma.base.BaseInitializer;
import com.qa.luma.data.excelData.CustomerData;
import com.qa.luma.data.excelData.LoginUserData;
import com.qa.luma.data.excelData.ProductData;
import com.qa.luma.pages.commonpages.CommonPage;
import com.qa.luma.utils.ExcelUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LumaBaseTest extends BaseInitializer {

    ExcelUtils ex = new ExcelUtils();

    /**
     * For fetching excel data with module
     */
    public void setUpDataFromExcel() {
        ex.updateModuleTestSpecificRecordRunTestData(module);
        // Retrieve data for each sheet separately
        if (module.equalsIgnoreCase("ECommerce")) {
            cData = getDataFromLumaTestSpecificRecordExcel(LUMA_TestSpecificRecordData, "CustomerData");
            lData = getDataFromLumaTestSpecificRecordExcel(LUMA_TestSpecificRecordData,"LoginUserData");
            pData = getDataFromLumaTestSpecificRecordExcel(LUMA_TestSpecificRecordData,"ProductData");
            logMessage("Excel data fetching started for module " + module);
        }
    }

    /**
     * For fetching excel data from sheets
     */
    public static List<?> getDataFromLumaTestSpecificRecordExcel(String path, String sheetName) {
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetName(sheetName).build();
        if(sheetName.equalsIgnoreCase("CustomerData")) {
            List<CustomerData> details = Poiji.fromExcel(new File(path), CustomerData.class, options);
            if (details.size() > 0) {
                List<CustomerData> toBeExecuted = new ArrayList<>();
                for (CustomerData i : details) {
                    if ("Yes".equalsIgnoreCase(i.getFlagRunTestExecution())) {
                        toBeExecuted.add(i);
                    }
                }
                return toBeExecuted;
            }
            return details;
        } else if(sheetName.equalsIgnoreCase("LoginUserData")) {
            List<LoginUserData> details = Poiji.fromExcel(new File(path), LoginUserData.class, options);
            if (details.size() > 0) {
                List<LoginUserData> toBeExecuted = new ArrayList<>();
                for (LoginUserData i : details) {
                    if ("Yes".equalsIgnoreCase(i.getFlagRunTestExecution())) {
                        toBeExecuted.add(i);
                    }
                }
                return toBeExecuted;
            }
            return details;
        } else if(sheetName.equalsIgnoreCase("ProductData")) {
            List<ProductData> details = Poiji.fromExcel(new File(path), ProductData.class, options);
            if (details.size() > 0) {
                List<ProductData> toBeExecuted = new ArrayList<>();
                for (ProductData i : details) {
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
