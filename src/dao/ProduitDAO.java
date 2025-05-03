package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Produit;
import utils.ConnexionBdd;

public class ProduitDAO {

	ConnexionBdd cn = ConnexionBdd.getInstance();

	public List<String> getProduitStoreStockBelowMinimum() {

		List<String> produits = new ArrayList<>();

		String query = "SELECT designationProduit FROM produit WHERE stockMag < stockMiniMag ORDER BY id";

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				produits.add(rs.getString("designationProduit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produits;
	}

	public int getQuantiteByNomProduit(String designationProduit) {

		String query = "SELECT stockEntrepot FROM produit WHERE designationProduit = ?";

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query)) {
			stmt.setString(1, designationProduit);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("stockEntrepot");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Produit getProduitByNom(String designationProduit) {

		String query = "SELECT id, codeProduit, stockMag, stockMiniMag, designationProduit, prixPdt, stockEntrepot FROM produit WHERE designationProduit = ?";

		try (PreparedStatement stmt = cn.laconnexion().prepareStatement(query)) {
			stmt.setString(1, designationProduit);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Produit(rs.getInt("id"), rs.getInt("codeProduit"), rs.getInt("stockMag"),
							rs.getInt("stockMiniMag"), rs.getString("designationProduit"), rs.getInt("prixPdt"),
							rs.getInt("stockEntrepot"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
