package org.openmrs.module.spa.helpers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openmrs.test.BaseContextMockTest;

@RunWith(MockitoJUnitRunner.class)

public class VerifyUrlTest extends BaseContextMockTest {

	VerifyUrl verifyUrlInit = new VerifyUrl();
	VerifyUrl verifyUrl = Mockito.spy(verifyUrlInit);

	// PRIVATE Mocks
	private String mockUrls() {
		return Mockito.doReturn(
				"^(htt(p|ps)://)?(www.)?A?a?mpath.com(/d[0-9]/[a-zA-Z]*)?,www.kimosabe.com,//ampath-poc-test.fra1.digitaloceanspaces.com/import-map.json")
				.when(verifyUrl).getWhitelistUrls();
	}

	@Test
	public void testGetWhitelistUrls() throws Exception {

		mockUrls();
		final String actual = verifyUrl.getWhitelistUrls();
		final String expected = "^(htt(p|ps)://)?(www.)?A?a?mpath.com(/d[0-9]/[a-zA-Z]*)?,www.kimosabe.com,//ampath-poc-test.fra1.digitaloceanspaces.com/import-map.json";
		Assert.assertEquals(expected, actual);
	}

	// //////////////////////////////////////////
	@Test
	public void testRegexTest() throws Exception {
		mockUrls();
		final boolean actual = verifyUrl.regexTest("www.ampath.com");
		Assert.assertTrue(actual);
	}

	@Test
	public void testStringTest() throws Exception {
		mockUrls();
		final boolean actual = verifyUrl.stringTest("www.kimosabi.com");
		Assert.assertEquals(true, actual);
	}

	// //////////////////////////////////////////////////
	@Test
	public void testGetStringUrls() throws Exception {
		mockUrls();
		int actual = verifyUrl.getStringUrls().length;
		Assert.assertEquals(3, actual);
	}

	@Test
	public void testSplitUrl() throws Exception {
		final String obj = "www.ampath.com,www.kimosabi.com";
		final String[] output = verifyUrl.splitUrl(obj);
		Assert.assertEquals(2, output.length);
	}
}
