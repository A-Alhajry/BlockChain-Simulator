package qu.master.blockchain.documentsattestation.models;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import qu.master.blockchain.documentsattestation.models.beans.Enterprise;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseService;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseServiceType;
import qu.master.blockchain.documentsattestation.models.beans.FileRecord;

public class BeansRepository {
	
	private static String databaseUrl = "jdbc:sqlite:database" + File.separator + "app.db";
	private static String createScript = "database" + File.separator + "create_database.sql";
	
	public static void createDatabase() throws Exception {
		
		try (Connection connection = getConnection()) {
			
			//System.out.println("New Database has been created ! ");
			//createTables(connection);
			boolean createTables = createTables(connection);
			System.out.println("Tables created ");
		}
	}
	
	public List<Enterprise> getEnterprisesList() throws Exception {
		
		List<Enterprise> enterprises = new ArrayList<>();
		
		try (Connection connection = getConnection()) {
			String sql = " Select * From Enterprise ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String publicKey = rs.getString("public_key");
				
				enterprises.add(new Enterprise(id, name, publicKey));
			}
		}
		
		return enterprises;
	}
	
	public List<EnterpriseService> getEnterprisesServicesList(String enterpriseId, EnterpriseServiceType type) throws Exception {
		
		List<EnterpriseService> services = new ArrayList<EnterpriseService>();
		
		try (Connection connection = getConnection()) {
			String sql = " Select * From EnterpriseService Where enterprise_id = ? And type_id = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, enterpriseId);
			pstmt.setInt(2, type.getId());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String supportedFiles = rs.getString("supported_files");
				services.add(new EnterpriseService(id, title, desc, supportedFiles));
			}
		}
		
		return services;
	}
	
	public boolean saveBeanRecord(FileRecord record) throws Exception{
		
		try (Connection connection = getConnection()) {
			String sql = " Insert Into FileRecord (id, type_id, class_name, address, bean_id) Values(?, ?, ?, ?, ?) ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, record.getId());
			ps.setInt(2, record.getTypeId());
			ps.setString(3, record.getClassName());
			ps.setString(4, record.getAddress());
			ps.setString(5, record.getBeanId());
			return ps.execute();
		}
	}
	
	public FileRecord getRecordByBeanId(String beanId) throws Exception{
		try (Connection connection = getConnection()) {
			String sql = " Select * From FileRecord Where bean_id = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, beanId);
			ResultSet rs = ps.executeQuery();
			FileRecord record = null;
			while (rs.next()) {
				String id = rs.getString("id");
				String address = rs.getString("address");
				String className = rs.getString("class_name");
				int typeId = rs.getInt("type_id");
				
				record =  new FileRecord(id, beanId, typeId, className, address);
			}
			
			return record;
		}
	}
	
	public List<FileRecord> getRecordsByClass(Class<?> beanClass) throws Exception  {
		try (Connection connection = getConnection()) {
			List<FileRecord> records = new ArrayList<>();
			String sql = " Select * From FileRecord Where class_name = ? ";
			String className = beanClass.getSimpleName();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, className);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String id = rs.getString("id");
				int typeId = rs.getInt("type_id");
				String beanId = rs.getString("bean_id");
				String address = rs.getString("address");
				
				records.add(new FileRecord(id, beanId, typeId, className, address));
			}
			
			return records;
		}
	}
	
	private static Connection getConnection() throws Exception{
		return DriverManager.getConnection(databaseUrl);
	}
	
	private static boolean createTables(Connection connection) throws Exception{
		byte[] scriptBytes = Files.readAllBytes(Paths.get(createScript));
		String script = new String(scriptBytes).trim();
		String[] commands = script.split(";");
		for(String command : commands) {
			System.out.println(command);
			Statement stmn = connection.createStatement();
			stmn.execute(command.toString());
		}
		//List<String> script = Files.readAllLines(Paths.get(createScript));
		
		return true;
		
	}
}
