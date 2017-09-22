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
	
	//=======��ҳ========
	public void index(){
		render("/admin/home.html");
	}
	
	//=======����ʱ��=======
	public void LongTime(){
		JSONObject json = new JSONObject();
		TimeLong tlong = TimeLong.dao.findById(4);
		tlong.set("timelong", getParaToInt("time"));
		boolean tl = tlong.update();
		if(tl){
			json.put("state", "success");
			json.put("msg", "�޸ĳɹ���");
			json.put("val", getParaToInt("time"));
		}else{
			json.put("state", "error");
			json.put("msg", "�޸�ʧ�ܣ����Ժ����ԣ�");
		}
		renderJson(json.toJSONString());
	}
	
	public void getNowTime(){
		TimeLong tlong = TimeLong.dao.findById(4);
		setAttr("tlong",tlong);
		render("/admin/timelong.html");
	}
	
	//===============δ�����ĺ���============================
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
			json.put("msg", "����¼��ɹ���");
		}else{
			json.put("state", "error");
			json.put("msg", "����¼��ʧ�ܣ����Ժ����ԣ�");
		}
		renderJson(json.toJSONString());
	}
	
	public void delNum(){
		JSONObject json = new JSONObject();
		int numid = getParaToInt();
		Lottery ltt = Lottery.dao.findById(numid);
		ltt.delete();
		json.put("state", "success");
		json.put("msg", "����ɾ���ɹ���");
		renderJson(json.toJSONString());
	}
	
	//===============һ�����ĺ���===============
	public void getoverList(){
		List<LotteryLog> list = LotteryLog.dao.find("SELECT * FROM lottery_log ORDER BY creantime DESC");
		setAttr("dateList",list);
		render("/admin/overNum.html");
	}
	
	//========����������========
	public void start(){
		TimeLong tlong = TimeLong.dao.findById(4);
		new TaskTimer().startTask(tlong.getInt("timelong"));//�����ʱ���ǿ�����ʱ��
		new TaskNumber().startTask();//�����ʱ����ͬ��ǰ��˵ĵ���ʱ��ʱ��
		JSONObject json = new JSONObject();
		json.put("state", true);
		renderJson(json.toJSONString());
	}
	
	public void stop(){
		System.out.println("��ִ�н����������Ĺ���");
		JSONObject json = new JSONObject();
		json.put("state", new TaskTimer().stopTimer());
		new TaskNumber().stopTimer(); //�ѵ���ʱҲ����һ��
		renderJson(json.toJSONString());
	}
	
	//��ȡ��ʱ״̬
	public void getTaskStautus(){
		TaskTimerBean tkb = TaskTimerBean.dao.findById(1);
		setAttr("tkb",tkb);
		render("/admin/TaskTime.html");
	}
	
	//���ý��صĺ���Ϊ�յ�ʱ���Ƿ��Զ�����
	public void setAutoStautus(){
		IsAutoStart tkb = IsAutoStart.dao.findById(1);
		setAttr("tkb",tkb);
		render("/admin/isAutoStart.html");
	}
	
	//�ر��Զ�����������
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
	 * �����Զ�����������
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
	 * ����һ�쿪���ڵĽ���
	 */
	public void openHtml(){
		OpenNum tkb = OpenNum.dao.findById(1);
		setAttr("tkb",tkb);
		render("/admin/openNum.html");
	}
	
	/**
	 * ����һ�쿪����
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
