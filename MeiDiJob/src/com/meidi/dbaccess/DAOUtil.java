package com.meidi.dbaccess;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DAOUtil {

	private String sql;
	private PreparedStatement ps = null;
	private Connection con = null;
	
	public DAOUtil(String sql) throws Exception
	{
		this.sql = sql;
		con = DBAccess.getInstance().getConnection();
//		con = SimpleConnectionPool.getConnection();
		ps = con.prepareStatement(sql);
	}
	
	/**
	 * 绑定参数
	  * @Title: bind
	  * @Description: TODO
	  * @param @param index
	  * @param @param value
	  * @param @throws Exception
	  * @return void
	  * @throws
	 */
	public void bind(int index, Object value) throws Exception
	{
		if(value instanceof String)
		{
			ps.setString(index, value.toString());
		}
		else if(value instanceof Date)
		{
			ps.setDate(index, new java.sql.Date(((Date) value).getTime()));
		}
		else if(value instanceof Integer)
		{
			ps.setInt(index, (Integer)value);
		}
		else if(value instanceof BigDecimal)
		{
			ps.setBigDecimal(index, (BigDecimal)value);
		}
	}
	
	/**
	 * 执行查询操作
	  * @Title: executeQuery
	  * @Description: TODO
	  * @param @return
	  * @param @throws Exception
	  * @return List<Map>
	  * @throws
	 */
	public List<Map> executeQuery() throws Exception
	{
		List<Map> result = new ArrayList<Map>();
		ResultSet rs = null;
		try
		{
			Map map = null;
			rs = ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount(); 
			while (rs.next()) 
			{
				    map = new HashMap(); 
				    for (int i = 1; i <= columnCount; i++) 
				    { 
				    	map.put(md.getColumnName(i), rs.getObject(i)); 
				    }
				    result.add(map); 
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			close();
		}
		return result;
	}
	
	public Map executeQueryOne() throws Exception
	{
		ResultSet rs = null;
		Map map = null;
		try
		{
			rs = ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount(); 
			while (rs.next()) 
			{
				    map = new HashMap(); 
				    for (int i = 1; i <= columnCount; i++) 
				    { 
				    	map.put(md.getColumnName(i), rs.getObject(i)); 
				    }
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			close();
		}
		return map;
	}
	
	/**
	 * 执行更新操作
	  * @Title: executeUpdate
	  * @Description: TODO
	  * @param @throws Exception
	  * @return void
	  * @throws
	 */
	public void executeUpdate() throws Exception
	{
		try
		{
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			close();
		}
	}
	
	/**
	 * 关闭连接
	  * @Title: close
	  * @Description: TODO
	  * @param 
	  * @return void
	  * @throws
	 */
	private void close()
	{
		try
		{
			if(ps != null)
			{
				ps.close();
			}
			if(con != null)
			{
				con.close();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
