/*
SQLyog Enterprise Trial - MySQL GUI v7.11 
MySQL - 5.6.22-log : Database - videomark
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`videomark` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `videomark`;

/*Table structure for table `cachetask` */

DROP TABLE IF EXISTS `cachetask`;

CREATE TABLE `cachetask` (
  `taskID` varchar(50) NOT NULL,
  `json` text NOT NULL,
  `originalFileId` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `middletasktargettype` */

DROP TABLE IF EXISTS `middletasktargettype`;

CREATE TABLE `middletasktargettype` (
  `taskTargetTypeID` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `taskID` varchar(50) DEFAULT NULL,
  `targetTypeID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`taskTargetTypeID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `originalfile` */

DROP TABLE IF EXISTS `originalfile`;

CREATE TABLE `originalfile` (
  `originalFileID` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `path` varchar(1000) DEFAULT NULL,
  `taskID` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `pid` varchar(50) DEFAULT NULL,
  `isParent` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`originalFileID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `resultfile` */

DROP TABLE IF EXISTS `resultfile`;

CREATE TABLE `resultfile` (
  `resultFileID` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `path` varchar(1000) DEFAULT NULL,
  `taskID` varchar(50) DEFAULT NULL,
  `statusID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`resultFileID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `resulttype` */

DROP TABLE IF EXISTS `resulttype`;

CREATE TABLE `resulttype` (
  `resultTypeID` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `typename` varchar(50) NOT NULL,
  PRIMARY KEY (`resultTypeID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `roleID` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `rolename` varchar(50) NOT NULL,
  PRIMARY KEY (`roleID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `status` */

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `statusID` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `statusname` varchar(50) NOT NULL,
  PRIMARY KEY (`statusID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `targettype` */

DROP TABLE IF EXISTS `targettype`;

CREATE TABLE `targettype` (
  `targetTypeID` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `targetName` varchar(50) DEFAULT NULL,
  `parentID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`targetTypeID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `taskID` varchar(50) NOT NULL,
  `taskname` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `userID` varchar(50) DEFAULT NULL,
  `statusID` varchar(50) DEFAULT NULL,
  `resultTypeID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`taskID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `tree_data` */

DROP TABLE IF EXISTS `tree_data`;

CREATE TABLE `tree_data` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL,
  `isParent` varchar(1) DEFAULT NULL,
  `flag` varchar(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userID` varchar(50) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `note` text,
  `flag` int(11) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `realname` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `tel` varchar(20) NOT NULL,
  `collage` varchar(50) DEFAULT NULL,
  `roleID` varchar(50) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
