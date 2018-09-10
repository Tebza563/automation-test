import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class TestCaseTwo{

    private static final String SAMPLE_XLSX_FILE_PATH = "DataExcel.xls";
    private static final String DRIVER_PATH = "chromedriver.exe";
    private static final String SITE_URL = "http://www.way2automation.com/angularjs-protractor/webtables/";
    private static final String DRIVE_SYS_NAME = "webdriver.chrome.driver";

    WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception{
        //setup chrome driver
        ClassLoader classLoader = getClass().getClassLoader();
        String exePath =  classLoader.getResource(DRIVER_PATH).getPath();
        System.setProperty(DRIVE_SYS_NAME, exePath);
        driver = new ChromeDriver();
        // open chrome and nav to url
        driver.get(SITE_URL);
        // maximize window
        driver.manage().window().maximize();
    }


    List<String> uniqueUserNames = Lists.newArrayList();

    @Test
    public void AddUser() throws Exception{
        //load excel file
        ClassLoader classLoader = getClass().getClassLoader();
        String exePath =  classLoader.getResource(SAMPLE_XLSX_FILE_PATH).getPath();
        Workbook workbook = WorkbookFactory.create(new File(exePath));

        //get the first sheet
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter dataFormatter = new DataFormatter();

        int cellNumber = 0;

        //iterate on each row
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if(cellNumber != 0) {

                cellNumber = 1;

                //click on add user
                driver.findElement(By.xpath("/html/body/table/thead/tr[2]/td/button/i")).click();

                //wait 2 seconds for page to load
                Thread.sleep(2000);

                //iterate on each cell
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);

                    //check for duplicates

                    if(!uniqueUserNames.contains(cellValue)) {
                        if (cellNumber == 1) {
                            //set firstName
                            setValue(cellValue, "/html/body/div[3]/div[2]/form/table/tbody/tr[1]/td[2]/input");
                        } else if (cellNumber == 2) {
                            //set lastNAme
                            setValue(cellValue, "/html/body/div[3]/div[2]/form/table/tbody/tr[2]/td[2]/input");
                        }
                        else if (cellNumber == 3) {
                            //set userName
                            uniqueUserNames.add(cellValue);
                            setValue(cellValue, "/html/body/div[3]/div[2]/form/table/tbody/tr[3]/td[2]/input");
                        } else if (cellNumber == 4) {
                            //set customer
                            setValue(cellValue, "/html/body/div[3]/div[2]/form/table/tbody/tr[4]/td[2]/input");
                        } else if (cellNumber == 5) {
                            //set Customer
                            if(cellValue.equalsIgnoreCase("Company AAA")) {
                                driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[5]/td[2]/label[1]/input")).click();
                            } else if(cellValue.equalsIgnoreCase("Company BBB")) {
                                driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[5]/td[2]/label[2]/input")).click();
                            }
                        } else if (cellNumber == 6) {
                            //set Role
                            driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[6]/td[2]/select")).click();
                            if(cellValue.equalsIgnoreCase("admin")) {
                                driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[6]/td[2]/select/option[4]")).click();
                            } else if(cellValue.equalsIgnoreCase("customer")){
                                driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[6]/td[2]/select/option[3]")).click();
                            }

                        } else if (cellNumber == 7) {
                            //set email
                            setValue(cellValue, "/html/body/div[3]/div[2]/form/table/tbody/tr[7]/td[2]/input");
                        } else if (cellNumber == 8) {
                            //set cell phone
                            setValue(cellValue, "/html/body/div[3]/div[2]/form/table/tbody/tr[8]/td[2]/input");
                        }
                    } else {
                    }

                    //move to next cell
                    cellNumber = cellNumber + 1;
                }
                driver.findElement(By.xpath("/html/body/div[3]/div[3]/button[2]")).click();
            }
            else {
                //skip headers
                cellNumber = cellNumber + 1;
            }
        }
    }

    private void setValue(String cellValue, String s) {
        driver.findElement(By.xpath(s)).clear();
        driver.findElement(By.xpath(s)).sendKeys(cellValue);
    }
}

