package sqlCRUDPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class crudOps {

	static String dbname = "tempDB";
	
	public void create() throws SQLException, Exception{
		Class.forName("com.mysql.jdbc.Driver");
		System.out.print("Driver loaded -- ");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
		System.out.print("Connected -- ");
		Statement st=con.createStatement();
		st.execute("use "+dbname);
		st.execute("CREATE TABLE `tempdb`.`employee` ( `empid` INT NOT NULL , `name` VARCHAR(25) NOT NULL , `city` VARCHAR(10) NOT NULL , `phone` VARCHAR(10) NOT NULL ) ENGINE = InnoDB");
		System.out.print("Database Created. -- Table Created -- Columns Added.");
		read();
		con.close();
	}
	
	
	
	public static Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String link = "jdbc:mysql://localhost:3306/" + dbname;
			Connection con=DriverManager.getConnection(link,"root","");
			return con;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insert() throws Exception {
		Connection con = connect();
		System.out.println("\nEnter number of employee records to add");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num = Integer.parseInt(br.readLine());
		for(int i = 0;i<num;i++) {
			System.out.println("Employee "+(i+1));
			System.out.println("Enter Employee Name");
			String name = br.readLine();
			System.out.println("Enter Employee City");
			String city = br.readLine();
			System.out.println("Enter Employee Phone");
			String phone = br.readLine();
			String query = "INSERT INTO employee (empid, name, city, phone) VALUES(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, (i+1));
			ps.setString(2, name);
			ps.setString(3, city);
			ps.setString(4, phone);
			ps.executeUpdate();
		}
		read();
		con.close();
	}
	
	public static void read() throws Exception{
		Connection con = connect();
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("select * from employee");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
		}
		con.close();
	}
	
	public void update() throws Exception{
		Connection con = connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Employee ID for the employee you want to update data");
		int empID = Integer.parseInt(br.readLine());
		System.out.println("Enter Employee Name");
		String name = br.readLine();
		System.out.println("Enter Employee City");
		String city = br.readLine();
		System.out.println("Enter Employee Phone");
		String phone = br.readLine();
		String query = "UPDATE employee SET name=? ,city=? ,phone=? WHERE empid=?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, name);
		ps.setString(2, city);
		ps.setString(3, phone);
		ps.setInt(4, empID);
		int temp = ps.executeUpdate();
		if(temp>0) {
			System.out.println("The Employee "+empID+" was updated successfully!");
		}
		read();
		con.close();
	}
	
	public void delete() throws Exception {
		Connection con = connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Employee ID for the employee you want to delete");
		int empID = Integer.parseInt(br.readLine());
		String query = "DELETE FROM employee WHERE empid="+empID;
		PreparedStatement ps = con.prepareStatement(query);
		//ps.setInt(1,empID);
		int temp =ps.executeUpdate(query);
		if(temp>0) {
			System.out.println("The record for employee "+empID+" was removed successfully!");
		}
		read();
		con.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		crudOps cop = new crudOps();
		cop.create();
		cop.insert();
		cop.update();
		cop.delete();
	}

}
