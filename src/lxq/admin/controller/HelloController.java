package lxq.admin.controller;

import java.util.List;

import com.base.BaseController;
import com.bean.Lottery;
import com.config.ControllerBind;

@ControllerBind(controllerKey = "/front/article")
public class HelloController extends BaseController{

	public void index(){
		List<Lottery> userInfo = Lottery.dao.find("select * from lottery");
		setAttr("namelist", userInfo);
		render("/admin/index.html");
	}
	
	public void addUser(){
		Lottery model = getModel(Lottery.class,"userInfo");
		model.save();
		index();
	}
	
	public void delUser(){
		Lottery model = getModel(Lottery.class);
		model.put("id", getParaToInt("ID"));
		model.delete();
		index();
	}
	
	public void updUser(){
		Lottery model = getModel(Lottery.class);
		List<Lottery> umesList = model.find("SELECT * FROM lottery WHERE id = '"+getParaToInt("ID")+"'");
		renderJson(umesList.get(0));
	}
	
	public void saveUpd(){
		Lottery model = getModel(Lottery.class,"userInfo");
		model.update();
		index();
	}
	
	public void findDate(){
		List<Lottery> userInfo = Lottery.dao.find("select * from lottery where username like '%"+getPara("username").trim()+"%'");
		setAttr("namelist", userInfo);
		setAttr("upstatus",0);
		render("/admin/index.html");
	}
	
	public void test(){
		String canshu = getPara("canshu");
		System.out.println("ÈòøËê®Âæ∑Ôºö"+canshu);
		renderJson("{\"state\":\"success\",\"msg\":\"≥…π¶£°\"}");
	}

}
