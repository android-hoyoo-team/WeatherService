package per.cz.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.*;  

import per.cz.util.CheckTimes;

public class SqlServerTest {  

	/**
	 * @param args
	 */
	public static void main(String[] args) {  
		
		//System.out.println(new java.util.Date(1422633622000l));
//		try {
//			System.setOut(new PrintStream(new File("F://yidong1.txt")));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		String data4AcResult = DBUtil.getData4QRAcResult(LocalDBConfig.DRIVER, LocalDBConfig.URL+LocalDBConfig.DBNAME, LocalDBConfig.UNAME, LocalDBConfig.PWD,"SELECT empNO AS 工号, personName AS 姓名 ,personType AS 用户,oInpatientArea AS  病区,oCellName AS 位置 ,InOutType AS 动作,nTime AS 时间 FROM `r_suttagrecord_view` where empNO is not null  and (personName like '%三%' or empNO like '%三%') order by nTime",0,0);
//		//System.out.println(data4AcResult);
		//String sql="select dbo.fnGetDistance(东经,北纬,120.35,28.90) as ss from T_Station_Area";
		//String sql="select top 1 townfine0_.fcstTime as col_0_0_, townfine0_.stid as col_1_0_, towninfo1_.stname as col_2_0_, towninfo1_.stlat as col_3_0_, towninfo1_.stlon as col_4_0_, townfine0_.fh as col_5_0_, townfine0_.ww03 as col_6_0_, townfine0_.ww06 as col_7_0_, townfine0_.ww12 as col_8_0_, townfine0_.t as col_9_0_, townfine0_.tmax12 as col_10_0_, townfine0_.tmin12 as col_11_0_, townfine0_.pr03 as col_12_0_, townfine0_.pr06 as col_13_0_, townfine0_.pr12 as col_14_0_, townfine0_.pr24 as col_15_0_, townfine0_.rh as col_16_0_, townfine0_.wd as col_17_0_, townfine0_.ws as col_18_0_, townfine0_.cloud as col_19_0_, townfine0_.vv as col_20_0_, townfine0_.slp as col_21_0_ from town_fine townfine0_ cross join town_info towninfo1_ where townfine0_.stid=towninfo1_.stid and townfine0_.fcstTime=(select max(townfine2_.fcstTime) from town_fine townfine2_)";
		String sql="select * from co_mobile.dbo.users";
		String tableData1 = DBUtil.getData4QRAcResult("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://61.164.175.158:8327;", "public@58560", "public@58560", sql,0,0);
		System.out.println(tableData1);
//		String tableData1 = DBUtil.getData4QRListMap("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", "select count(*) as c from zgxj_护士站医嘱执行单",0,0);
		//System.out.println(tableData1);
//		String tableData = DBUtil.getData4QRListMap("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", "select * from zgxj_护士站医嘱执行单",0,0);
//		//System.out.println(tableData);
		//		String tableData = DBUtil.getTableData("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@10.41.88.71:1521:orcl", "system", "clu111", "test");
//		String data4qrListMap = DBUtil.getData4QRListMap("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", "select * from zgxj_护士站医嘱执行单", 0, 10);
//		//System.out.println(data4qrListMap);
//		String data4qrListMap1 = DBUtil.getData4QRListMap("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", "select * from zgxj_view_emr_patientlist", 0, 10);
//		//System.out.println(data4qrListMap1);
//		CheckTimes checkTimes = new CheckTimes();
		//String sql="SELECT *FROM `person`";
//		String sql="SELECT *FROM `mh_nurseexecuterecord_view`";
//		String sql="SELECT `mh_nurseexecuterecord_patient`.`execute`   AS `name`,`mh_nurseexecuterecord_patient`.`execute`   AS `info` FROM `mh_nurseexecuterecord_patient` GROUP BY `mh_nurseexecuterecord_patient`.`execute` ";
//		String sql="select 生日,住院号,入院床号,入院病区,入院科别,入院日期,出院日期,姓名,性别 from zgxj_view_emr_patientlist ";
//		String data4qrListMap3 = DBUtil.getData4ListMap(LocalDBConfig.DRIVER, LocalDBConfig.URL+LocalDBConfig.DBNAME, LocalDBConfig.UNAME, LocalDBConfig.PWD,sql,0,0);
//		//System.out.println(checkTimes.getLastTimes());
//		String data4qrListMap3 = DBUtil.getData4QRAcResult("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", sql, 0, 10);
		
//		String data4qrListMap3 = DBUtil.getData4QRAcResult("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", "select 生日,住院号,入院床号,入院病区,入院科别,入院日期,出院日期,姓名,性别 from zgxj_view_emr_patientlist ", 0, 10);
//		String data4qrListMap3 = DBUtil.getData4QRListMap("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", "select * from zgxj_MEDIC_BASE", 0, 10);
//		//System.out.println(data4qrListMap3);
//		String data4qrListMap2 = DBUtil.getData4QRListMap("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", "select * from zgxj_MEDIC_BASE", 0, 10); 
////		String data4qrListMap2 = DBUtil.getData4Vector("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://192.168.2.55", "q", "1", "select 生日,住院号,入院床号,入院病区,入院科别,入院日期,出院日期,姓名,性别 from zgxj_view_emr_patientlist where 入院日期>'2012-12-13' order by 入院日期", 0, 10); 
//		//System.out.println(data4qrListMap2);
	}  

} 