package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import dao.UtilisateurDAO;
import model.Utilisateur;

public class PageConnexion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public PageConnexion() {
		setTitle("Connexion - Gestion des stocks");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(370, 250, 660, 420);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(240, 245, 255));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		// === TITRE ===
		JLabel lblTitre = new JLabel("Connexion à la plateforme de gestion");
		lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setForeground(new Color(50, 50, 100));
		contentPane.add(lblTitre, BorderLayout.NORTH);

		// === CENTRE ===
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblLogin = new JLabel("Identifiant :");
		lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JTextField textFieldLogin = new JTextField(20);

		JLabel lblPassword = new JLabel("Mot de passe :");
		lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JPasswordField passwordField = new JPasswordField(20);

		JCheckBox showPassword = new JCheckBox("Afficher le mot de passe");
		showPassword.setOpaque(false);
		showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		showPassword.addActionListener(e -> {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0); // Affiche le mot de passe
			} else {
				passwordField.setEchoChar('\u2022'); // Rétablit les bullets ●●●
			}
		});

		JButton btnConnexion = createStyledButton("Se connecter");

		// Spinner (masqué par défaut)
		JProgressBar loadingSpinner = new JProgressBar();
		loadingSpinner.setIndeterminate(true);
		loadingSpinner.setVisible(false);

		// Placement
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(lblLogin, gbc);
		gbc.gridy++;
		formPanel.add(textFieldLogin, gbc);
		gbc.gridy++;
		formPanel.add(lblPassword, gbc);
		gbc.gridy++;
		formPanel.add(passwordField, gbc);
		gbc.gridy++;
		formPanel.add(showPassword, gbc);
		gbc.gridy++;
		formPanel.add(btnConnexion, gbc);
		gbc.gridy++;
		formPanel.add(loadingSpinner, gbc);

		contentPane.add(formPanel, BorderLayout.CENTER);

		// === ACTION CONNEXION ===
		btnConnexion.addActionListener(e -> {
			String login = textFieldLogin.getText();
			char[] password = passwordField.getPassword();

			// Champ vide ?
			if (login.isEmpty() || password.length == 0) {
				JOptionPane.showMessageDialog(contentPane, "Veuillez remplir tous les champs.", "Champs requis",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Lancer l'animation
			btnConnexion.setEnabled(false);
			loadingSpinner.setVisible(true);

			// Simulation du traitement en arrière-plan
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					Thread.sleep(1500); // simulation d’attente
					return null;
				}

				@Override
				protected void done() {
					Utilisateur user = UtilisateurDAO.seConnecter(login, new String(password));

					// Réinitialisation
					textFieldLogin.setText("");
					passwordField.setText("");
					btnConnexion.setEnabled(true);
					loadingSpinner.setVisible(false);

					if (user != null) {
						JOptionPane.showMessageDialog(contentPane, "Vous êtes connecté.");
						new PageAccueil(user).setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(contentPane, "Identifiant ou mot de passe incorrect.",
								"Erreur de connexion", JOptionPane.ERROR_MESSAGE);
					}
				}
			};
			worker.execute();
		});
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
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(70, 130, 255));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(100, 150, 255));
			}
		});
		return button;
	}

}
