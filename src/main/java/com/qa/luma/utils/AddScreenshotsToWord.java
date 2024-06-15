package com.qa.luma.utils;

import com.qa.luma.base.BaseInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.testng.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddScreenshotsToWord extends BaseInitializer {
    public static Logger log = LogManager.getLogger(AddScreenshotsToWord.class.getName());
    private static String currentDate = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
    public static String wordPath = setAutomationFilesPath() + "AutomationFiles\\Results\\ExecutionTestResults\\WordScreenShots\\";

    /**
     * This method will add screen shots to word document
     *
     * @param wordDocName    Word document name
     * @param screenshotName Screenshot name
     * @param pageName       Web page name
     */
    public static void appendPictureToWord(String wordDocName, String screenshotName, String pageName) {
        String testClassName = Reporter.getCurrentTestResult().getTestClass().getRealClass().getSimpleName();
        String[] arr = testClassName.split("_");
        testClassName = arr[0];

        wordDocName = wordPath + currentDate + "\\" + testClassName + "\\" + wordDocName + ".docx";

        createTemplateIfDoesNotExists(wordDocName);

        //open existing file and append after last paragraph
        try (XWPFDocument doc = new XWPFDocument(OPCPackage.open(wordDocName))) {
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            XWPFParagraph paragraph = paragraphs.get(paragraphs.size() - 1);
            XWPFRun runText = paragraph.createRun();
            runText.setText(pageName);
            runText.addBreak();

            File image = new File(screenshotName);

            //attach image file.
            try (FileInputStream inputStream = new FileInputStream(image)) {
                runText.addPicture(inputStream,
                        XWPFDocument.PICTURE_TYPE_PNG, image.getName(), Units.toEMU(500), Units.toEMU(500));
            } catch (Exception ex) {
                log.error("Exception occurred while attaching screenshot to word document  " + ex.getMessage());
            }

            //write file to memory
            try (FileOutputStream out = new FileOutputStream(wordDocName, true)) {
                doc.write(out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            log.error("Exception occurred while add screenshot to word document  " + ex.getMessage());
        }
    }

    /**
     * This method will check the word document is exist or not.
     * It will create folder structure with file if it does not contains.
     *
     * @param wordDocName Word document name
     */
    private static void createTemplateIfDoesNotExists(String wordDocName) {
        String testClassName = Reporter.getCurrentTestResult().getTestClass().getRealClass().getSimpleName();
        String[] arr = testClassName.split("_");
        testClassName = arr[0];
        String dirPath = wordPath + currentDate + "\\" + testClassName;
        try {
            File file = new File(dirPath);
            file.mkdirs();
        } catch (Exception e) {
            log.error("Exception occurred while creating the folder " + e.getMessage());
        }

        try (XWPFDocument doc = new XWPFDocument(OPCPackage.open(wordDocName))) {
        } catch (Exception e) {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph p = document.createParagraph();
            XWPFRun r = p.createRun();
            r.setText("The following screenshots captured while executing Test class : " + testClassName +
                    " And Test Method Name:  " + Reporter.getCurrentTestResult().getMethod().getMethodName());
            try (FileOutputStream out = new FileOutputStream(wordDocName)) {
                document.write(out);
                document.close();
            } catch (Exception ex) {
                log.error("Exception occurred while creating word document  " + ex.getMessage());
            }
            r.addBreak();
        }
    }
}
