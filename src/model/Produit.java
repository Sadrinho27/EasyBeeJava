package model;

public class Produit {
	private int idProduit;
	private int codeProduit;
	private int stockMag;
	private int stockMiniMag;
	private String designationProduit;
	private double prixPdt;
	private int stockEntrepot;

	public Produit(int idProduit, int codeProduit, int stockMag, int stockMiniMag, String designationProduit,
			double prixPdt, int stockEntrepot) {
		this.idProduit = idProduit;
		this.codeProduit = codeProduit;
		this.stockMag = stockMag;
		this.stockMiniMag = stockMiniMag;
		this.designationProduit = designationProduit;
		this.prixPdt = prixPdt;
		this.stockEntrepot = stockEntrepot;
	}

	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public int getCodeProduit() {
		return codeProduit;
	}

	public void setCodeProduit(int codeProduit) {
		this.codeProduit = codeProduit;
	}

	public int getStockMag() {
		return stockMag;
	}

	public void setStockMag(int stockMag) {
		this.stockMag = stockMag;
	}

	public int getStockMiniMag() {
		return stockMiniMag;
	}

	public void setStockMiniMag(int stockMiniMag) {
		this.stockMiniMag = stockMiniMag;
	}

	public String getDesignationProduit() {
		return designationProduit;
	}

	public void setDesignationProduit(String designationProduit) {
		this.designationProduit = designationProduit;
	}

	public double getPrixPdt() {
		return prixPdt;
	}

	public void setPrixPdt(double prixPdt) {
		this.prixPdt = prixPdt;
	}

	public int getStockEntrepot() {
		return stockEntrepot;
	}

	public void setStockEntrepot(int stockEntrepot) {
		this.stockEntrepot = stockEntrepot;
	}

}
