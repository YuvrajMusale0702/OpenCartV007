package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{

	@Test(groups= {"Regression","Master"})
	public void verify_account_registration()
	{
		try
		{
		logger.info("***Start TC001_AccountRegistrationTest *** ");
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickRegister();
		logger.info("Clicked on Reg link..");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details..");
		//Randomly created data
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLasttName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");
	    regpage.setTelephone(randomNumber());
	    
	    //Password -aplphanumeric+"@"
	    String password=aplhaNumeric();
	    regpage.setPassword(password);
	    regpage.setConfirmpassword(password);
	    
	    regpage.setPrivacyPolicy();
	    regpage.clickContinue();
	    
	    logger.info("Validating expected message..");
	    String confmsg=regpage.getConfirmationMsg();
	    if(confmsg.equals("Your Account Has Been Created!"))
	    {
	    	Assert.assertTrue(true);
	    }
	    else
	    {
	    	logger.error("Test Failed");
	    	logger.debug("Debug logs.. ");
	    	Assert.assertTrue(false);
	    }
	}
	   catch(Exception e)
	     {
	    	 Assert.fail();
	     }
	
	     logger.info("*** Finished TC001_AccountRegistrationTest****");
	 
	}
}