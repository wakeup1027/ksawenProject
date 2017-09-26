package lxq.user.util;

import java.util.Random;

/**
 * 格式化结果集
 * @author Administrator
 *
 */
public class FormString {
	private static final String[] StrNum= {"1","2","3","4","5","6"};
	
	//获取第一个数
	public String firstNum(String Num){
		return Num.substring(0,1);
	}
	
	//获取第二位数
	public String secondNum(String Num){
		return Num.substring(1,2);
	}
	
	//获取第三位数
	public String threeNum(String Num){
		return Num.substring(2,3);
	}
	
	//求大小
	public String bigorsam(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		if((f+s+t)<=10){
			return "小";
		}else{
			return "大";
		}
	}
	
	//求单双
	public String onlyAll(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		if((f+s+t)%2==0){
			return "双";
		}else{
			return "单";
		}
	}
	
	//求和
	public int allNum(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		return (f+s+t);
	}
	
	//随机获取1~6的三位数
	public String getThreeNum(){
		Random random = new Random();
		String code = new String();
		for(int i=0; i<3; i++){
			int index = random.nextInt(5);
			code += StrNum[index+1];
		}
		return code;
	}
	
	//补位，如果位数不足的时候用0来填上
	public String formNum(int oneday, int nowday){
		String stroneday = String.valueOf(oneday);
		String strnowday = String.valueOf(nowday);
		if(stroneday.length()-strnowday.length()==2){
			return "00"+strnowday;
		}else if(stroneday.length()-strnowday.length()==1){
			return "0"+strnowday;
		}else{
			return strnowday;
		}
	}
	
	//判断用户登陆验证
	public boolean userLogin(String user, String password){
		if("ksadmin".equals(user)&&"awenjiusan..".equals(password)){
			return true;
		}else{
			return false;
		}
	}
	
	public String subStr(String num,int star){
		return num.substring(star, num.length());
	}
	
	public static void main(String[] args) { 
		  System.out.println(new FormString().formNum(144,110));
	}
	
	private class Invalid{  
        
    }
}
