package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.CommandeDAO;
import model.Commande;
import model.Utilisateur;

public class ListeCmde extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public ListeCmde(Utilisateur user) {
		setTitle("Liste des commandes en attente - Gestion des stocks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(370, 250, 700, 420);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout(20, 20));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setBackground(new Color(245, 250, 255));
		setContentPane(contentPane);

		// === Titre ===
		JLabel lblTitre = new JLabel("Sélectionner une commande à préparer");
		lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitre, BorderLayout.NORTH);

		// === Centre ===
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setOpaque(false);
		contentPane.add(centerPanel, BorderLayout.CENTER);

		JLabel lblSelectCmde = new JLabel("Commandes en attente :");
		lblSelectCmde.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblSelectCmde.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblSelectCmde);

		centerPanel.add(Box.createVerticalStrut(10));

		JComboBox<String> cbListCmde = new JComboBox<>();
		cbListCmde.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		cbListCmde.setMaximumSize(new Dimension(300, 30));
		cbListCmde.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(cbListCmde);

		cbListCmde.addItem("— Sélectionnez une commande —");

		CommandeDAO commandeDAO = new CommandeDAO();
		List<Commande> commandes = commandeDAO.listeCmdeByStatut("en attente");

		for (Commande commande : commandes) {
			cbListCmde.addItem(commande.getNom());
		}

		centerPanel.add(Box.createVerticalStrut(20));

		JButton btnSuivant = createStyledButton("Suivant", new Color(46, 204, 113));
		btnSuivant.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(btnSuivant);

		// === Bas de page ===
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bottomPanel.setOpaque(false);
		JButton btnRetour = createStyledButton("Retour", new Color(52, 152, 219));
		bottomPanel.add(btnRetour);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		// === Évènements ===
		btnSuivant.addActionListener(e -> {
			String cmdeSelectionne = (String) cbListCmde.getSelectedItem();
			if (cmdeSelectionne == null || cmdeSelectionne.startsWith("—")) {
				JOptionPane.showMessageDialog(contentPane, "Veuillez choisir une commande.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			} else {
				new DetailsCmde(user, cmdeSelectionne).setVisible(true);
				dispose();
			}
		});

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
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		return btn;
	}

}
