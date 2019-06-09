CREATE TABLE `http_access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `http_status_code` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `protocol_info` varchar(255) DEFAULT NULL,
  `request_detail` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1514293 DEFAULT CHARSET=latin1;

CREATE TABLE `blocked_ip` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `block_reason` varchar(255) DEFAULT NULL,
  `blocking_date` datetime DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

