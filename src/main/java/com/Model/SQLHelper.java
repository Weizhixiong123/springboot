package com.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class SQLHelper {

 private static JdbcTemplate jdbc;
  
 public SQLHelper() {
	  if(jdbc == null) {
		 jdbc = getCon();
	  }
 
 } 
public JdbcTemplate getCon() {
 
	BasicDataSource datasource = new BasicDataSource();
	   datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	   datasource.setUrl("jdbc:mysql://localhost:3306/springtest?serverTimeZone=UTC&useSSL=false");
	   datasource.setUsername("root");
	   datasource.setPassword("wzx");
	   jdbc = new JdbcTemplate(datasource);
    return jdbc;     
}
	
	
// private JdbcTemplate jdbc;
// 
// public SQLHelper() {
//	 BasicDataSource datasource = new BasicDataSource();
//	   datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//	   datasource.setUrl("jdbc:mysql://localhost:3306/springtest?serverTimeZone=UTC&useSSL=false");
//	   datasource.setUsername("root");
//	   datasource.setPassword("wzx");
//	   jdbc = new JdbcTemplate(datasource);
	   
// }
 public List<Map<String,Object>> Select_List(String Table){

	   String sql = "SELECT*FROM " + Table;
	   List<Map<String,Object>> maps = jdbc.queryForList(sql);
	   return maps;
 }
 public List<Map<String,Object>> Select_List(String Table,String Condition){

	   String sql = "select * from "+Table +" where "+Condition;
	   List<Map<String,Object>> maps = jdbc.queryForList(sql);
	   return maps;
}
 
 public List<Map<String,Object>> Select_List(String Table,String Field,String Condition){

	   String sql = "select " +Field+ " from "+Table +" where "+Condition;
	   List<Map<String,Object>> maps = jdbc.queryForList(sql);
	   return maps;
}
 public List<Map<String,Object>> Select_List(String Table,String Field,String Condition,String Order){

	   String sql = "select " +Field+ " from "+Table +" where "+Condition+" order by "+Order;
	   List<Map<String,Object>> maps = jdbc.queryForList(sql);
	   return maps;
}
 
 // 查询id
 
 public String Select_String(String Table,String Field,String Conditions){
      String sql = "select "+ Field +" from "+Table +" where "+Conditions + " limit 1";
       
      String result = jdbc.queryForObject(sql, String.class);
	   return result;
}
 public boolean Select_Exsit(String Table,String Conditions){
     String sql = "select count(*) from "+Table +" where "+Conditions;
      
     int result = jdbc.queryForObject(sql, Integer.class);
	  if(result > 0) {
		  return true;
	  }else {
		  return false;
	  } 
    }

 public  int Insert(String Table,Object t) {
	 String sql ="insert into "+Table +" ("+ GetField(t) + ") values (" + GetPlace(t) + ")";
     int result  = jdbc.update(sql,GetValues(t));
     return result;
 }
 public  int Update_Row(String Table,Object t,String Condition) {
	 String sql ="update "+Table +" set "+GetFieldWithPlace(t)+" where "+Condition; 
    int result  = jdbc.update(sql,GetValues(t));
      return result;
 
 }
 
 public  int Update_String(String Table,String key,String value,String Condition) {
	 String sql ="update "+Table +" set "+key +"=? where "+Condition; 
  System.out.println(sql);
 int result  = jdbc.update(sql,value);
   return result;
 
 }
 public  int Delete(String Table,String Condition) {
	 String sql ="delete from "+Table+" where "+Condition ; 
  System.out.println(sql);
 int result  = jdbc.update(sql);
   return result;
 
 }
 public int Excute(String sql) {
	 return jdbc.update(sql);
 }
 public List<Map<String,Object>> Select_BySQL(String sql){
	  List<Map<String,Object>> maps = jdbc.queryForList(sql);
	  return maps;
 }
 
 
 

 
 
  private String GetField(Object o) {
	 String Result="";
	 Class<? extends Object> t=o.getClass();
	 Field[] fs=t.getDeclaredFields();
	 for(Field f : fs) {
		 
		 if(!f.getName().toLowerCase().equals("id")) {
		 
		 Result += f.getName()+",";
		 }
	 }
	 Result=Result.substring(0,Result.length()-1);
	 return Result;
 }

 private String GetPlace(Object o) {
	 String Result="";
	 Class<? extends Object> t=o.getClass();
	 Field[] fs=t.getDeclaredFields();
	 for(Field f : fs) {
		 
		 if(!f.getName().toLowerCase().equals("id")) {
		 Result+="?,";
		 }
	 }
	 Result=Result.substring(0,Result.length()-1);
	 return Result;
 }
 private String[] GetValues(Object o) {
	 List<String> list = new ArrayList<String>();
	 
	 Class<? extends Object> t=o.getClass();
	 Field[] fs=t.getDeclaredFields();
	 for(Field f : fs) {
		 f.setAccessible(true);
		 if(!f.getName().toLowerCase().equals("id")) {
      
            try {
				if(f.get(o) == null || f.get(o)=="") {
					list.add("");
				}else {
					list.add(f.get(o).toString());
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		 }
	 }
	    return list.toArray(new String[list.size()]);
 }
 private String GetFieldWithPlace(Object o) {
	 String Result= "";
	 Class<? extends Object> t=o.getClass();
	 Field[] fs=t.getDeclaredFields();
	 for(Field f : fs) {
		 if(!f.getName().toLowerCase().equals("id")) {
		  Result += f.getName()+"=?,";
		 }
	 }
	 Result=Result.substring(0,Result.length()-1);
	 return Result; 
 }

}
