-- phpMyAdmin SQL Dump
-- version 4.4.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 09, 2015 at 07:27 
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `masterDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `listPerjalanan`
--

CREATE TABLE IF NOT EXISTS `listPerjalanan` (
  `kd_prjlnan` varchar(10) NOT NULL,
  `nm_bus` varchar(20) NOT NULL,
  `jenis` varchar(15) NOT NULL,
  `region` varchar(10) NOT NULL,
  `tujuan` varchar(20) NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listPerjalanan`
--

INSERT INTO `listPerjalanan` (`kd_prjlnan`, `nm_bus`, `jenis`, `region`, `tujuan`, `harga`) VALUES
('001', 'Sumber Kencono', 'null', 'Jateng', 'Blora', 10000);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
