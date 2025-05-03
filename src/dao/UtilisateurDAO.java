package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import model.Utilisateur;
import utils.ConnexionBdd;

public class UtilisateurDAO {

	static ConnexionBdd cn = ConnexionBdd.getInstance();

	public static Utilisateur seConnecter(String login, String password) {

		String query = "SELECT identifiant, idCat, motDePasse FROM salarie WHERE identifiant = ?";

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query)) {

			stmt.setString(1, login);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String hashStocke = rs.getString("motDePasse");

					if (BCrypt.checkpw(password, hashStocke)) {
						return new Utilisateur(rs.getString("identifiant"), rs.getInt("idCat"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
