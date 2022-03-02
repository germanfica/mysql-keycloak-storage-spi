package com.germanfica;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJdbc {
	public static void main( String[] args) throws SQLException, ConfigurationException {
		// == application.properties  ==
		FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
				new FileBasedConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class)
						.configure(new Parameters().properties()
								.setFileName("application.properties")
								.setThrowExceptionOnMissing(true)
								.setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
								.setIncludesAllowed(false));

		PropertiesConfiguration config = builder.getConfiguration();

		// == sql connection ==
		String jdbcUrl = config.getString("datasource.url");
		String user = config.getString("datasource.username");
		String pass = config.getString("datasource.password");
		
		System.out.println("connecting ...");
		Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
		System.out.println("connected");
	}
}
