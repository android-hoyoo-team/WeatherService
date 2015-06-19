package org.cz.project.dao;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.FloatType;

public class SQLServerDialect_ext extends org.hibernate.dialect.SQLServerDialect {
	public SQLServerDialect_ext() {
		super();
		System.out.println("__________ext:ssss");
		this.registerFunction("fnGetDistance", new SQLFunctionTemplate(new FloatType(),
				"MAX(?1)"));
	}
}