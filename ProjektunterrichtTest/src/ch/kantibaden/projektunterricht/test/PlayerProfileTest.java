package ch.kantibaden.projektunterricht.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.ShareManager;

public class PlayerProfileTest {
	
	private static PlayerProfile player;
	private static ShareManager shares;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		player = new PlayerProfile("name", "password", new BigDecimal(1000));
		shares = new ShareManager();
		
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
		fail("Not yet implemented");
	}

	@Test
	public void testBuy() {
		fail("Not yet implemented");
	}

	@Test
	public void testSell() {
		fail("Not yet implemented");
	}

}
