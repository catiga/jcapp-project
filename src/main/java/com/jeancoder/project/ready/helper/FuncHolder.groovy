package com.jeancoder.project.ready.helper

import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.project.ready.appfunc.AppFunction
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.support.security.MD5Util

class FuncHolder {
	
	private FuncHolder() {}
	
	public static final FuncHolder INSTANCE = new FuncHolder();

	def _sys_func_list_ = [:];
	
	def apps() {
		return _sys_func_list_.keySet();
	}
	
	def add(def app_code, def funcs) {
		if(!funcs) return;
		for(x in funcs) {
			try {
				x.appcode = app_code;
				//设置当前id
				x.unicode = MD5Util.getMD5(x.click_url);
				//查找父级
				if(x.parent_id) {
					AppFunction parent = this.get_by_id(x.parent_id, _sys_func_list_.get(app_code));
					if(parent!=null) {
						x.parent_unicode = parent.unicode;
					}
				}
			} catch(any) {
			}
		}
		//更新
		_sys_func_list_.put(app_code, funcs);
	}
	
	def getall(def app_code) {
		return _sys_func_list_.get(app_code);
	}
	
	def get_appcode_funcs_by_ids(def appcode, def mod_list) {
		def result = _sys_func_list_.get(appcode);
		Map<String,String> retMap = new HashMap<String,String>();
		def ret = [];
		for(x in result) {
			mod_list.each({
				if(it==x.unicode) {
					if(retMap.get(x.unicode) == null) {
						ret.add(x);
					}
					retMap.put(x.unicode, x.unicode);
					if(x.parent_id) {
						def is_in = false;
						def parent = this.get_by_id(x.parent_id, result);
						ret.each {item-> if(item.id==parent.id) {is_in = true;}}
						if(!is_in)
							ret.add(parent);
					}
				} 
			});
		}
		return ret;
	}
	
	
	
	
	
	def get_by_id(def id, List<AppFunction> functions) {
		for(AppFunction f : functions) {
			if(f.id==id) {
				return f;
			}
		}
		return null;
	}
	
	
	
	def Map<AppFunction, List<AppFunction>> my_funcs(def appcode) {
		List<AppFunction> functions = _sys_func_list_.get(appcode);
		if(!functions) return null;
		
		Map<AppFunction, List<AppFunction>> parent_functions = new LinkedHashMap<AppFunction, List<AppFunction>>();
		SysProjectInfo project = GlobalHolder.getProj();
		if(functions!=null&&!functions.isEmpty()) {
			for(AppFunction f : functions) {
				AppFunction parent_f = null;
				List<AppFunction> result_f = new ArrayList<AppFunction>();
				
				//只取两级的判断
				if(f.getLevel().equals(1)) {
					//表示当前这个为一级模块
					parent_f = f;
					for(AppFunction f_2 : functions) {
						if('0000'.equals(f_2.getFunc_type())){
							continue;
						}
						if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
							if(f_2.getLimpro().equals("0")&&!project.root) {
								continue;
							}
							result_f.add(f_2);
						}
					}
				} else if(f.getLevel().equals(2)) {
					//表示当前这个为二级模块
					parent_f = get_by_id(f.getParent_id(), functions);
					if(parent_f==null) {
						continue;
					}
					for(AppFunction f_2 : functions) {
						if('0000'.equals(f_2.getFunc_type())){
							continue;
						}
						if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
							if(f_2.getLimpro().equals("0")&&!project.root) {
								continue;
							}
							result_f.add(f_2);
						}
					}
				}
				parent_functions.put(parent_f, result_f);
			}
		}
		return parent_functions;
	}
	
	
	
	
	
	def Map<AppFunction, List<AppFunction>> org_funcs(def appcode, def functions) {
		if(!functions) return null;
		
		Map<AppFunction, List<AppFunction>> parent_functions = new LinkedHashMap<AppFunction, List<AppFunction>>();
		SysProjectInfo project = GlobalHolder.getProj();
		if(functions!=null&&!functions.isEmpty()) {
			for(AppFunction f : functions) {
				AppFunction parent_f = null;
				List<AppFunction> result_f = new ArrayList<AppFunction>();
				
				//只取两级的判断
				if(f.getLevel().equals(1)) {
					//表示当前这个为一级模块
					parent_f = f;
					for(AppFunction f_2 : functions) {
						if('0000'.equals(f_2.getFunc_type())){
							continue;
						}
						if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
							if(f_2.getLimpro().equals("0")&&!project.root) {
								continue;
							}
							result_f.add(f_2);
						}
					}
				} else if(f.getLevel().equals(2)) {
					//表示当前这个为二级模块
					parent_f = get_by_id(f.getParent_id(), _sys_func_list_.get(appcode));
					if(parent_f==null) {
						continue;
					}
					for(AppFunction f_2 : functions) {
						if('0000'.equals(f_2.getFunc_type())){
							continue;
						}
						if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
							if(f_2.getLimpro().equals("0")&&!project.root) {
								continue;
							}
							result_f.add(f_2);
						}
					}
				}
				parent_functions.put(parent_f, result_f);
			}
		}
		return parent_functions;
	}
	
