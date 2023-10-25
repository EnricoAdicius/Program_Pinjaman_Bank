/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 10.4.11-MariaDB : Database - tubes_pbo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tubes_pbo` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `tubes_pbo`;

/*Table structure for table `jaminan` */

DROP TABLE IF EXISTS `jaminan`;

CREATE TABLE `jaminan` (
  `Kode_Properti` varchar(10) NOT NULL,
  `Nama_Properti` varchar(20) DEFAULT NULL,
  `Harga_properti` int(20) DEFAULT NULL,
  `Status_J` int(1) DEFAULT NULL,
  PRIMARY KEY (`Kode_Properti`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `jaminan` */

insert  into `jaminan`(`Kode_Properti`,`Nama_Properti`,`Harga_properti`,`Status_J`) values 
('01','Emas',4500000,1),
('02','Rumah',10000000,1),
('03','BPKB',500000,1);

/*Table structure for table `karyawan` */

DROP TABLE IF EXISTS `karyawan`;

CREATE TABLE `karyawan` (
  `NIP` int(8) NOT NULL,
  `Nama_Karyawan` varchar(20) DEFAULT NULL,
  `Alamat` varchar(20) DEFAULT NULL,
  `Status_K` int(1) DEFAULT NULL,
  PRIMARY KEY (`NIP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `karyawan` */

insert  into `karyawan`(`NIP`,`Nama_Karyawan`,`Alamat`,`Status_K`) values 
(101184,'Yuda Keling','Bandung',1),
(101185,'Dobleh','Bandung',1),
(101188,'Siti','Cimahi',1);

/*Table structure for table `nasabah` */

DROP TABLE IF EXISTS `nasabah`;

CREATE TABLE `nasabah` (
  `CIF` int(6) NOT NULL,
  `Nama_Nasabah` varchar(30) DEFAULT NULL,
  `Alamat` varchar(30) DEFAULT NULL,
  `Status_N` int(1) DEFAULT NULL,
  PRIMARY KEY (`CIF`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `nasabah` */

insert  into `nasabah`(`CIF`,`Nama_Nasabah`,`Alamat`,`Status_N`) values 
(10112,'Yuda','Bandung',1),
(10113,'Cecep','Jakarta',1),
(10115,'Agung','Jakarta',1),
(10119,'Andika','Cimahi',1);

/*Table structure for table `pinjaman` */

DROP TABLE IF EXISTS `pinjaman`;

CREATE TABLE `pinjaman` (
  `CIF` int(18) DEFAULT NULL,
  `Rekening` int(16) NOT NULL,
  `Saldo` int(20) DEFAULT NULL,
  `Kode_Properti` varchar(10) DEFAULT NULL,
  `Limit_Kredit` int(20) DEFAULT NULL,
  `Jangka_Waktu` varchar(10) DEFAULT NULL,
  `Total_Angsuran` varchar(15) DEFAULT NULL,
  `NIP` int(15) DEFAULT NULL,
  `Status_P` int(1) DEFAULT NULL,
  PRIMARY KEY (`Rekening`),
  KEY `CIF` (`CIF`),
  KEY `NIP` (`NIP`),
  KEY `Kode_Properti` (`Kode_Properti`),
  CONSTRAINT `pinjaman_ibfk_1` FOREIGN KEY (`CIF`) REFERENCES `nasabah` (`CIF`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pinjaman_ibfk_2` FOREIGN KEY (`NIP`) REFERENCES `karyawan` (`NIP`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pinjaman_ibfk_3` FOREIGN KEY (`Kode_Properti`) REFERENCES `jaminan` (`Kode_Properti`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `pinjaman` */

insert  into `pinjaman`(`CIF`,`Rekening`,`Saldo`,`Kode_Properti`,`Limit_Kredit`,`Jangka_Waktu`,`Total_Angsuran`,`NIP`,`Status_P`) values 
(10112,11224455,7000000,'01',6000000,'12','3000000',101184,1),
(10113,22002200,9000000,'01',8000000,'8','2000000',101185,1),
(10115,22557788,1200000,'02',2000000,'24','1600000',101185,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
