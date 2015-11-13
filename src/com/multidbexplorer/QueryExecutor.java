package com.multidbexplorer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class QueryExecutor implements Runnable {
	private String url;
	private String id;
	private String pwd;
	private String query;

	public QueryExecutor(String url, String id, String pwd, String query) {
		this.url = url;
		this.id = id;
		this.pwd = pwd;
		this.query = query;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		executeQuery(url, id, pwd, query);
	}

	public void executeQuery(String url, String id, String pwd, String query) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pwd);
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			printResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException sqle) {
				}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
			}
		}
	}

	private void printResultSet(ResultSet rs) {
		if (rs == null) {
			System.out.println("resultSet is null");
			return;
		}

		try {
			ResultSetMetaData rsm = rs.getMetaData();
			StringBuffer sb = new StringBuffer();
			sb.append(url).append("\n");
			for (int i = 1; i <= rsm.getColumnCount(); i++) {
				sb.append(rsm.getColumnName(i)).append("\t");
			}
			sb.append("\n");
			while (rs.next()) {
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					sb.append(rs.getObject(i)).append("\t");
				}
				sb.append("\n");
			}
			System.out.println(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
