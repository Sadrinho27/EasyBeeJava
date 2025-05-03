package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Utilisateur;

public class PageAccueil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public PageAccueil(Utilisateur user) {
		setTitle("Accueil - Gestion des stocks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(370, 250, 700, 420);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(240, 245, 255));
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		setContentPane(contentPane);

		// ===== TOP PANEL =====
		JLabel lblUser = new JLabel("Bienvenue, " + user.getLogin() + " !");
		lblUser.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblUser.setForeground(new Color(50, 50, 100));
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(240, 245, 255));
		topPanel.add(lblUser);
		contentPane.add(topPanel, BorderLayout.NORTH);

		// ===== CENTER PANEL =====
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 15, 10, 15);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Labels
		JLabel lblVendeurs = new JLabel("Espace Vendeur");
		lblVendeurs.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblVendeurs.setForeground(Color.DARK_GRAY);
		JLabel lblPreparateurs = new JLabel("Espace Préparateur");
		lblPreparateurs.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblPreparateurs.setForeground(Color.DARK_GRAY);

		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(lblVendeurs, gbc);

		gbc.gridx = 1;
		centerPanel.add(lblPreparateurs, gbc);

		// Boutons Vendeur
		JButton passerCmdBtn = createStyledButton("Passer une commande d'approvisionnement");
		passerCmdBtn.addActionListener(e -> {
			if (user.getRole() == 1) {
				new PasserCmde(user).setVisible(true);
				dispose();
			} else {
				showAccessDenied();
			}
		});

		JButton suiviCmdBtn = createStyledButton("Suivi des commandes");
		suiviCmdBtn.addActionListener(e -> {
			if (user.getRole() == 1) {
				new PageSuiviCmde(user).setVisible(true);
				dispose();
			} else {
				showAccessDenied();
			}
		});

		// Boutons Préparateur
		JButton listCmdBtn = createStyledButton("Liste des commandes");
		listCmdBtn.addActionListener(e -> {
			if (user.getRole() == 2) {
				new ListeCmde(user).setVisible(true);
				dispose();
			} else {
				showAccessDenied();
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(passerCmdBtn, gbc);

		gbc.gridx = 1;
		centerPanel.add(listCmdBtn, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(suiviCmdBtn, gbc);

		contentPane.add(centerPanel, BorderLayout.CENTER);

		// ===== BOTTOM PANEL =====
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bottomPanel.setBackground(new Color(240, 245, 255));
		JButton btnDeco = createStyledButton("Déconnexion");
		btnDeco.setBackground(new Color(220, 80, 80));
		btnDeco.setForeground(Color.WHITE);
		btnDeco.addActionListener(e -> {
			new PageConnexion().setVisible(true);
			dispose();
		});
		bottomPanel.add(btnDeco);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
	}

	private JButton createStyledButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		button.setBackground(new Color(100, 150, 255));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setRolloverEnabled(true);

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent evt) {
				if (text.equals("Déconnexion")) {
					button.setBackground(new Color(250, 100, 100));
				} else {
					button.setBackground(new Color(70, 130, 255));
				}
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				if (text.equals("Déconnexion")) {
					button.setBackground(new Color(220, 80, 80));
				} else {
					button.setBackground(new Color(100, 150, 255));
				}
			}
		});

		return button;
	}

	private void showAccessDenied() {
		JOptionPane.showMessageDialog(contentPane,
				"Vous n'avez pas le rôle nécessaire pour accéder à cette fonctionnalité.", "Accès refusé",
				JOptionPane.ERROR_MESSAGE);
	}
}
