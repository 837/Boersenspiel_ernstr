package ch.kantibaden.projektunterricht;

import java.io.IOException;

import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.ShareManager;


public class Boersenspiel {

	private static PlayerProfile player;
	private static ShareManager manager;

	public Boersenspiel() {
	
	}

	public static void main(String[] args) {
		player = new PlayerProfile("Matthias Ernst", "ernst.matthias@hotmail.com", "abc123", 10000);

		try {
			manager = new ShareManager();
		} catch (IOException e) {
			System.out.println("Fehler in Boersenspiel.java manager=new ShareManager()");
			e.printStackTrace();
		}
	}

}
