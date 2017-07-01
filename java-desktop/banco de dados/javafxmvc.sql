-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 01, 2017 at 03:46 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `javafxmvc`
--

-- --------------------------------------------------------

--
-- Table structure for table `categorias`
--

CREATE TABLE `categorias` (
  `cdCategoria` int(11) NOT NULL,
  `descricao` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categorias`
--

INSERT INTO `categorias` (`cdCategoria`, `descricao`) VALUES
(1, 'Eletrônicos'),
(2, 'Vesturário'),
(3, 'Eletrodomésticos');

-- --------------------------------------------------------

--
-- Table structure for table `clientes`
--

CREATE TABLE `clientes` (
  `cdCliente` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cpf` varchar(50) NOT NULL,
  `telefone` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clientes`
--

INSERT INTO `clientes` (`cdCliente`, `nome`, `cpf`, `telefone`) VALUES
(1, 'Rafael', '111.111.111-11', '(11) 1111-1111'),
(2, 'João Paulo', '222.222.222-22', '(22) 2222-2222'),
(3, 'Maria', '333.333.333-33', '(33) 3333-3333');

-- --------------------------------------------------------

--
-- Table structure for table `consultas`
--

CREATE TABLE `consultas` (
  `cdConsulta` int(10) NOT NULL,
  `cdCliente` int(11) NOT NULL,
  `valor` double(10,2) NOT NULL,
  `pago` tinyint(1) NOT NULL,
  `data` date NOT NULL,
  `estado` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `consultas`
--

INSERT INTO `consultas` (`cdConsulta`, `cdCliente`, `valor`, `pago`, `data`, `estado`) VALUES
(29, 2, 16550.00, 0, '2017-06-07', 'Pendente'),
(30, 2, 6000.00, 0, '2017-06-08', 'Pendente'),
(31, 3, 215.00, 0, '2017-06-02', 'Pendente');

-- --------------------------------------------------------

--
-- Table structure for table `itensdeconsulta`
--

CREATE TABLE `itensdeconsulta` (
  `cdItemDeConsulta` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `valor` double(10,2) NOT NULL,
  `cdProduto` int(11) NOT NULL,
  `cdConsulta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `itensdeconsulta`
--

INSERT INTO `itensdeconsulta` (`cdItemDeConsulta`, `quantidade`, `valor`, `cdProduto`, `cdConsulta`) VALUES
(69, 1, 550.00, 3, 29),
(70, 2, 6000.00, 2, 29),
(71, 3, 6000.00, 1, 29),
(72, 2, 4000.00, 1, 29),
(73, 2, 6000.00, 2, 30),
(74, 1, 215.00, 4, 31);

-- --------------------------------------------------------

--
-- Table structure for table `itensdevenda`
--

CREATE TABLE `itensdevenda` (
  `cdItemDeVenda` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `valor` float NOT NULL,
  `cdProduto` int(11) DEFAULT NULL,
  `cdVenda` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `itensdevenda`
--

INSERT INTO `itensdevenda` (`cdItemDeVenda`, `quantidade`, `valor`, `cdProduto`, `cdVenda`) VALUES
(1, 1, 2000, 1, 1),
(2, 1, 3000, 2, 1),
(3, 1, 550, 3, 2),
(4, 1, 215, 4, 2);

-- --------------------------------------------------------

--
-- Table structure for table `procedimentos`
--

CREATE TABLE `procedimentos` (
  `cdProcedimento` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `ano` int(10) NOT NULL,
  `total` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `procedimentos`
--

INSERT INTO `procedimentos` (`cdProcedimento`, `nome`, `ano`, `total`) VALUES
(1, 'Blefaroplastia', 2015, 2500.50),
(2, 'Lipoaspiração', 2015, 2500.50),
(3, 'Aumento de mamas', 2015, 2500.50),
(4, 'Enxerto de gordura', 2015, 2500.50),
(5, 'Rinoplastia', 2015, 2500.50),
(6, 'Toxina Botulínica ', 2016, 2500.50),
(7, 'Ácido Hialurônico ', 2016, 2500.50),
(8, 'Remoção de pelos ', 2016, 2500.50),
(9, 'Peeling químico ', 2016, 2500.50),
(10, 'rejuvenescimento da pele a laser ', 2016, 2500.50),
(11, 'Aumento de mamas\r\n', 2017, 2500.50),
(12, 'Lipoaspiração\r\n\r\n', 2017, 2500.50),
(13, 'Blefaroplastia', 2017, 2500.50),
(14, 'Enxerto de gordura\r\n', 2017, 2500.50),
(15, 'Abdominoplastia', 2017, 2500.50);

-- --------------------------------------------------------

--
-- Table structure for table `produtos`
--

CREATE TABLE `produtos` (
  `cdProduto` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `preco` float NOT NULL,
  `quantidade` int(11) NOT NULL,
  `cdCategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produtos`
--

INSERT INTO `produtos` (`cdProduto`, `nome`, `preco`, `quantidade`, `cdCategoria`) VALUES
(1, 'TV 32 Sony', 2000, 8970, 1),
(2, 'TV 40 Sony', 3000, 931, 1),
(3, 'Tênis de rico 40', 550, 980, 1),
(4, 'Tênis Nike', 215, 1, 2),
(6, 'Video Game3', 1850, 253, 2);

-- --------------------------------------------------------

--
-- Table structure for table `servicos`
--

CREATE TABLE `servicos` (
  `cdservico` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `preco` double(10,2) DEFAULT NULL,
  `descricao` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `servicos`
--

INSERT INTO `servicos` (`cdservico`, `nome`, `preco`, `descricao`) VALUES
(1, 'redução de gordura', 500.00, 'ddads'),
(3, 'Limpeza de pele', 500.00, 'Muito legal'),
(4, 'Preenchimento facil', 455.00, 'Preenchimento facil'),
(5, 'Procedimento estético I', 45454.00, 'procedimento estético I'),
(6, 'procedimento estéticio II', 455.00, 'procedimento estético');

-- --------------------------------------------------------

--
-- Table structure for table `vendas`
--

CREATE TABLE `vendas` (
  `cdVenda` int(11) NOT NULL,
  `data` date NOT NULL,
  `valor` float NOT NULL,
  `pago` tinyint(1) NOT NULL,
  `cdCliente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vendas`
--

INSERT INTO `vendas` (`cdVenda`, `data`, `valor`, `pago`, `cdCliente`) VALUES
(1, '2017-06-13', 5000, 0, 1),
(2, '2017-06-06', 765, 0, 1),
(3, '2017-06-06', 4000, 0, 2),
(4, '2017-05-30', 15000, 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`cdCategoria`);

--
-- Indexes for table `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`cdCliente`);

--
-- Indexes for table `consultas`
--
ALTER TABLE `consultas`
  ADD PRIMARY KEY (`cdConsulta`),
  ADD KEY `cdCliente` (`cdCliente`);

--
-- Indexes for table `itensdeconsulta`
--
ALTER TABLE `itensdeconsulta`
  ADD PRIMARY KEY (`cdItemDeConsulta`),
  ADD KEY `cdProduto` (`cdProduto`),
  ADD KEY `cdConsulta` (`cdConsulta`);

--
-- Indexes for table `itensdevenda`
--
ALTER TABLE `itensdevenda`
  ADD PRIMARY KEY (`cdItemDeVenda`),
  ADD KEY `fk_itensdevenda_produtos` (`cdProduto`),
  ADD KEY `fk_itensdevenda_vendas` (`cdVenda`);

--
-- Indexes for table `procedimentos`
--
ALTER TABLE `procedimentos`
  ADD PRIMARY KEY (`cdProcedimento`);

--
-- Indexes for table `produtos`
--
ALTER TABLE `produtos`
  ADD PRIMARY KEY (`cdProduto`),
  ADD KEY `fk_produtos_categorias` (`cdCategoria`);

--
-- Indexes for table `servicos`
--
ALTER TABLE `servicos`
  ADD PRIMARY KEY (`cdservico`);

--
-- Indexes for table `vendas`
--
ALTER TABLE `vendas`
  ADD PRIMARY KEY (`cdVenda`),
  ADD KEY `fk_vendas_clientes` (`cdCliente`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categorias`
--
ALTER TABLE `categorias`
  MODIFY `cdCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `clientes`
--
ALTER TABLE `clientes`
  MODIFY `cdCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `consultas`
--
ALTER TABLE `consultas`
  MODIFY `cdConsulta` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `itensdeconsulta`
--
ALTER TABLE `itensdeconsulta`
  MODIFY `cdItemDeConsulta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;
--
-- AUTO_INCREMENT for table `itensdevenda`
--
ALTER TABLE `itensdevenda`
  MODIFY `cdItemDeVenda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `procedimentos`
--
ALTER TABLE `procedimentos`
  MODIFY `cdProcedimento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `produtos`
--
ALTER TABLE `produtos`
  MODIFY `cdProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `servicos`
--
ALTER TABLE `servicos`
  MODIFY `cdservico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `vendas`
--
ALTER TABLE `vendas`
  MODIFY `cdVenda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `consultas`
--
ALTER TABLE `consultas`
  ADD CONSTRAINT `consultas_ibfk_1` FOREIGN KEY (`cdCliente`) REFERENCES `clientes` (`cdCliente`);

--
-- Constraints for table `itensdeconsulta`
--
ALTER TABLE `itensdeconsulta`
  ADD CONSTRAINT `itensdeconsulta_ibfk_1` FOREIGN KEY (`cdProduto`) REFERENCES `produtos` (`cdProduto`),
  ADD CONSTRAINT `itensdeconsulta_ibfk_2` FOREIGN KEY (`cdConsulta`) REFERENCES `consultas` (`cdConsulta`);

--
-- Constraints for table `itensdevenda`
--
ALTER TABLE `itensdevenda`
  ADD CONSTRAINT `fk_itensdevenda_produtos` FOREIGN KEY (`cdProduto`) REFERENCES `produtos` (`cdProduto`),
  ADD CONSTRAINT `fk_itensdevenda_vendas` FOREIGN KEY (`cdVenda`) REFERENCES `vendas` (`cdVenda`);

--
-- Constraints for table `produtos`
--
ALTER TABLE `produtos`
  ADD CONSTRAINT `fk_produtos_categorias` FOREIGN KEY (`cdCategoria`) REFERENCES `categorias` (`cdCategoria`);

--
-- Constraints for table `vendas`
--
ALTER TABLE `vendas`
  ADD CONSTRAINT `fk_vendas_clientes` FOREIGN KEY (`cdCliente`) REFERENCES `clientes` (`cdCliente`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
