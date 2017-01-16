-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 15, 2017 at 02:08 PM
-- Server version: 5.7.16-log
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `database`
--

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `chat_id` int(11) NOT NULL,
  `chat_issuer` int(10) UNSIGNED NOT NULL,
  `event_id` int(10) UNSIGNED NOT NULL,
  `chat_message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `event_id` int(10) UNSIGNED NOT NULL,
  `event_name` varchar(100) NOT NULL,
  `event_admin` int(10) UNSIGNED NOT NULL,
  `event_description` text NOT NULL,
  `event_image` text NOT NULL,
  `event_created_at` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`event_id`, `event_name`, `event_admin`, `event_description`, `event_image`, `event_created_at`) VALUES
(3, 'event1', 5, 'Movies', 'empty', 1484333157),
(4, 'event2', 6, 'Travell', 'empty', 1484338557);

-- --------------------------------------------------------

--
-- Table structure for table `events_participants`
--

CREATE TABLE `events_participants` (
  `ep_id` int(10) UNSIGNED NOT NULL,
  `event_id` int(10) UNSIGNED NOT NULL,
  `member_id` int(10) UNSIGNED NOT NULL,
  `ep_status` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `events_participants`
--

INSERT INTO `events_participants` (`ep_id`, `event_id`, `member_id`, `ep_status`) VALUES
(1, 3, 6, 1),
(2, 3, 7, 1),
(5, 4, 7, 1),
(6, 4, 8, 1),
(9, 3, 8, 1),
(11, 4, 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `member_id` int(10) UNSIGNED NOT NULL,
  `member_name` varchar(50) NOT NULL,
  `gmail` varchar(100) NOT NULL,
  `udid` text NOT NULL,
  `member_account_status` tinyint(1) NOT NULL DEFAULT '1',
  `member_image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`member_id`, `member_name`, `gmail`, `udid`, `member_account_status`, `member_image`) VALUES
(5, 'sam1', 'sam5@gmail.com', '11111111111111111111111111111', 1, ''),
(6, 'sam2', 'sam6@gmail.com', '22222222222222222222222', 1, 'empty'),
(7, 'sam3', 'sam7@gmail.com', '11111111111111111111111111111', 1, ''),
(8, 'sam4', 'sam8@gmail.com', '22222222222222222222222', 1, 'empty');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(10) NOT NULL,
  `event_id` int(10) UNSIGNED NOT NULL,
  `transaction_issuer` int(10) UNSIGNED NOT NULL,
  `transaction_receiver` int(10) UNSIGNED NOT NULL,
  `transaction_amount` double NOT NULL,
  `transaction_description` text NOT NULL,
  `transaction_image` text NOT NULL,
  `transaction_created_at` int(11) UNSIGNED NOT NULL,
  `transaction_updated_at` int(11) UNSIGNED NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `event_id`, `transaction_issuer`, `transaction_receiver`, `transaction_amount`, `transaction_description`, `transaction_image`, `transaction_created_at`, `transaction_updated_at`) VALUES
(15, 3, 1, 2, 10, 'Food', '', 1484043357, 0),
(16, 3, 1, 2, 10, 'Food', '', 1484043357, 0),
(17, 3, 1, 2, -5, 'Loan', '', 1484043657, 0),
(18, 3, 1, 2, -5, 'Loan', '', 1484043657, 0),
(19, 3, 2, 1, 20, 'Drinks', '', 1484046117, 0),
(20, 3, 2, 1, 20, 'Drinks', '', 1484046117, 0),
(21, 4, 5, 6, 15, 'Gas', '', 1484047017, 0),
(22, 4, 5, 6, 15, 'Gas', '', 1484047017, 0),
(23, 4, 6, 5, 12, 'Taxi', '', 1484209017, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `image` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `mobile`, `image`) VALUES
(68, 'Sangaria', 'sangaria@gmail.com', '111111', 'http://10.0.0.11/retrofit_tutorial/UploadedFiles/b5487a00-e75c-4123-ab3a-aa8b52beb5bf_5872b4fe7eb38.jpg'),
(70, 'Bamboo Palace', 'bamboopalace@gmail.com', '08218563', 'http://10.0.0.11/retrofit_tutorial/UploadedFiles/5d5a2f1f-6548-4a58-b619-20668d25e2f7_5872b5b38764c.jpg'),
(71, 'Chili Masala', 'chilimasala@gmail.com', '12121212', 'http://10.0.0.11/retrofit_tutorial/UploadedFiles/b9f5c776-6d30-4931-b0bf-291d1c58653d_5872b5fad2d52.jpg'),
(73, 'Axels Grill', 'axel-grill@gmail.com', '0864592899', 'http://10.0.0.11/retrofit_tutorial/UploadedFiles/eacdaa67-9374-4676-9f7a-3e249f2dc597_5872b6a85f4e5.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`chat_id`),
  ADD KEY `issuer` (`chat_issuer`),
  ADD KEY `event_id` (`event_id`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`event_id`),
  ADD KEY `member_id` (`event_admin`);

--
-- Indexes for table `events_participants`
--
ALTER TABLE `events_participants`
  ADD PRIMARY KEY (`ep_id`),
  ADD KEY `event_id` (`event_id`),
  ADD KEY `member_id` (`member_id`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`member_id`),
  ADD UNIQUE KEY `gmail` (`gmail`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `event_id` (`event_id`),
  ADD KEY `transaction_issuer` (`transaction_issuer`) USING BTREE,
  ADD KEY `transaction_receiver` (`transaction_receiver`) USING BTREE;

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `chat_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `event_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `events_participants`
--
ALTER TABLE `events_participants`
  MODIFY `ep_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `member_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`chat_issuer`) REFERENCES `events_participants` (`ep_id`),
  ADD CONSTRAINT `chat_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`);

--
-- Constraints for table `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `events_ibfk_1` FOREIGN KEY (`event_admin`) REFERENCES `members` (`member_id`);

--
-- Constraints for table `events_participants`
--
ALTER TABLE `events_participants`
  ADD CONSTRAINT `events_participants_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`),
  ADD CONSTRAINT `events_participants_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`transaction_issuer`) REFERENCES `events_participants` (`ep_id`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`transaction_receiver`) REFERENCES `events_participants` (`ep_id`),
  ADD CONSTRAINT `transactions_ibfk_3` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
