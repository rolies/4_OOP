-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2015 at 04:56 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `masterdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `listbus`
--

CREATE TABLE IF NOT EXISTS `listbus` (
  `kode` varchar(10) NOT NULL,
  `nm_bus` varchar(20) NOT NULL,
  `jmlh_Bus` int(5) NOT NULL,
  `jmlh_Bangku` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listbus`
--

INSERT INTO `listbus` (`kode`, `nm_bus`, `jmlh_Bus`, `jmlh_Bangku`) VALUES
('01ECO', 'Sumber Kencono', 2, 20),
('01VIP', 'Sumber Kencono', 4, 30),
('02AC', 'Gumarang', 8, 30),
('02PTS', 'Gumarang', 4, 40),
('02VIP', 'Gumarang', 91, 34);

-- --------------------------------------------------------

--
-- Table structure for table `listperjalanan`
--

CREATE TABLE IF NOT EXISTS `listperjalanan` (
  `kd_prjlnan` varchar(10) NOT NULL,
  `ID_bus` varchar(20) NOT NULL,
  `region` varchar(15) NOT NULL,
  `waktu` varchar(10) NOT NULL,
  `tujuan` varchar(20) NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listperjalanan`
--

INSERT INTO `listperjalanan` (`kd_prjlnan`, `ID_bus`, `region`, `waktu`, `tujuan`, `harga`) VALUES
('001', '01VIP', 'Jawa', 'Siang', 'Madiun', 60000),
('002', '02PTS', 'Sumatra', 'Malam', 'Palembang', 200000),
('003', '02AC', 'Sumatra', 'Siang', 'Lampung', 300000),
('004', '02AC', 'Kalimantan', 'Siang', 'Sampit', 300000),
('005', '01VIP', 'Sulawesi', 'Siang', 'Makassar', 500000),
('006', '02PTS', 'Bali & Nt', 'Malam', 'Lombok', 450000);

-- --------------------------------------------------------

--
-- Table structure for table `listtransaksi`
--

CREATE TABLE IF NOT EXISTS `listtransaksi` (
  `ID_trans` varchar(10) NOT NULL,
  `waktu` varchar(10) NOT NULL,
  `nm_customer` varchar(20) NOT NULL,
  `contact` varchar(15) NOT NULL,
  `total` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listtransaksi`
--

INSERT INTO `listtransaksi` (`ID_trans`, `waktu`, `nm_customer`, `contact`, `total`) VALUES
('1', '2015-6- 11', 'rolies', '085330', 300000);

-- --------------------------------------------------------

--
-- Table structure for table `listtransaksidetail`
--

CREATE TABLE IF NOT EXISTS `listtransaksidetail` (
  `ID_trans` varchar(10) NOT NULL,
  `ID_route` varchar(10) NOT NULL,
  `no_bgku` int(3) NOT NULL,
  `harga` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `listtransaksidetail`
--

INSERT INTO `listtransaksidetail` (`ID_trans`, `ID_route`, `no_bgku`, `harga`) VALUES
('1', '003', 3, 300000);

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
-- Indexes for table `listbus`
--
ALTER TABLE `listbus`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `listtransaksi`
--
ALTER TABLE `listtransaksi`
  ADD PRIMARY KEY (`ID_trans`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
