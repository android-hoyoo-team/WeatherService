package org.cz.project.dao;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.FloatType;
import org.hibernate.type.StandardBasicTypes;

public class SQLServerDialect_ext extends org.hibernate.dialect.SQLServerDialect {
	public SQLServerDialect_ext() {
		super();
		System.out.println("__________ext:ssss");
//		registerFunction( "fnYYY", new StandardSQLFunction( "dbo.fnYYY", StandardBasicTypes.STRING ) );
		registerFunction( "fn_get_distance", new SQLFunctionTemplate( StandardBasicTypes.FLOAT, "dbo.fnGetDistance(?1,?2,?3,?4)" ) );
//		this.registerFunction("fnGetDistance", new SQLFunctionTemplate(new FloatType(),
//				"dbo.fnGetDistance(?1,?2,?3,?4)"));
	}
}