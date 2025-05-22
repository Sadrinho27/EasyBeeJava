-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 22 mai 2025 à 23:30
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `easybee_projet2`
--
CREATE DATABASE IF NOT EXISTS `easybee_projet2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `easybee_projet2`;

-- --------------------------------------------------------

--
-- Structure de la table `bonlivraison`
--

DROP TABLE IF EXISTS `bonlivraison`;
CREATE TABLE IF NOT EXISTS `bonlivraison` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dateLivraison` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `categoriesalarie`
--

DROP TABLE IF EXISTS `categoriesalarie`;
CREATE TABLE IF NOT EXISTS `categoriesalarie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomCat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `categoriesalarie`
--

INSERT INTO `categoriesalarie` (`id`, `nomCat`) VALUES
(1, 'vendeur'),
(2, 'preparateur');

-- --------------------------------------------------------

--
-- Structure de la table `cmdeapprodepot`
--

DROP TABLE IF EXISTS `cmdeapprodepot`;
CREATE TABLE IF NOT EXISTS `cmdeapprodepot` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dateCommande` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `statutCommande` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'en attente',
  `idCatSalarie` int NOT NULL,
  `nomCommande` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idCat` (`idCatSalarie`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `detailcmd`
--

DROP TABLE IF EXISTS `detailcmd`;
CREATE TABLE IF NOT EXISTS `detailcmd` (
  `idProduit` int NOT NULL,
  `idBonLivraison` int NOT NULL,
  `qtePrepa` int NOT NULL,
  `qteRecu` int NOT NULL DEFAULT '0',
  `idCmdeApproDepot` int NOT NULL,
  PRIMARY KEY (`idProduit`,`idBonLivraison`),
  KEY `idBonLivraison` (`idBonLivraison`),
  KEY `idCmdeApproDepot` (`idCmdeApproDepot`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `detailproduit`
--

DROP TABLE IF EXISTS `detailproduit`;
CREATE TABLE IF NOT EXISTS `detailproduit` (
  `idProduit` int NOT NULL,
  `idCmdeApproDepot` int NOT NULL,
  `qteCmde` int NOT NULL,
  PRIMARY KEY (`idProduit`,`idCmdeApproDepot`),
  KEY `idCmdeApproDepot` (`idCmdeApproDepot`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `codeProduit` int NOT NULL,
  `stockMag` int NOT NULL,
  `stockMiniMag` int NOT NULL,
  `designationProduit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `prixPdt` float NOT NULL,
  `stockEntrepot` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `codeProduit`, `stockMag`, `stockMiniMag`, `designationProduit`, `prixPdt`, `stockEntrepot`) VALUES
(24, 1001, 5, 20, 'Pot en verre 250g', 0.5, 100),
(25, 1002, 40, 20, 'Pot en verre 500g', 0.6, 80),
(26, 1003, 10, 30, 'Couvercle doré pour pot', 0.1, 200),
(27, 1004, 200, 50, 'Étiquettes personnalisables', 0.15, 500),
(28, 1005, 10, 2, 'Ruche Dadant 10 cadres', 85, 15),
(29, 1006, 5, 1, 'Ruche Dadant 20 cadres', 110, 10),
(30, 1007, 15, 20, 'Cadres montés avec fil', 1.2, 100),
(31, 1008, 70, 30, 'Feuilles de cire gaufrée', 0.9, 120),
(32, 1009, 15, 5, 'Enfumoir inox', 25, 30),
(33, 1010, 20, 5, 'Lève-cadres', 8, 40),
(34, 1011, 25, 10, 'Brosse à abeilles', 4.5, 50),
(35, 1012, 4, 2, 'Extracteur manuel 4 cadres', 190, 6),
(36, 1013, 10, 3, 'Couteau à désoperculer', 15, 15),
(37, 1014, 20, 5, 'Seau alimentaire 10L', 6, 30),
(38, 1015, 25, 10, 'Nourrissement sirop', 12, 40),
(39, 1016, 30, 10, 'Pâte candi', 8, 50),
(40, 1017, 10, 5, 'Traitement Varroa Apivar', 18, 20),
(41, 1018, 12, 3, 'Blouson apiculteur', 35, 20),
(42, 1019, 18, 5, 'Gants cuir + toile', 12, 30),
(43, 1020, 15, 5, 'Chapeau avec voile', 18, 25);

-- --------------------------------------------------------

--
-- Structure de la table `salarie`
--

DROP TABLE IF EXISTS `salarie`;
CREATE TABLE IF NOT EXISTS `salarie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `matriculeSalarie` int NOT NULL,
  `nomSalarie` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `prenomSalarie` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `idCat` int NOT NULL,
  `identifiant` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `motDePasse` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idCat` (`idCat`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `cmdeapprodepot`
--
ALTER TABLE `cmdeapprodepot`
  ADD CONSTRAINT `cmdeapprodepot_ibfk_1` FOREIGN KEY (`idCatSalarie`) REFERENCES `categoriesalarie` (`id`);

--
-- Contraintes pour la table `detailcmd`
--
ALTER TABLE `detailcmd`
  ADD CONSTRAINT `detailcmd_ibfk_1` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`),
  ADD CONSTRAINT `detailcmd_ibfk_2` FOREIGN KEY (`idBonLivraison`) REFERENCES `bonlivraison` (`id`),
  ADD CONSTRAINT `detailcmd_ibfk_3` FOREIGN KEY (`idCmdeApproDepot`) REFERENCES `cmdeapprodepot` (`id`);

--
-- Contraintes pour la table `detailproduit`
--
ALTER TABLE `detailproduit`
  ADD CONSTRAINT `detailproduit_ibfk_1` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`),
  ADD CONSTRAINT `detailproduit_ibfk_2` FOREIGN KEY (`idCmdeApproDepot`) REFERENCES `cmdeapprodepot` (`id`);

--
-- Contraintes pour la table `salarie`
--
ALTER TABLE `salarie`
  ADD CONSTRAINT `salarie_ibfk_1` FOREIGN KEY (`idCat`) REFERENCES `categoriesalarie` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
