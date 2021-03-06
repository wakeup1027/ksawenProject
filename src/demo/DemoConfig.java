package demo;

//import gxTest.gxFunc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lxq.user.util.FormString;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal3.JFinal3BeetlRenderFactory;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.config.HtmlHandler;
import com.config.BasePathHandler;
import com.config.AutoBindModels;
import com.config.AutoBindRoutes;
import com.config.SessionAttrInterceptor;

public class DemoConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		InputStream in=null;
        Properties props=new Properties();
        try {
        	in=getClass().getResourceAsStream("dbconfig.properties" );
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String jdbcUrl=props.getProperty("DevMode").trim();
        if("false".equals(jdbcUrl)){
        	me.setDevMode(false);
        }else{
        	me.setDevMode(true);
        }
		//设置解析渲染html的工厂
		me.setViewType(ViewType.JSP); // 设置视图类型为Jsp，否则默认为FreeMarker
		JFinal3BeetlRenderFactory rf = new JFinal3BeetlRenderFactory();
		rf.config();
		me.setRenderFactory(rf);
		
		// 获取GroupTemplate ,可以设置共享变量等操作  作用是在渲染界面的时候如果出现界面中的方法 也可以执行
		GroupTemplate groupTemplate = rf.groupTemplate;
		groupTemplate.registerFunctionPackage("strutil", FormString.class);
	}

	@Override
	public void configRoute(Routes me) {
		//配置路由：就是访问地址的
		//me.add("/hello",HelloController.class);
		me.add(new AutoBindRoutes());
	}

	@Override
	public void configEngine(Engine me) {
		//添加共享函数的模板文件
	}

	@Override
	public void configHandler(Handlers me) {
		//设置路径html伪路径
		me.add(new HtmlHandler());
		// 全路径获取
		me.add(new BasePathHandler("BASE_PATH"));
	}

	@Override
	public void configInterceptor(Interceptors me) {
		//配置拦截器
		me.add(new SessionAttrInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {
		InputStream in=null;
        Properties props=new Properties();
        try {
        	in=getClass().getResourceAsStream("dbconfig.properties" );
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String jdbcUrl=props.getProperty("jdbcUrl").trim();
        String url=props.getProperty("url").trim();
        String user=props.getProperty("user").trim();
        String passw=props.getProperty("passw").trim();
		C3p0Plugin c3p0Plugin = new C3p0Plugin(jdbcUrl, user, passw, url);
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		
		String ShowSql=props.getProperty("ShowSql").trim();
		if("false".equals(ShowSql)){
			arp.setShowSql(false);
		}else{
			arp.setShowSql(true);
		}

		new AutoBindModels(arp);
	}
	
}