//	
//	public static void main (String[] arg) {
//		def result = '[{"id":1,"appcode":"crm","unicode":"6a992d5529f459a44fee58c733255e86","func_name":"会员规则管理","func_ss":"fa-shopping-cart","func_type":"1000","func_info":null,"parent_id":null,"parent_unicode":null,"c_time":null,"a_time":null,"flag":0,"level":1,"click_url":"index","whole":null,"hasson":null,"sort":null,"limpro":null},{"id":2,"appcode":"crm","unicode":"ee11cbb19052e40b07aac0ca060c23ee","func_name":"注册用户与会员","func_ss":"fa-shopping-cart","func_type":"1000","func_info":null,"parent_id":null,"parent_unicode":null,"c_time":null,"a_time":null,"flag":0,"level":1,"click_url":"user","whole":null,"hasson":null,"sort":null,"limpro":null},{"id":202,"appcode":"crm","unicode":"54c28ce7a06ca7440ff162a01b5f2c15","func_name":"会员管理","func_ss":"fa-shopping-cart","func_type":"1000","func_info":null,"parent_id":2,"parent_unicode":null,"c_time":null,"a_time":null,"flag":0,"level":2,"click_url":"mc/list","whole":null,"hasson":null,"sort":null,"limpro":null},{"id":201,"appcode":"crm","unicode":"35220e2a2abce3277a4a3c153a932228","func_name":"注册用户管理","func_ss":"fa-shopping-cart","func_type":"1000","func_info":null,"parent_id":2,"parent_unicode":null,"c_time":null,"a_time":null,"flag":0,"level":2,"click_url":"user/index","whole":null,"hasson":null,"sort":null,"limpro":null},{"id":3,"appcode":"crm","unicode":"0d6bc55feb9a213da2c9df21c723100f","func_name":"预制卡管理","func_ss":"fa-shopping-cart","func_type":"1000","func_info":null,"parent_id":null,"parent_unicode":null,"c_time":null,"a_time":null,"flag":0,"level":1,"click_url":"predo","whole":null,"hasson":null,"sort":null,"limpro":null},{"id":301,"appcode":"crm","unicode":"2c4aa2aa4d3b1dbb3f6b99bcc316f096","func_name":"预制卡订单","func_ss":"fa-shopping-cart","func_type":"1000","func_info":null,"parent_id":3,"parent_unicode":null,"c_time":null,"a_time":null,"flag":0,"level":2,"click_url":"predo/index","whole":null,"hasson":null,"sort":null,"limpro":null}]'
//		result = JackSonBeanMapper.jsonToList(result);
//		def mod_list = '["6a992d5529f459a44fee58c733255e86","ee11cbb19052e40b07aac0ca060c23ee","54c28ce7a06ca7440ff162a01b5f2c15","35220e2a2abce3277a4a3c153a932228","0d6bc55feb9a213da2c9df21c723100f","2c4aa2aa4d3b1dbb3f6b99bcc316f096","6a992d5529f459a44fee58c733255e86","ee11cbb19052e40b07aac0ca060c23ee","54c28ce7a06ca7440ff162a01b5f2c15","35220e2a2abce3277a4a3c153a932228","0d6bc55feb9a213da2c9df21c723100f","2c4aa2aa4d3b1dbb3f6b99bcc316f096"]';
//		mod_list = JackSonBeanMapper.jsonToList(mod_list)
//		//println "FuncHolder_result__" + JackSonBeanMapper.toJson(result);
//		//println "FuncHolder_mod_list__" + JackSonBeanMapper.toJson(mod_list);
//		Map<String,String> retMap = new HashMap<String,String>();
//		def ret = [];
//		for(x in result) {
//			mod_list.each({
//				if(it==x.unicode) {
//					if(retMap.get(x.unicode) == null) {
//						ret.add(x);
//					} 
//					retMap.put(x.unicode, x.unicode);
//					if(x.parent_id) {
//						def is_in = false;
//						def parent = INSTANCE.get_by_id(x.parent_id, result);
//						println "parent______" + JackSonBeanMapper.toJson(parent);
//						println "x________"+ JackSonBeanMapper.toJson(x);
//						ret.each {item-> 
//							if(item.id==parent.id) {
//								is_in = true;
//							}
//						}
//						if(!is_in)
//							ret.add(parent);
//					}
//				}
//			});
//		}
//		println JackSonBeanMapper.toJson(ret);
//	}
}
