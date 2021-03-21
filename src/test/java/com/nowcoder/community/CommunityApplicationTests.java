package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationConttext() {
		System.out.println(applicationContext);

		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class); //从容器中获取这个类型的bean
		System.out.println(alphaDao.select());

		alphaDao = applicationContext.getBean( "alphaHibernate", AlphaDao.class); //指定了bean的名字，并指定对象类型
		System.out.println(alphaDao.select());

	}
	@Test //主动去获取容器，并从容器中拿一个bean来用
	public void testBeanConfig() {
		SimpleDateFormat simpleDateFormat =
				applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	//依赖注入！
	@Autowired   //Spring容器把alphaDao注入这个属性，就可以直接使用，不必再getbean
	@Qualifier("alphaHibernate")  //指定bean，Spring容器把名为alphaHibernate的bean注入给变量alphaDao
	private AlphaDao alphaDao;

	@Test
	public void testDI() {
		System.out.println(alphaDao);
	}
}
