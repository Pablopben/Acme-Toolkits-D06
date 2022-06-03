package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumCreateTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final String code, final String title, final String description, final String startDate,
			final String endDate, final String budget, final String info) {
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List my items");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);

		super.clickOnButton("Create chimpum");

		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Inventor", "List the chimpums of my items");
		super.checkListingExists();
		super.sortListing(1, "desc");

		super.checkColumnHasValue(0, 2, title);
		super.checkColumnHasValue(0, 3, startDate);

		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("info", info);

		super.clickOnSubmit("Delete");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final String title, final String description, final String startDate, final String endDate,
			final String budget, final String info) {

		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List my items");
		super.checkListingExists();
		super.sortListing(1, "desc");
		super.clickOnListingRecord(0);

		super.clickOnButton("Create chimpum");

		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	@Order(30)
	public void hackingTest() {
		// As the framework doesn't support this hacking feature we will have to perform
		// this manually

		// 1) Log in as patron1 (username=patron1, password = patron1)
		// 2) Navigate to the URL to create a chimpum
		// 3) Check that a panic happens

		// 4) Log out
		// 5) Navigate to the URL to create a chimpum
		// 6) Check that a panic happens

		// 7) Log in as inventor1 (username=inventor1, password=inventor1)
		// 8) Go to the URL of an item of another inventor
		// 9) Go to the URL of creation of a chimpum for this item
		// 10) Check that a panic happens

		// TODO change depending on what you get in control check

		// 11) Go to the show form of an item that is a COMPONENT
		// 12) Go to the URL of creation of a chimpum for this item
		// 14) Check that a panic happens
	}
}
