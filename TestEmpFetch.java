package com.liftoff.test;

import java.util.Sets;
import java.util.Collections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.Binding;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.impl.DSL;

import com.liftoffllc.first.tables.Departments;
import com.liftoffllc.first.tables.Employee;




public class TestEmpFetch {
	
	
	public static Map<String,Integer> countEmployeesPerDept()
	{

		  String user = "root";
	        String password = "Abc*12345";
	        String url = "jdbc:mysql://localhost:3306/Office?useSSL=false";
	        
	        Connection connection = null;
	
	        Departments d = Departments.DEPARTMENTS;
	        Employee e = Employee.EMPLOYEE;
	        
	        try  {
	        	connection = DriverManager.getConnection(url, user, password);
	            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
	            
	            Map<String,Integer> M = new HashMap<String,Integer>();

	            Field<Integer> count = DSL.field(DSL.selectCount().from(e).where(e.DEPARTMENTS_SL.eq(d.SL)));
	
	           
	            Result<Record2<String,Integer>> result = dslContext.select(d.NAME,count).from(d).leftOuterJoin(e).on(d.SL.eq(e.DEPARTMENTS_SL)).fetch();
	            System.out.println("Result " + result);
	            for (Record r : result) {
	                    Integer numEmployees = (Integer) r.getValue(count);
	                    String deptName =(String) r.getValue(d.NAME);
	                    System.out.println(numEmployees+" " + deptName);
	                    M.put(deptName,numEmployees);
                       
  
	            } 
	            return M;
	            
	        }
	        catch(Exception ex3)
	        {
	        	ex3.printStackTrace();
	            return null;      	
	        }
	        finally
	        {
	        	try {
					connection.close();
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
	        }

		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(countEmployeesPerDept());
	
	}

}

