package net.thornydev.mybatis.koan.koan01;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class Koan01 {

	// To complete this configuration koan, you will need to complete three files:
	// 1. fill in the proper path to the MyBatis XML configuration file in this file
	// 2. fill in the "blanks" in the MyBatis XML configuration file (look for TODO markers)
	// 3. fill in the properties for your database and JDBC settings in the properties
	//    file that the MyBatis XML config file references
	@Test
	public void learnBasicConfigurationSetup() throws Exception {
		// TODO: fill in path
		String resource = "net/thornydev/mybatis/koan/koan01/koan01-config.xml";  
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// TODO: provide proper argument to SqlSessionFactoryBuilder
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
		
		assertNotNull(sqlSessionFactory);
	}

}
