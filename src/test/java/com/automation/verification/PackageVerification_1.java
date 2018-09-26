package com.automation.verification;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.automation.indexpage.PackageIndexpage;
import com.automation.init.AbstractPage;
import com.automation.utility.Common;

public class PackageVerification_1 extends AbstractPage {

	public PackageVerification_1(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@FindBy (xpath=".//span[@id='user_fullname']")
	WebElement XverifyLogin;
	
	public boolean verifyLoginP() {
		
		Common.waitForElement(driver, XverifyLogin);
		if(XverifyLogin.getText().equalsIgnoreCase("Welcome Sydney Test"))
		{
			return true;
			
		}	
		
		return false;
	}
	
	

}
