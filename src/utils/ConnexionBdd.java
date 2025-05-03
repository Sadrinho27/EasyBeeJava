package utils;

import java.sql.Connection;
import java.sql.DriverManager;

// Singleton
public class ConnexionBdd {

	private static ConnexionBdd instance;
	Connection con;

	private String host = "localhost";
	private String dbName = "easybee_projet2";
	private String username = "easybee_projet2";
	private String password = "(s@JOuAzT4Hxh7iP";

	private ConnexionBdd() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbName, username, password);
			System.out.println("connexion reussie");
		} catch (Exception ex) {
			System.out.println(" ERREUR | Problème de connexion à la base de données : " + ex.getMessage());
		}
	}

	public static ConnexionBdd getInstance() {
		if (instance == null) {
			synchronized (ConnexionBdd.class) { // Synchronisation pour éviter les conflits en multi-threading
				if (instance == null) {
					instance = new ConnexionBdd();
				}
			}
		}
		return instance;
	}

	public Connection laconnexion() {
		return con;
	}
}
