package lxq.user.util;

import java.util.Random;

/**
 * ��ʽ�������
 * @author Administrator
 *
 */
public class FormString {
	private static final String[] StrNum= {"1","2","3","4","5","6"};
	
	//��ȡ��һ����
	public String firstNum(String Num){
		return Num.substring(0,1);
	}
	
	//��ȡ�ڶ�λ��
	public String secondNum(String Num){
		return Num.substring(1,2);
	}
	
	//��ȡ����λ��
	public String threeNum(String Num){
		return Num.substring(2,3);
	}
	
	//���С
	public String bigorsam(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		if((f+s+t)<=10){
			return "С";
		}else{
			return "��";
		}
	}
	
	//��˫
	public String onlyAll(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		if((f+s+t)%2==0){
			return "˫";
		}else{
			return "��";
		}
	}
	
	//���
	public int allNum(String num){
		int f = Integer.parseInt(num.substring(0, 1));
		int s = Integer.parseInt(num.substring(1, 2));
		int t = Integer.parseInt(num.substring(2, 3));
		return (f+s+t);
	}
	
	//�����ȡ1~6����λ��
	public String getThreeNum(){
		Random random = new Random();
		String code = new String();
		for(int i=0; i<3; i++){
			int index = random.nextInt(5);
			code += StrNum[index+1];
		}
		return code;
	}
	
	//��λ�����λ�������ʱ����0������
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
	
	//�ж��û���½��֤
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
