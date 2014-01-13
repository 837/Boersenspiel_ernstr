package ch.kantibaden.projektunterricht.dao;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.ext.IncompatibleFileFormatException;
import com.db4o.ext.OldFormatException;
import com.db4o.query.Query;

import ch.kantibaden.projektunterricht.model.PlayerProfile;

public class UserDao {

	private final static String DB4OFILENAME = "boersenspiel_ernstr_SAVES.db4o";
	private static PlayerProfile user;

	public UserDao() {
	}

	public static boolean login(String username, String password) {
		boolean login = false;
		ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB4OFILENAME);

		try {

			// retrieveUserByName
			Query query = db.query();
			query.constrain(PlayerProfile.class);
			query.descend("name").constrain(username);
			ObjectSet<PlayerProfile> result = query.execute();

			if (result.hasNext()) // found
			{
				// Search for user with pw
				query = db.query();
				query.constrain(PlayerProfile.class);
				query.descend("password").constrain(password);
				ObjectSet<PlayerProfile> resultWithPw = query.execute();

				if (resultWithPw.hasNext()) // found
				{
					setUser(resultWithPw.next());
					System.out.println("loggedIn  " + username);
					login = true;
				}
			} else {
				System.out.println("failed loggedIn");
				login = false;
			}
		} catch (Db4oIOException | DatabaseFileLockedException | IncompatibleFileFormatException | OldFormatException | DatabaseReadOnlyException
				| DatabaseClosedException ex) {
			System.out.println("Error in login() -->" + ex.getMessage());
			login = false;
		} finally {
			db.close();
		}
		return login;
	}

	public static boolean signup(String name, String password, String startkapital) {
		boolean signup = false;
		ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB4OFILENAME);
		try {
			// retrieveUserByName
			Query query = db.query();
			query.constrain(PlayerProfile.class);
			query.descend("name").constrain(name);
			ObjectSet<PlayerProfile> result = query.execute();

			if (result.hasNext()) // found
			{
				System.out.println("failed signup");
				signup = false;
			} else {

				db.store(new PlayerProfile(name, password, Integer.parseInt(startkapital)));

				System.out.println("signed up");
				signup = true;
			}
		} catch (Db4oIOException | DatabaseFileLockedException | IncompatibleFileFormatException | OldFormatException | DatabaseReadOnlyException
				| DatabaseClosedException ex) {
			System.out.println("Error in signup() -->" + ex.getMessage());
			signup = false;
		} finally {
			db.close();
		}
		return signup;
	}

	public static void saveUser(PlayerProfile user) {
		ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB4OFILENAME);
		try {
			// retrieveUserByName
			Query query = db.query();
			query.constrain(PlayerProfile.class);
			query.descend("name").constrain(user.getName());
			ObjectSet<PlayerProfile> result = query.execute();
			if (result.hasNext()) // found
			{
				PlayerProfile userToDelete = result.next();
				db.delete(userToDelete);
				db.store(user);
			} else {
				db.store(user);
			}

		} catch (Db4oIOException | DatabaseFileLockedException | IncompatibleFileFormatException | OldFormatException | DatabaseReadOnlyException
				| DatabaseClosedException ex) {
			System.out.println("Error in saveUser1() -->" + ex.getMessage());

		} finally {
			db.close();
		}
	}

	public static PlayerProfile getUser() {
		return user;
	}

	public static void setUser(PlayerProfile user) {
		UserDao.user = user;
	}

}