-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 12, 2015 at 06:58 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `poznawajka`
--

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `gr_IdGroup` int(11) NOT NULL,
  `gr_Name` varchar(50) NOT NULL,
  `gr_Description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`gr_IdGroup`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `us_UserId` int(11) NOT NULL AUTO_INCREMENT,
  `us_UserName` varchar(50) NOT NULL,
  `us_UserPassword` varchar(100) NOT NULL,
  `us_UserPasswordReset` tinyint(1) NOT NULL,
  PRIMARY KEY (`us_UserId`),
  UNIQUE KEY `us_UserName` (`us_UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users_info`
--

CREATE TABLE IF NOT EXISTS `users_info` (
  `ui_UserId` int(11) NOT NULL,
  `ui_Name` varchar(100) NOT NULL,
  `ui_LastName` varchar(100) NOT NULL,
  `ui_Sex` tinyint(1) NOT NULL,
  `ui_Status` tinyint(1) NOT NULL,
  `ui_Preferences` varchar(1000) DEFAULT NULL,
  `ui_Description` varchar(1000) DEFAULT NULL,
  `ui_GpsX` float DEFAULT NULL,
  `ui_GpsY` float DEFAULT NULL,
  `ui_Age` int(11) NOT NULL,
  PRIMARY KEY (`ui_UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_friendship`
--

CREATE TABLE IF NOT EXISTS `user_friendship` (
  `uf_Id` int(11) NOT NULL AUTO_INCREMENT,
  `uf_UserId` int(11) NOT NULL,
  `uf_UserFriendId` int(11) NOT NULL,
  PRIMARY KEY (`uf_Id`),
  KEY `uf_UserId` (`uf_UserId`,`uf_UserFriendId`),
  KEY `uf_UserFriendId` (`uf_UserFriendId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_gallery`
--

CREATE TABLE IF NOT EXISTS `user_gallery` (
  `ug_Id` int(11) NOT NULL AUTO_INCREMENT,
  `ug_UserId` int(11) NOT NULL,
  `ug_GalleryName` varchar(50) NOT NULL,
  `ug_Size` int(11) NOT NULL,
  PRIMARY KEY (`ug_Id`),
  KEY `ug_UserId` (`ug_UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_groups`
--

CREATE TABLE IF NOT EXISTS `user_groups` (
  `ug_Id` int(11) NOT NULL AUTO_INCREMENT,
  `ug_UserId` int(11) NOT NULL,
  `ug_GroupId` int(11) NOT NULL,
  PRIMARY KEY (`ug_Id`),
  KEY `ug_UserId` (`ug_UserId`,`ug_GroupId`),
  KEY `ug_GroupId` (`ug_GroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_photo`
--

CREATE TABLE IF NOT EXISTS `user_photo` (
  `up_PhotoId` int(11) NOT NULL AUTO_INCREMENT,
  `up_UserId` int(11) NOT NULL,
  `up_UserGalleryId` int(11) NOT NULL,
  PRIMARY KEY (`up_PhotoId`),
  UNIQUE KEY `up_UserId` (`up_UserId`,`up_UserGalleryId`),
  UNIQUE KEY `up_UserGalleryId` (`up_UserGalleryId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_prov`
--

CREATE TABLE IF NOT EXISTS `user_prov` (
  `pr_Id` int(11) NOT NULL AUTO_INCREMENT,
  `pr_UserId` int(11) NOT NULL,
  `pr_TargetUserId` int(11) NOT NULL,
  `pr_TypeProv` int(11) NOT NULL,
  `pr_Message` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`pr_Id`),
  KEY `pr_UserId` (`pr_UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_rating`
--

CREATE TABLE IF NOT EXISTS `user_rating` (
  `ur_Id` int(11) NOT NULL AUTO_INCREMENT,
  `ur_UserId` int(11) NOT NULL,
  `ur_Rate` int(11) NOT NULL,
  `ur_Comment` varchar(500) DEFAULT NULL,
  `ur_FromUserId` int(11) NOT NULL,
  PRIMARY KEY (`ur_Id`),
  KEY `ur_UserId` (`ur_UserId`),
  KEY `ur_FromUserId` (`ur_FromUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `users_info`
--
ALTER TABLE `users_info`
  ADD CONSTRAINT `users_info_ibfk_1` FOREIGN KEY (`ui_UserId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_friendship`
--
ALTER TABLE `user_friendship`
  ADD CONSTRAINT `user_friendship_ibfk_2` FOREIGN KEY (`uf_UserFriendId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_friendship_ibfk_1` FOREIGN KEY (`uf_UserId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_gallery`
--
ALTER TABLE `user_gallery`
  ADD CONSTRAINT `user_gallery_ibfk_1` FOREIGN KEY (`ug_UserId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_groups`
--
ALTER TABLE `user_groups`
  ADD CONSTRAINT `user_groups_ibfk_2` FOREIGN KEY (`ug_GroupId`) REFERENCES `groups` (`gr_IdGroup`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_groups_ibfk_1` FOREIGN KEY (`ug_UserId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_photo`
--
ALTER TABLE `user_photo`
  ADD CONSTRAINT `user_photo_ibfk_2` FOREIGN KEY (`up_UserGalleryId`) REFERENCES `user_gallery` (`ug_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_photo_ibfk_1` FOREIGN KEY (`up_UserId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_prov`
--
ALTER TABLE `user_prov`
  ADD CONSTRAINT `user_prov_ibfk_1` FOREIGN KEY (`pr_UserId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_rating`
--
ALTER TABLE `user_rating`
  ADD CONSTRAINT `user_rating_ibfk_2` FOREIGN KEY (`ur_FromUserId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_rating_ibfk_1` FOREIGN KEY (`ur_UserId`) REFERENCES `users` (`us_UserId`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
