package ch.kantibaden.projektunterricht.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.kantibaden.projektunterricht.dao.UserDao;
import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.Share;
import ch.kantibaden.projektunterricht.model.ShareContainer;
import ch.kantibaden.projektunterricht.model.ShareManager;

public class ShareContainerTest {
	
	private static ShareContainer shareContainer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		ShareManager shareManager = new ShareManager();
		for (;/*ever*/;) {
			try {
				shareManager.downloadAll();
				break;
			} catch (Exception e) {
			}
		}
		
		shareContainer = new ShareContainer(shareManager.getShares().get(0), 2);
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

	@Test
	public void testBuy() {
		shareContainer.buy(1);
		
		assertEquals(shareContainer.getAmount(), 3);
		
		shareContainer.sell(1);//Ensure it's always 2
		
	}

	@Test
	public void testSell() {
		shareContainer.sell(1);
		
		assertEquals(shareContainer.getAmount(), 1);
		
		shareContainer.buy(1);//Ensure it's always 2
	}

}
