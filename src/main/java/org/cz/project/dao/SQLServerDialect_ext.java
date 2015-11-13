package org.cz.project.dao;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.FloatType;
import org.hibernate.type.StandardBasicTypes;

public class SQLServerDialect_ext extends org.hibernate.dialect.SQLServerDialect {
	public SQLServerDialect_ext() {
		super();
//		registerFunction( "fnYYY", new StandardSQLFunction( "dbo.fnYYY", StandardBasicTypes.STRING ) );
		registerFunction( "fn_get_distance", new SQLFunctionTemplate( StandardBasicTypes.FLOAT, "JhPanWeather.dbo.fnGetDistance(?1,?2,?3,?4)" ) );
		registerFunction( "ext_concat", new SQLFunctionTemplate( StandardBasicTypes.FLOAT, "cast(?1 as varchar(8))+?2" ) );
		registerFunction( "cast_avg_decimal", new SQLFunctionTemplate( StandardBasicTypes.FLOAT, "cast(avg(?1) as decimal(10,1))" ) );
//		this.registerFunction("fnGetDistance", new SQLFunctionTemplate(new FloatType(),
//				"dbo.fnGetDistance(?1,?2,?3,?4)"));
	}
}