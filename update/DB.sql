/** 20180910 **/
CREATE TABLE `rd_admg_ms` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `proj_id` bigint(20) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `uk` bigint(20) DEFAULT NULL,
  `un` varchar(255) DEFAULT NULL,
  `res` varchar(255) DEFAULT NULL,
  `ud` text,
  `addr` varchar(32) DEFAULT NULL,
  `exeres` text,
  `execode` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4559 DEFAULT CHARSET=utf8;


/*20181005*/
ALTER TABLE `sys_project_support_config` ADD COLUMN `sc_code_cat` VARCHAR(16) AFTER `sc_code`;


/*20181105*/
ALTER TABLE `sys_project_info` ADD COLUMN `dbpid` bigint(11) DEFAULT 1;
ALTER TABLE `sys_project_info` ADD COLUMN `rbpid` bigint(11) DEFAULT 1;


