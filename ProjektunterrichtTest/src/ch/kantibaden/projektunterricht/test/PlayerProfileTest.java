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

public class PlayerProfileTest {

	private static PlayerProfile player;
	private static Share share;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		player = new PlayerProfile("name", "password", new BigDecimal(10000));
		UserDao.setUser(player);
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
	public void testGetTotalShareValue() {
		assertEquals(player.getTotalShareValue(), share.getValue().multiply(new BigDecimal(2)));
	}

	@Test
	public void testBuy() {
		player.buy(share, 1);
		assertEquals(player.getOwnedAmountOfShare(share), 3);
		
		player.sell(share, 1);//to ensure he always owns 2;
	}
	
	@Test
	public void testBuyTooMany() {
		player.buy(share, 999999);
		assertEquals(player.getOwnedAmountOfShare(share), 2);
	}

	@Test
	public void testSell() {
		player.sell(share, 1);
		assertEquals(player.getOwnedAmountOfShare(share), 1);
	}
	
	@Test
	public void testSellTooMany() {
		player.sell(share, 9999);
		assertEquals(player.getOwnedAmountOfShare(share), 0);
		player.buy(share, 2);//to ensure he always owns 2;
	}
	
	@Test
	public void testGetTotalValueOfShare() {
		assertEquals(player.getTotalValueOfShare(share), share.getValue().multiply(new BigDecimal(2)));
	}
	
	@Test
	public void testGetOwnedAmountOfShare() {
		assertEquals(player.getOwnedAmountOfShare(share), 2);
	}

}
