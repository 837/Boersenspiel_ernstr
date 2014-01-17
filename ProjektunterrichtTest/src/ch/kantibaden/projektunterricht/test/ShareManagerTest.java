package ch.kantibaden.projektunterricht.test;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.kantibaden.projektunterricht.model.ShareManager;

public class ShareManagerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(timeout=2000)
	public void testDownloadAll() {
		ShareManager shares = new ShareManager();
		try {
			shares.downloadAll();
		} catch (IOException e) {
			
		}
	}

}
