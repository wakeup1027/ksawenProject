package lxq.user.controller;

import java.util.List;

import lxq.user.util.TaskNumber;
import lxq.user.util.TaskTimer;

import com.alibaba.fastjson.JSONObject;
import com.base.BaseController;
import com.bean.IsAutoStart;
import com.bean.Lottery;
import com.bean.LotteryLog;
import com.bean.OpenNum;
import com.bean.TaskTimerBean;
import com.bean.TimeLong;
import com.config.ControllerBind;

@ControllerBind(controllerKey = "/info")
public class Controller extends BaseController {
	
	//=======主页========
	public void index(){
		render("/admin/home.html");
	}
	
	//=======设置时长=======
	public void LongTime(){
		JSONObject json = new JSONObject();
		TimeLong tlong = TimeLong.dao.findById(4);
		tlong.set("timelong", getParaToInt("time"));
		boolean tl = tlong.update();
		if(tl){
			json.put("state", "success");
			json.put("msg", "修改成功！");
			json.put("val", getParaToInt("time"));
		}else{
			json.put("state", "error");
			json.put("msg", "修改失败！请稍后再试！");
		}
		renderJson(json.toJSONString());
	}
	
	public void getNowTime(){
		TimeLong tlong = TimeLong.dao.findById(4);
		setAttr("tlong",tlong);
		render("/admin/timelong.html");
	}
	
	//===============未开奖的号码============================
	public void inpNum(){
		List<Lottery> list = Lottery.dao.find("SELECT * FROM lottery ORDER BY creantime DESC");
		setAttr("dateList",list);
		render("/admin/kaijianNum.html");
	}
	
	public void saveNum(){
		JSONObject json = new JSONObject();
		Lottery ltt = new Lottery();
		ltt.set("Num",Integer.parseInt(getPara("firstNum")+""+getPara("secondNum")+""+getPara("threeNum")));
		ltt.set("creantime",getNow());
		if(ltt.save()){
			json.put("state", "success");
			json.put("msg", "号码录入成功！");
		}else{
			json.put("state", "error");
			json.put("msg", "号码录入失败！请稍后再试！");
		}
		renderJson(json.toJSONString());
	}
	
	public void delNum(){
		JSONObject json = new JSONObject();
		int numid = getParaToInt();
		Lottery ltt = Lottery.dao.findById(numid);
		ltt.delete();
		json.put("state", "success");
		json.put("msg", "号码删除成功！");
		renderJson(json.toJSONString());
	}
	
	//===============一开奖的号码===============
	public void getoverList(){
		List<LotteryLog> list = LotteryLog.dao.find("SELECT * FROM lottery_log ORDER BY creantime DESC");
		setAttr("dateList",list);
		render("/admin/overNum.html");
	}
	
	//========启动开奖器========
	public void start(){
		TimeLong tlong = TimeLong.dao.findById(4);
		new TaskTimer().startTask(tlong.getInt("timelong"));//这个定时器是开奖定时器
		new TaskNumber().startTask();//这个定时器是同步前后端的倒计时定时器
		JSONObject json = new JSONObject();
		json.put("state", true);
		renderJson(json.toJSONString());
	}
	
	public void stop(){
		System.out.println("我执行结束开奖器的功能");
		JSONObject json = new JSONObject();
		json.put("state", new TaskTimer().stopTimer());
		new TaskNumber().stopTimer(); //把倒计时也重置一下
		renderJson(json.toJSONString());
	}
	
	//获取定时状态
	public void getTaskStautus(){
		TaskTimerBean tkb = TaskTimerBean.dao.findById(1);
		setAttr("tkb",tkb);
		render("/admin/TaskTime.html");
	}
	
	//设置奖池的号码为空的时候是否自动开奖
	public void setAutoStautus(){
		IsAutoStart tkb = IsAutoStart.dao.findById(1);
		setAttr("tkb",tkb);
		render("/admin/isAutoStart.html");
	}
	
	//关闭自动开奖的设置
	public void stopAuto(){
		IsAutoStart ysd = IsAutoStart.dao.findById(1);
		ysd.set("status", 0);
		JSONObject json = new JSONObject();
		if(ysd.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	/**
	 * 开启自动开奖的设置
	 */
	public void starAuto(){
		IsAutoStart ysd = IsAutoStart.dao.findById(1);
		ysd.set("status", 1);
		JSONObject json = new JSONObject();
		if(ysd.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
	/**
	 * 设置一天开几期的界面
	 */
	public void openHtml(){
		OpenNum tkb = OpenNum.dao.findById(1);
		setAttr("tkb",tkb);
		render("/admin/openNum.html");
	}
	
	/**
	 * 设置一天开几期
	 */
	public void SetOpenNum(){
		OpenNum tkb = OpenNum.dao.findById(1);
		tkb.set("openNum", getParaToInt("time"));
		tkb.set("spareNum", getParaToInt("time"));
		tkb.set("nowNum", 1);
		JSONObject json = new JSONObject();
		if(tkb.update()){
			json.put("state", "success");
		}else{
			json.put("state", "error");
		}
		renderJson(json.toJSONString());
	}
	
}
