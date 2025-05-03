package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Commande;
import model.Produit;
import utils.ConnexionBdd;

public class CommandeDAO {

	ConnexionBdd cn = ConnexionBdd.getInstance();

	public boolean createCommande(int roleUser, String nomProduit, int quantity) {
		String query1 = "INSERT INTO cmdeapprodepot (idCatSalarie, nomCommande) VALUES (?, ?)";
		String query2 = "INSERT INTO detailproduit (idProduit, idCmdeApproDepot, qteCmde) VALUES (?, ?, ?)";

		ProduitDAO produitDAO = new ProduitDAO();

		try {
			Produit produit = produitDAO.getProduitByNom(nomProduit);
			if (produit == null) {
				System.out.println("Produit introuvable : " + nomProduit);
				return false;
			}

			int idProduit = produit.getIdProduit();

			PreparedStatement stmt1 = cn.laconnexion().prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
			stmt1.setInt(1, roleUser);
			stmt1.setString(2, nomProduit);
			stmt1.executeUpdate();

			ResultSet generatedKeys = stmt1.getGeneratedKeys();
			if (generatedKeys.next()) {
				int idCmdeApproDepot = generatedKeys.getInt(1);

				PreparedStatement stmt2 = cn.laconnexion().prepareStatement(query2);
				stmt2.setInt(1, idProduit);
				stmt2.setInt(2, idCmdeApproDepot);
				stmt2.setInt(3, quantity);
				stmt2.executeUpdate();

				return true;
			} else {
				System.out.println("Échec de la récupération de l'ID de la commande.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Erreur lors de la création de la commande : " + e.getMessage());
			return false;
		}
	}

	public boolean saveDetailsCmde(Commande cmde, int qtePrepa) {
		String query = "INSERT INTO detailcmd (idProduit, idBonLivraison, qtePrepa, idCmdeApproDepot) VALUES (?, ?, ?, ?)";

		ProduitDAO produitDAO = new ProduitDAO();
		Produit produit = produitDAO.getProduitByNom(cmde.getNom());
		if (produit == null) {
			System.out.println("Produit introuvable : " + cmde.getNom());
			return false;
		}

		int idProduit = produit.getIdProduit();
		int idCmdeApproDepot = cmde.getId();
		int idBonLivraison = createBonLivraisonIfNeeded();

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query)) {
			stmt.setInt(1, idProduit);
			stmt.setInt(2, idBonLivraison);
			stmt.setInt(3, qtePrepa);
			stmt.setInt(4, idCmdeApproDepot);

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				this.changerStatutCommande(cmde.getNom(), "en cours de livraison");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateQntRecu(String nomCommande, int qteRecu, int idCmdeApproDepot) {
		String query = "UPDATE detailcmd SET qteRecu = ? WHERE idCmdeApproDepot = ?";

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query)) {
			stmt.setInt(1, qteRecu);
			stmt.setInt(2, idCmdeApproDepot);

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				this.changerStatutCommande(nomCommande, "livrée");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private int createBonLivraisonIfNeeded() {
		String query = "INSERT INTO bonlivraison (dateLivraison) VALUES (CURRENT_TIMESTAMP)";
		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			int rowInserted = stmt.executeUpdate();
			if (rowInserted > 0) {
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						return generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Commande afficherDetailsCmdeSelectione(String cmdeSelectionne) {
		String query = "SELECT id, nomCommande, statutCommande, qteCmde FROM detailproduit JOIN cmdeapprodepot ON idCmdeApproDepot = cmdeapprodepot.id WHERE nomCommande = ?";

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query)) {
			stmt.setString(1, cmdeSelectionne);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Commande(rs.getInt("id"), rs.getString("nomCommande"), rs.getInt("qteCmde"),
							rs.getString("statutCommande"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean changerStatutCommande(String nomCommande, String newStatut) {
		String query = "UPDATE cmdeapprodepot SET statutCommande = ? WHERE nomCommande = ?";

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query)) {
			stmt.setString(1, newStatut);
			stmt.setString(2, nomCommande);

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Commande> listeCmdeByStatut(String statut) {
		List<Commande> cmdes = new ArrayList<>();

		String query = "SELECT id, nomCommande, statutCommande FROM cmdeapprodepot WHERE statutCommande LIKE ?";

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query)) {
			stmt.setString(1, "%" + statut + "%");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Commande uneCmde = new Commande(rs.getInt("id"), rs.getString("nomCommande"), 0,
							rs.getString("statutCommande"));
					cmdes.add(uneCmde);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cmdes;
	}
}