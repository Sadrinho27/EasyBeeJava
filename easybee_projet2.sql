-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 14 avr. 2025 à 11:34
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
CREATE DATABASE IF NOT EXISTS `easybee_projet2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- --------------------------------------------------------

--
-- Structure de la table `categoriesalarie`
--

DROP TABLE IF EXISTS `categoriesalarie`;
CREATE TABLE IF NOT EXISTS `categoriesalarie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomCat` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- --------------------------------------------------------

--
-- Structure de la table `cmdeapprodepot`
--

DROP TABLE IF EXISTS `cmdeapprodepot`;
CREATE TABLE IF NOT EXISTS `cmdeapprodepot` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dateCommande` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `statutCommande` enum('AT','RP','RT','EP','CL') CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT 'AT',
  `idCatSalarie` int NOT NULL,
  `nomCommande` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idCat` (`idCatSalarie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- --------------------------------------------------------

--
-- Structure de la table `detailcmd`
--

DROP TABLE IF EXISTS `detailcmd`;
CREATE TABLE IF NOT EXISTS `detailcmd` (
  `idProduit` int NOT NULL,
  `idBonLivraison` int NOT NULL,
  `qtePrepa` int NOT NULL,
  `qteRecu` int NOT NULL,
  `idCmdeApproDepot` int NOT NULL,
  PRIMARY KEY (`idProduit`,`idBonLivraison`),
  KEY `idBonLivraison` (`idBonLivraison`),
  KEY `idCmdeApproDepot` (`idCmdeApproDepot`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

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
  `designationProduit` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `prixPdt` float NOT NULL,
  `stockEntrepot` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- --------------------------------------------------------

--
-- Structure de la table `salarie`
--

DROP TABLE IF EXISTS `salarie`;
CREATE TABLE IF NOT EXISTS `salarie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `matriculeSalarie` int NOT NULL,
  `nomSalarie` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `prenomSalarie` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `idCat` int NOT NULL,
  `identifiant` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `motDePasse` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idCat` (`idCat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

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
