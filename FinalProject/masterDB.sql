-- phpMyAdmin SQL Dump
-- version 4.4.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 16, 2015 at 01:58 
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
-- Table structure for table `listBus`
--

CREATE TABLE IF NOT EXISTS `listBus` (
  `kode` varchar(10) NOT NULL,
  `nm_bus` varchar(20) NOT NULL,
  `jmlh_Bus` int(5) NOT NULL,
  `jmlh_Bangku` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listBus`
--

INSERT INTO `listBus` (`kode`, `nm_bus`, `jmlh_Bus`, `jmlh_Bangku`) VALUES
('01ECO', 'Gumarang', 2, 20),
('01VIP', 'Gumarang', 4, 30),
('02AC', 'Gumarang', 8, 30),
('02PTS', 'Gumarang', 4, 40);

-- --------------------------------------------------------

--
-- Table structure for table `listPerjalanan`
--

CREATE TABLE IF NOT EXISTS `listPerjalanan` (
  `kd_prjlnan` varchar(10) NOT NULL,
  `ID_bus` varchar(20) NOT NULL,
  `region` varchar(15) NOT NULL,
  `waktu` varchar(10) NOT NULL,
  `tujuan` varchar(20) NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listPerjalanan`
--

INSERT INTO `listPerjalanan` (`kd_prjlnan`, `ID_bus`, `region`, `waktu`, `tujuan`, `harga`) VALUES
('001', '01ECO', 'Jawa', 'Siang', 'Jakarta', 200000),
('002', '01VIP', 'Jawa', 'Malam', 'Jakarta', 300000),
('003', '02AC', 'Jawa', 'Siang', 'Jakarta', 400000),
('004', '02PTS', 'Jawa', 'Siang', 'Jakarta', 300000),
('005', '01ECO', 'Sumatra', 'Siang', 'Riau', 250000),
('006', '01VIP', 'Sumatra', 'Malam', 'Riau', 400000),
('007', '02AC', 'Sumatra', 'Siang', 'Riau', 500000),
('008', '02PTS', 'Sumatra', 'Malam', 'Riau', 580000),
('009', '01ECO', 'Kalimantan', 'Siang', 'Pontianak', 400000),
('010', '01VIP', 'Kalimantan', 'Siang', 'Pontianak', 480000),
('011', '02AC', 'Kalimantan', 'Siang', 'Pontianak', 580000),
('012', '02PTS', 'Kalimantan', 'Siang', 'Pontianak', 680000),
('013', '01ECO', 'Sulawesi', 'Siang', 'Makassar', 580000),
('014', '01VIP', 'Sulawesi', 'Makassar', 'Malam', 680000),
('015', '02AC', 'Sulawesi', 'Siang', 'Makassar', 730000),
('016', '02PTS', 'Sulawesi', 'Siang', 'Makassar', 789000);

-- --------------------------------------------------------

--
-- Table structure for table `listTransaksi`
--

CREATE TABLE IF NOT EXISTS `listTransaksi` (
  `ID_trans` varchar(10) NOT NULL,
  `waktu` varchar(10) NOT NULL,
  `nm_customer` varchar(20) NOT NULL,
  `contact` varchar(15) NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listTransaksi`
--

INSERT INTO `listTransaksi` (`ID_trans`, `waktu`, `nm_customer`, `contact`, `total`) VALUES
('1', '2015-6- 11', 'rolies', '085330', 300000),
('2', '2015-6- 12', 'Makhi', '085330793203', 300000),
('3', '2015-6- 15', 'Faisyal', '085330793203', 300000),
('4', '2015-6- 15', 'Jarib', '123', 300000);

-- --------------------------------------------------------

--
-- Table structure for table `listTransaksiDetail`
--

CREATE TABLE IF NOT EXISTS `listTransaksiDetail` (
  `ID_trans` varchar(10) NOT NULL,
  `ID_route` varchar(10) NOT NULL,
  `no_bgku` int(3) NOT NULL,
  `harga` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listTransaksiDetail`
--

INSERT INTO `listTransaksiDetail` (`ID_trans`, `ID_route`, `no_bgku`, `harga`) VALUES
('1', '003', 3, 300000),
('2', '003', 3, 300000),
('3', '006', 3, 400000);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `id` int(4) NOT NULL,
  `user_id` varchar(10) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(254) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `user_id`, `name`, `password`) VALUES
(407, 'rolies', 's. rolis', 'c684180210d57e2cced6e0cb59b620d9');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `listBus`
--
ALTER TABLE `listBus`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `listTransaksi`
--
ALTER TABLE `listTransaksi`
  ADD PRIMARY KEY (`ID_trans`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
