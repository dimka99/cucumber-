package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import gherkin.formatter.model.Row;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CommonScripts {
	
	public static WebDriver driver;
	static WebDriverWait wait;
	public static String WritefilePath;



	public static WebDriver openUrl() {
		String browser = readProperty("browser");
		int browserno=0;
		if (browser.contains("Mozilla Firefox"))
		{
			browserno=1;
		}
		else if (browser.contains("Google Chrome"))
		{
			browserno=2;
		}
		else if (browser.contains("Internet Explorer"))
		{
			browserno=3;
		}
		String environment = readProperty("test.environment");
		String baseUrl = GetURL(environment);
		String chromeDriverPath = readProperty("chrome.driver.path");
		String ieDriverPath = readProperty("ie.driver.path");
		String MozillaDriverPath = readProperty("mozilla.driver.path");
		String downloadPath = readProperty("file.download.path");
		switch (browserno) {
		case 1:
			System.setProperty("webdriver.gecko.driver", MozillaDriverPath);
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
	        capabilities.setCapability("marionette", true);
	        driver = new FirefoxDriver();
			break;
		case 2:
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			driver = new ChromeDriver();
			break;
		case 3:
			System.setProperty("webdriver.ie.driver", ieDriverPath);
			driver = new InternetExplorerDriver();
			break;

		default:
			System.err.println("Browser not recognized");
			break;
		}
		long startTime = System.currentTimeMillis();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.MINUTES);
		driver.get(baseUrl);
		return driver;

	}

	// /-----------------------READ PROPERTY FUNCTION
	public static String readProperty(String property) {
		Properties properties = new Properties();
		try {

			InputStream input = new FileInputStream(
					"Config/testsettings.properties");
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (properties.getProperty(property));
	}
	
	// ------GET URL
	public static String GetURL(String environment) {
		String baseUrl = null;
		if (environment.contains("Test")) {
			baseUrl = readProperty("test.url");
		} else if (environment.contains("Development")) {
			baseUrl = readProperty("dev.url");
		} else if (environment.contains("Production")) {
			baseUrl = readProperty("live.url");
		}
		
		return baseUrl;
	}

	// ------GET URL
	public static WebDriver DoLogin()  {
		driver=Utils.CommonScripts.openUrl();
		String UName=readProperty("user.name");
		String Pwd=readProperty("password");
		PageObjects.LoginPage.txt_username(driver).sendKeys(UName);
		PageObjects.LoginPage.txt_password(driver).sendKeys(Pwd);
		PageObjects.LoginPage.btn_login(driver).click();
		
		return driver;
	}

	// Random Text Generation
	public static String RandomText(int len) {
		String text = "";

		String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < len; i++)
			text += charset.charAt((int) Math.floor(Math.random()
					* charset.length()));

		return text;
	}
	
	// Random Small Text Generation
	public static String RandomSmallText(int len) {
		String text = "";

		String charset = "abcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < len; i++)
			text += charset.charAt((int) Math.floor(Math.random()
					* charset.length()));

		return text;
	}
	
	// Generate Date and Time
	public static String GetDateTime() {
		String text = "";

		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm-ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   text=dtf.format(now);  
		return text;
	}
	

	public static ArrayList<String> GetServerList() {
		String filePath = readProperty("virtual.server.dbpath");
		File inputWorkbook = new File(filePath);
		 Workbook w = null;
		 ArrayList<String> ServerList =  new ArrayList<String>();
	        try {
	            try {
					w = Workbook.getWorkbook(inputWorkbook);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            Sheet sheet = w.getSheet(0);	           
	                for (int i = 0; i < sheet.getRows()-1; i++) {
	                	//System.out.println(sheet.getCell(0, i).getContents());
	                	Cell[] cells=sheet.getRow(i);
	                	if (cells.length>=2)
	                	{
	                		 Cell cell = sheet.getCell(0, i);
	 	                    CellType type = cell.getType();
	 	                    Cell cell1 = sheet.getCell(1, i);
	 	                    if (type == CellType.LABEL) {
	 	                    	
	 	                    ServerList.add(cell.getContents().toString()+";"+cell1.getContents().toString());
	                	}
	                	}
	                   
	 	                    else
	 	                    {
	 	                    	Cell cellss = sheet.getCell(0, i);
	 		                    CellType types = cellss.getType();
	 		                    if (types == CellType.LABEL) {
	 		                    	
	 		                    	ServerList.add(cellss.getContents().toString()+";"+" ");
	 		                    }
	 	                    }
	                }
	            
	        } catch (BiffException e) {
	           // e.printStackTrace();
	        }
	        return ServerList;
	      
	}
	
	public static void WriteServerList(ArrayList ServerList) {
		try {
			String filePath = readProperty("virtual.server.dbpath");
			WritableWorkbook wb = Workbook.createWorkbook(new File(filePath));
			WritableSheet ws = wb.createSheet("ServerList",1);
			{
		        for (int i=0;i<=ServerList.size()-1;i++)
		        {
		        	
		        	String Current=ServerList.get(i).toString();
		        	System.out.println(Current);
		        	if (Current.contains(";"))
		        	{
		        		String AddCheck []=Current.split(";");
		        		Label label = new Label(0,i,AddCheck [0]);
		        		Label label1 = new Label(1,i,AddCheck [1]);
						ws.addCell(label); 
						ws.addCell(label1); 
		        	}
		        	else
		        	{
		        		Label label2 = new Label(0,i,Current);
						ws.addCell(label2);
		        	}
		        		        			        	
		        }	
			
			
			}
			wb.write();
			wb.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
