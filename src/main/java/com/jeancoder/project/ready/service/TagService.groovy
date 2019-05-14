package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.dto.StoreTag
import com.jeancoder.project.ready.helper.GlobalHolder

class TagService {

	static final TagService __instance__ = new TagService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	public static TagService INSTANCE() {
		return __instance__;
	}
	
	StoreTag get(def id) {
		if(!id) {
			return null;
		}
		return jc_template.get(StoreTag.class, 'select * from StoreTag where flag!=? and id=?', -1, id);
	}
	
	void update_tag(StoreTag tag) {
		jc_template.update(tag);
	}
	
	List<StoreTag> store_tags(StoreInfo store) {
		if(store==null) {
			return null;
		}
		String sql = 'select * from StoreTag where flag!=? and pid=? and store_id=?';
		return jc_template.find(StoreTag.class, sql, -1, GlobalHolder.proj.id, store.id);
	}
	
	void update_tags(StoreInfo store, def tags) {
		String sql = 'update StoreTag set flag=-1 where pid=? and store_id=?';
		jc_template.batchExecute(sql, GlobalHolder.proj.id, store.id);

		Timestamp a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());		
		for(x in tags) {
			if(x.toString().equals("")) {
				continue;
			}
			StoreTag st = new StoreTag();
			st.a_time = a_time;
			st.c_time = a_time;
			st.flag = 0;
			st.name = x;
			st.pid = GlobalHolder.proj.id;
			st.store_id = store.id;
			jc_template.save(st);
		}
	}
}
