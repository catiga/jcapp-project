package com.jeancoder.project.ready.service

import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class FuncServiceCarry extends CarryJcDaoTemplate<SysFunction> {

	static final FuncServiceCarry _instance_ = new FuncServiceCarry();
	
	public static FuncServiceCarry INSTANCE() {
		 return _instance_;
	}
	
	public void save_func(SysFunction e) {
		this.save(e);
	}
	
	public void update_func(SysFunction e) {
		this.update(e);
	}
	
	public List<SysFunction> find_project_functions(SysProjectInfo project) {
		String sql = "select * from SysFunction where flag!=? and id in (select mod_id from ProjectMod where flag!=? and proj_id=?)";
		return this.find(sql, -1, -1, project.id);
	}
	
	public List<SysFunction> find_mods() {
		String sql = "select * from SysFunction where id in (";
		sql = sql + "select parent_id from SysFunction where flag!=? and id in (select mod_id from ProjectMod where flag!=? and proj_id=?)";
		sql = sql + ")";
		List<SysFunction> result = this.find(sql, -1, -1, GlobalHolder.proj.id);
		return result;
	}
	
	public List<SysFunction> find_mod_funcs(SysFunction parent) {
		String sql = 'select * from SysFunction where flag!=? and parent_id=? and id in (select mod_id from ProjectMod where flag!=? and proj_id=?)';
		return this.find(sql, -1, parent.id, -1, GlobalHolder.proj.id);
	}
	
	public SysFunction get_by_id(def id) {
		if(!id) {
			return null;
		}
		return this.get("select * from SysFunction where flag!=? and id=?", -1, id);
	}
	
	public List<SysFunction> find_role_functions(AuthRole role) {
		if(role==null) {
			return null;
		}
		String sql = "select * from SysFunction where flag!=? and id in (select func_id from RoleFunction where flag!=? and role_id=?)";
		return this.find(sql, -1, -1, role.id);
	}
	
	public List<SysFunction> find_roles_functions(List<AuthRole> roles) {
		if(!roles) {
			return null;
		}
		String sql = "select * from SysFunction where flag!=? and id in (select func_id from RoleFunction where flag!=? and role_id in ("; //?))
		
		def ids = [-1, -1];
		def ask_plus = '';
		roles.each{
			ids.add(it.id);
			ask_plus+='?,';
		}
		sql = sql + ask_plus[0..-2] + '))';
		return this.find(sql, ids.toArray());
	}
	
	
	
	
	
	
	/**
	 * 0000 : button
	 * 1000 : left
	 * 
	 * @param functions
	 * @return
	 */
	public Map<SysFunction, List<SysFunction>> my_funcs(List<SysFunction> functions) {
		Map<SysFunction, List<SysFunction>> parent_functions = new LinkedHashMap<SysFunction, List<SysFunction>>();
		SysProjectInfo project = GlobalHolder.getProj();
		if(functions!=null&&!functions.isEmpty()) {
			for(SysFunction f : functions) {
				SysFunction parent_f = null;
				List<SysFunction> result_f = new ArrayList<SysFunction>();
				
				//只取两级的判断
				if(f.getLevel().equals(1)) {
					//表示当前这个为一级模块
					parent_f = f;
					for(SysFunction f_2 : functions) {
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
					parent_f = this.get_by_id(f.getParent_id());
					if(parent_f==null) {
						LOGGER.error("parent_function_not_found: parent_id=" + f.getParent_id());
						continue;
					}
					for(SysFunction f_2 : functions) {
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
				
//				if(parent_f.getLevel().equals(1)){
				parent_functions.put(parent_f, result_f);
//				}
				
			}
		}
		return parent_functions;
	}
	
}
