package app;

import java.awt.EventQueue;

import ui.PageConnexion;

public class App {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PageConnexion frame = new PageConnexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

//		String mdpVendeur = "adminVendeur";
//		String hashVendeur = BCrypt.hashpw(mdpVendeur, BCrypt.gensalt(12));
//		System.out.println("Mot de passe du Vendeur haché :\n" + hashVendeur + "\n");
//
//		String mdpPrepa = "adminPrepa";
//		String hashPrepa = BCrypt.hashpw(mdpPrepa, BCrypt.gensalt(12));
//		System.out.println("Mot de passe du Préparateur haché :\n" + hashPrepa + "\n");
	}
}