package com.automation.indexpage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.automation.index.PackageIndex;
import com.automation.init.AbstractPage;
import com.automation.utility.Common;
import com.automation.verification.PackageVerification;
import com.automation.verification.PackageVerification_1;

public class PackageIndexpage_1 extends AbstractPage {

	public static String attachment_name;
	public static String Mobile = "+61444444444";
	public static String mailURL = "https://www.mailinator.com";
	public static String bmail;
	public static String rname;

	public PackageIndexpage_1(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = ".//*[@id='login']//input[@name='username']")
	WebElement Xusername;

	public void enterUsernameP(String Username) {
		Common.waitForElement(driver, Xusername);
		Common.type(Xusername, Username);
		Common.log("Username Entered is" + Username);
	}

	@FindBy(xpath = ".//*[@id='login']//input[@name='password']")
	WebElement XPassword;

	public void enterPasswordP(String Password) {
		Common.type(XPassword, Password);
		Common.log("Password entered is " + Password);
	}

	@FindBy(xpath = ".//form[@id='login']//button[@title='login']")
	WebElement XLoginbutton;

	public PackageVerification_1 clickLoginP() {
		// TODO Auto-generated method stub
		Common.clickOn(driver, XLoginbutton);
		Common.pause(2);
		return new PackageVerification_1(driver);

	}

	@FindBy(xpath = ".//*[@id='workflows']")
	WebElement Xworkflows;

	public void select_workflows() {
		// TODO Auto-generated method stub

		Common.selectFromComboByIndex(Xworkflows, 1);
	}

	@FindBy(xpath = ".//button[@id='create_package']")
	WebElement XBtncreate_package;

	public PackageVerification_1 ClickCreate() {

		Common.clickOn(driver, XBtncreate_package);
		return new PackageVerification_1(driver);

	}

	@FindBy(xpath = ".//form[@id='createPackage']//input[@id='packageReference']")
	WebElement X_txt_referencename;

	public void enterReferenceC() {

		rname = "Demo Package_" + Common.generateRandomChars(4);
		Common.type(X_txt_referencename, rname);

	}

	@FindBy(xpath = ".//input[contains(@class,'firstName')]")
	WebElement X_borrower_firstName;

	public void enterFirstName() {

		String fname = Common.generateRandomChars(6);
		Common.type(X_borrower_firstName, fname);
	}

	@FindBy(xpath = ".//input[contains(@class,'lastName')]")
	WebElement X_borrower_lastName;

	public void enterLastName() {

		String lname = Common.generateRandomChars(6);
		Common.type(X_borrower_lastName, lname);
	}

	@FindBy(xpath = ".//input[contains(@class,'email')]")
	WebElement X_borrower_mail;

	public void enterEmail() {
		bmail = Common.generateRandomChars(6) + "@" + "mailinator.com";
		Common.type(X_borrower_mail, bmail);
	}

	@FindBy(xpath = ".//input[contains(@class,'phone')]")
	WebElement Xphone;

	public void enterMobile() {

		Common.type(Xphone, Mobile);

	}

	@FindBy(xpath = ".//input[@title='toggle to include/exclude attachment'][not(contains(@class,'wafrAttachmentRequired' ))]")
	List<WebElement> X_chckbox_btn_attachments;

	public void clickAttachmentsCheckbox() {

		for (int i = 0; i < X_chckbox_btn_attachments.size(); i++) {
			Common.checkChkBox(X_chckbox_btn_attachments.get(i));
			attachment_name = X_chckbox_btn_attachments.get(i).getText();
			Common.log("Attachment Selectd is -->" + attachment_name);
		}

	}

	@FindBy(xpath = ".//input[@title='toggle to include/exclude attachment'][(contains(@class,'wafrAttachmentRequired' ))]")
	List<WebElement> X_chckbox_btn_required;

	public void clickrequiredChckbox() {

		for (int i = 0; i < X_chckbox_btn_required.size(); i++) {

			if (attachment_name.equals(X_chckbox_btn_attachments.get(1))) {
				Common.checkChkBox(X_chckbox_btn_required.get(i));

			}

		}
	}

	@FindBy(xpath = ".//button[@id='createpkgid']")
	WebElement X_btncreatepack;

	public PackageVerification_1 ClickCreatePackage() {

		Common.clickOn(driver, X_btncreatepack);
		return new PackageVerification_1(driver);

	}

	@FindBy(xpath = ".//span[@id='fileupload']//span[text()='Cloud Services Agreement']/../input")
	WebElement X_chkBox_cloud;

	public PackageVerification_1 ClickServiceCheckBox() {

		Common.waitForElement(driver, X_chkBox_cloud);
		Common.checkChkBox(X_chkBox_cloud);
		return new PackageVerification_1(driver);

	}

	@FindBy(xpath = ".//input[@type='file']")
	WebElement X_upload_file;

	public void uploadServiceAgreement() {

		Common.waitForElement(driver, X_upload_file);

		File f = new File("/eSign/Resources/Cloud Hosting Agreement.pdf");
		X_upload_file.sendKeys(f.getAbsolutePath());

	}

	@FindBy(xpath = ".//*[@id='formoverlay']")
	WebElement X_createsend;

	public void ClickSendPackage() {

		Common.clickOn(driver, X_createsend);

	}

	@FindBy(xpath = ".//input[@id='inboxfield']")
	WebElement X_input_txt;

	@FindBy(xpath = "//span[@class='input-group-btn']")
	WebElement X_btn_go;

	@FindBy(xpath = ".//*[@id='inbox_field']")
	WebElement X_inbox_txt;

	public void Checkmailreceived() {

		Common.switchToNewtabWithUrl(driver, mailURL);
		Common.type(X_input_txt, bmail);
		Common.clickOn(driver, X_btn_go);
		Common.waitForElement(driver, X_inbox_txt);
	}

	/*
	 * @FindBy (xpath=
	 * ".//div[@id='msgpane']//div[contains(text(),'Ref 24Aug8')]") WebElement
	 */
	public void CheckmailSub() {

	}

}
