project:

  INSERT INTO `sys_merchant_info` (`id`, `name`, `info`, `a_time`, `c_time`, `flag`, `merch_id`, `s_conf`)
VALUES
  (1, '腾讯视频好时光', '平台运营商，创梦天地', '2015-10-13 20:57:51', '2018-06-22 19:05:03', 0, NULL, NULL);

  INSERT INTO `sys_project_info` (`id`, `proj_name`, `proj_status`, `logo`, `a_time`, `c_time`, `flag`, `config`, `domain`, `proj_key`, `merch_id`, `custom_phone`, `template`, `info`, `brief`, `sms_phone`, `root`, `proj_biz_clas`, `proj_type`, `indemc`, `default_index`, `sms_tpl`, `i_p_unique`, `is_p_plat`, `admin_tpl`, `fskin`, `depmode`, `depmac`, `user_id`, `user_name`)
VALUES
  (1, '腾讯视频好时光', '10', 'pl_dd7b75d8dd3fa89f9d95882b17179124', '2018-06-21 17:26:58', '2018-09-14 09:01:17', 0, NULL, 'test.m.iplaysky.com', '92yp', 0, '', NULL, '项目说明.\r\n介绍信息.', '来体验吧', NULL, 1, 0, '1000', 9, NULL, NULL, 1, 0, NULL, NULL, 0, NULL, 0, NULL);

  INSERT INTO `sys_domain_object` (`id`, `dtype`, `domain`, `parent_domain`, `oid`, `c_time`, `flag`)
VALUES
  (1, 'com.jeancoder.project.ready.dto.SysProjectInfo', 'test.m.iplaysky.com', NULL, 1, '2018-06-26 15:13:56', 0);


INSERT INTO `t_admin_user` (`id`, `name`, `password`, `mobile`, `c_time`, `a_time`, `flag`, `status`, `username`, `mv`, `pid`)
VALUES
  (1, 'super.admin', 'c4ca4238a0b923820dcc509a6f75849b', '13501020884', '2018-09-10 16:49:10', '2015-10-12 20:44:50', 0, '00', 'te', 1, 1);


  INSERT INTO `t_admin_role` (`id`, `role_name`, `role_type`, `flag`, `c_time`, `a_time`, `pid`)
VALUES
  (1, '超级管理员', '0000', 0, '2018-08-21 16:47:23', '2015-10-12 20:45:31', 1);


  INSERT INTO `t_admin_user_role` (`id`, `user_id`, `role_id`, `c_time`, `flag`)
VALUES
  (1, 1, 1, '2018-06-29 12:56:37', 0);


INSERT INTO `t_admin_function` (`id`, `func_name`, `func_ss`, `func_info`, `parent_id`, `func_type`, `c_time`, `a_time`, `flag`, `level`, `click_url`, `whole`, `hasson`, `limpro`, `sort`)
VALUES
  (1, '权限管理', 'fa-sitemap', '权限系统', 76, '1000', '2018-07-02 19:33:44', '2015-05-08 20:00:00', 0, 1, 'auth', 0, 0, '1', 31),
  (2, '角色管理', '', '角色管理', 1, '1000', '2018-06-29 11:44:30', '2015-05-08 20:00:00', 0, 2, 'auth/role/list', 1, 0, '1', 314),
  (3, '用户管理', '', '后台用户', 1, '1000', '2018-07-03 23:16:23', '2015-05-08 20:00:00', 0, 2, 'auth/user/list', 1, 0, '1', 313),
  (5, '功能管理', '', '功能管理', 21, '1000', '2018-06-29 14:25:39', '2015-05-08 20:00:00', 0, 2, 'func/list', 1, 0, '0', 31),
  (21, '系统配置', 'fa-pie-chart', '', 76, '1000', '2018-07-02 19:33:33', '2015-09-30 13:03:04', 0, 1, 'project', 0, 0, '0', 3),
  (22, '项目管理', '', '', 21, '1000', '2018-06-29 16:14:25', '2015-09-30 13:09:22', 0, 2, 'project/list', 1, 0, '0', 32),
  (23, '授权管理', NULL, '授权系统功能', 1, '0000', '2018-06-29 11:44:53', '2015-10-12 21:57:57', 0, 2, 'auth/grant', 1, 0, '1', 312),
  (24, '系统商家', '', '商户相关功能管理', 21, '1000', '2017-12-20 17:55:02', '2015-10-13 20:24:30', 0, 2, 'admin/sys/merch', 1, 0, '0', 35),
  (25, '商户模块配置', '', '', 21, '0000', '2017-12-20 17:55:02', '2015-10-15 23:34:17', 0, 2, 'admin/sys/func', 1, 0, '0', 33),
  (43, '模板管理', '', '系统模板管理', 21, '1000', '2017-12-20 17:55:02', '2016-03-19 15:56:26', 0, 2, 'admin/sys/template', 1, 0, '0', 34),
  (48, '密码重置', NULL, '', 1, '0000', '2018-06-29 11:44:58', '2016-06-20 12:11:36', 0, 2, 'adauth/upwd', 1, 0, '1', 311),
  (49, '配置数据', '', '', 21, '1000', '2017-12-20 17:55:02', '2016-07-27 11:06:13', 0, 2, 'admin/sys/config', 1, 0, '0', 36),
  (86, '项目UI管理', '', 'admin/sys/project/ui', 21, '1000', '2017-12-20 17:55:02', '2017-01-24 01:06:27', 0, 2, 'admin/sys/project/ui', 1, 0, '0', 37),
  (272, '门店管理', 'fa-bank', '门店管理', NULL, '1000', '2018-07-02 11:16:27', '2018-07-01 09:22:13', 0, 1, 'store/list', 1, 0, '1', NULL),
  (274, '组织架构', 'fa-cubes', '', 1, '1000', '2018-07-02 12:15:46', '2018-07-02 11:07:55', 0, 2, 'org/list', 1, 0, '1', NULL);

INSERT INTO `t_admin_role_function` (`id`, `role_id`, `func_id`, `c_time`, `flag`)
VALUES
  (1, 1, 2, '2018-06-29 11:43:09', 0),
  (2, 1, 3, '2018-06-29 11:43:08', 0),
  (3, 1, 22, '2018-06-29 11:43:04', 0);




INSERT INTO `t_admin_role_mod_mm` (`id`, `role_id`, `func`, `appcode`, `a_time`, `c_time`, `flag`)
VALUES
  (8, 1, 'd0f41d47849052bdef8da905df24daff', 'project', '2018-08-14 19:12:08', '2018-08-14 19:12:08', 0),
  (11, 1, '4e62890302b020ac65610e58ce6df085', 'project', '2018-08-14 20:11:05', '2018-08-14 20:11:05', 0),
  (16, 1, '391907e66c7e2eaba0458dd763d8fe0e', 'project', '2018-08-14 20:42:14', '2018-08-14 20:42:14', 0),
  (17, 1, '4f084ccb2d10792099ea4b6d2d25d328', 'project', '2018-08-17 19:38:12', '2018-08-17 19:38:12', 0),
  (18, 1, '557bb0ef99db4337b1c1727fd3a01c2a', 'project', '2018-08-17 19:38:12', '2018-08-17 19:38:12', 0),
  (19, 1, 'bf779478a46175b019ea2360ee6e2af7', 'project', '2018-08-17 19:38:13', '2018-08-17 19:38:13', 0),
  (20, 1, 'ce8d00b8106b6ffeb375f9d6ab43c8c3', 'project', '2018-09-10 16:58:26', '2018-09-10 16:58:26', 0);



