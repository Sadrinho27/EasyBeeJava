package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.CommandeDAO;
import dao.ProduitDAO;
import model.Utilisateur;

public class PasserCmde extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@SuppressWarnings("serial")
	public PasserCmde(Utilisateur user) {
		setTitle("Passer une commande - Gestion des stocks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(370, 250, 700, 450);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setBackground(new Color(245, 250, 255));
		setContentPane(contentPane);

		// === Titre principal ===
		JLabel titre = new JLabel("Nouvelle commande d'approvisionnement");
		titre.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		titre.setForeground(new Color(44, 62, 80));
		contentPane.add(titre, BorderLayout.NORTH);

		// === Zone centrale ===
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 5, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		contentPane.add(centerPanel, BorderLayout.CENTER);

		// === Formulaire gauche ===
		JLabel lblNomProduit = new JLabel("Nom du produit :");
		lblNomProduit.setFont(new Font("Segoe UI", Font.BOLD, 13));
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(lblNomProduit, gbc);

		JComboBox<String> cbListProduit = new JComboBox<>();
		cbListProduit.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		gbc.gridy++;
		centerPanel.add(cbListProduit, gbc);

		JLabel lblQnt = new JLabel("Quantité :");
		lblQnt.setFont(new Font("Segoe UI", Font.BOLD, 13));
		gbc.gridy++;
		centerPanel.add(lblQnt, gbc);

		JTextField tfQnt = new JTextField("0");
		gbc.gridy++;
		centerPanel.add(tfQnt, gbc);

		JLabel lblStock = new JLabel("Stock Entrepôt : -");
		lblStock.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		gbc.gridy++;
		centerPanel.add(lblStock, gbc);

		JButton btnAjouter = createStyledButton("Ajouter à la commande", new Color(46, 204, 113));
		gbc.gridy++;
		centerPanel.add(btnAjouter, gbc);

		// === Tableau des produits à droite ===
		DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Produit", "Quantité" }, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		JTable table = new JTable(tableModel);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(20);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(280, 160));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 5;
		centerPanel.add(scroll, gbc);

		JButton btnSupprimer = createStyledButton("Supprimer le produit", new Color(231, 76, 60));
		gbc.gridy = 5;
		gbc.gridheight = 1;
		centerPanel.add(btnSupprimer, gbc);

		// === Boutons bas ===
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		bottomPanel.setOpaque(false);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		JButton btnValider = createStyledButton("Valider la commande", new Color(41, 128, 185));
		JButton btnRetour = createStyledButton("Retour", new Color(149, 165, 166));

		bottomPanel.add(btnValider);
		bottomPanel.add(btnRetour);

		// === Récupération des produits ===
		ProduitDAO produitDAO = new ProduitDAO();
		cbListProduit.addItem("Sélectionnez un produit");
		List<String> produits = produitDAO.getProduitStoreStockBelowMinimum();
		produits.forEach(cbListProduit::addItem);

		cbListProduit.addActionListener(e -> {
			String selectedProduit = (String) cbListProduit.getSelectedItem();
			if (selectedProduit != null && !selectedProduit.equals("Sélectionnez un produit")) {
				int quantite = produitDAO.getQuantiteByNomProduit(selectedProduit);
				lblStock.setText("Stock Entrepôt : " + quantite);
			} else {
				lblStock.setText("Stock Entrepôt : -");
			}
		});

		// === Action bouton Ajouter ===
		btnAjouter.addActionListener(e -> {
			String produit = (String) cbListProduit.getSelectedItem();
			if (produit == null || produit.equals("Sélectionnez un produit")) {
				showMessage("Veuillez choisir un produit.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			for (int i = 0; i < tableModel.getRowCount(); i++) {
				if (tableModel.getValueAt(i, 0).equals(produit)) {
					showMessage("Ce produit a déjà été ajouté.", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			int qte;
			try {
				qte = Integer.parseInt(tfQnt.getText());
				if (qte <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				showMessage("Quantité invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int stock = produitDAO.getQuantiteByNomProduit(produit);
			if (qte > stock) {
				showMessage("Quantité supérieure au stock.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			tableModel.addRow(new Object[] { produit, qte });
		});

		// === Supprimer ligne sélectionnée ===
		btnSupprimer.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row >= 0)
				tableModel.removeRow(row);
			else
				showMessage("Sélectionnez un produit à supprimer.", "Info", JOptionPane.INFORMATION_MESSAGE);
		});

		// === Valider la commande ===
		btnValider.addActionListener(e -> {
			if (tableModel.getRowCount() == 0) {
				showMessage("Ajoutez au moins un produit.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			CommandeDAO commandeDAO = new CommandeDAO();
			boolean success = true;

			for (int i = 0; i < tableModel.getRowCount(); i++) {
				String pdt = (String) tableModel.getValueAt(i, 0);
				int qty = (int) tableModel.getValueAt(i, 1);
				if (!commandeDAO.createCommande(user.getRole(), pdt, qty)) {
					success = false;
					break;
				}
			}

			if (success) {
				showMessage("Commande enregistrée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
				new PageAccueil(user).setVisible(true);
				dispose();
			} else {
				showMessage("Une erreur s'est produite.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});

		// === Retour ===
		btnRetour.addActionListener(e -> {
			new PageAccueil(user).setVisible(true);
			dispose();
		});
	}

	private JButton createStyledButton(String text, Color bgColor) {
		JButton btn = new JButton(text);
		btn.setBackground(bgColor);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}

	private void showMessage(String msg, String title, int type) {
		JOptionPane.showMessageDialog(contentPane, msg, title, type);
	}
}
