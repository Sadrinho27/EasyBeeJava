package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.CommandeDAO;
import model.Commande;
import model.Utilisateur;

public class PageSuiviCmde extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public PageSuiviCmde(Utilisateur user) {
		setTitle("Suivi des commandes - Gestion des stocks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(370, 250, 660, 420);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 245, 255));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// === TITRE ===
		JLabel lblTitre = new JLabel("Suivi des commandes en cours de livraison");
		lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitre.setForeground(new Color(50, 50, 100));
		lblTitre.setBounds(82, 30, 480, 25);
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitre);

		JLabel lblCmdes = new JLabel("Commandes en cours de livraison :");
		lblCmdes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblCmdes.setBounds(172, 80, 300, 20);
		contentPane.add(lblCmdes);

		JComboBox<String> cbListCmdes = new JComboBox<>();
		cbListCmdes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		cbListCmdes.setBounds(172, 105, 300, 30);
		contentPane.add(cbListCmdes);
		cbListCmdes.addItem("— Sélectionnez une commande —");

		CommandeDAO commandeDAO = new CommandeDAO();

		Map<String, Commande> commandeMap = new HashMap<>();
		List<Commande> commandes = commandeDAO.listeCmdeByStatut("en cours de livraison");

		for (Commande uneCommande : commandes) {
			cbListCmdes.addItem(uneCommande.getNom());
			commandeMap.put(uneCommande.getNom(), uneCommande);
		}

		// === CHECKBOX ===
		JCheckBox chckbxNewStatut = new JCheckBox("La commande a bien été livrée");
		chckbxNewStatut.setBackground(new Color(240, 245, 255));
		chckbxNewStatut.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		chckbxNewStatut.setBounds(197, 150, 250, 25);
		contentPane.add(chckbxNewStatut);

		// === QUANTITÉ REÇUE ===
		JLabel lblQntRecu = new JLabel("Quantité Reçue :");
		lblQntRecu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblQntRecu.setBounds(180, 190, 150, 25);
		lblQntRecu.setVisible(false);
		contentPane.add(lblQntRecu);

		JTextField tfQntRecu = new JTextField();
		tfQntRecu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tfQntRecu.setBounds(330, 190, 100, 25);
		tfQntRecu.setVisible(false);
		contentPane.add(tfQntRecu);

		// === BOUTON TERMINÉ ===
		JButton btnTermine = new JButton("Terminé");
		btnTermine.setBounds(262, 247, 120, 35);
		btnTermine.setBackground(new Color(46, 204, 113));
		btnTermine.setForeground(Color.WHITE);
		btnTermine.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnTermine.setFocusPainted(false);
		btnTermine.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTermine.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		contentPane.add(btnTermine);

		// === BOUTON RETOUR ===
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBounds(30, 320, 100, 35);
		btnRetour.setBackground(new Color(52, 152, 219));
		btnRetour.setForeground(Color.WHITE);
		btnRetour.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnRetour.setFocusPainted(false);
		btnRetour.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRetour.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		contentPane.add(btnRetour);

		// === ACTIONS ===
		chckbxNewStatut.addActionListener(e -> {
			if (!cbListCmdes.getSelectedItem().equals("— Sélectionnez une commande —")) {
				boolean checked = chckbxNewStatut.isSelected();
				lblQntRecu.setVisible(checked);
				tfQntRecu.setVisible(checked);
			} else {
				JOptionPane.showMessageDialog(contentPane, "Veuillez d'abord sélectionner une commande.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				chckbxNewStatut.setSelected(false);
			}
		});

		btnTermine.addActionListener(e -> {
			if (!cbListCmdes.getSelectedItem().equals("— Sélectionnez une commande —")) {
				if (chckbxNewStatut.isSelected()) {
					try {
						int qntRecu = Integer.parseInt(tfQntRecu.getText().trim());
						if (qntRecu > 0) {
							String selectedNom = (String) cbListCmdes.getSelectedItem();
							Commande cmdeSelectionne = commandeMap.get(selectedNom);

							commandeDAO.updateQntRecu(cmdeSelectionne.getNom(), qntRecu, cmdeSelectionne.getId());

							JOptionPane.showMessageDialog(contentPane, "Le statut de la commande a été changé",
									"Succès", JOptionPane.INFORMATION_MESSAGE);

							new PageSuiviCmde(user).setVisible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(contentPane,
									"Veuillez saisir une quantité reçue supérieure à 0.", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(contentPane, "Veuillez saisir une quantité valide.", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Veuillez confirmer la livraison", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(contentPane, "Aucune commande sélectionnée", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnRetour.addActionListener(e -> {
			new PageAccueil(user).setVisible(true);
			dispose();
		});
	}
}
