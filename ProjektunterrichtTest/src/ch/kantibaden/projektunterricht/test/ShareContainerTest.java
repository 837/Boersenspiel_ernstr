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
import ch.kantibaden.projektunterricht.model.ShareManager;

public class ShareContainerTest {

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
		ArrayList<Share> shares = shareManager.getShares();
		share = shares.get(0);
		player.buy(share, 2);
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
		fail("Not yet implemented");
	}

	@Test
	public void testSell() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

}
