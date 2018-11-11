package qu.master.blockchain.documents.attestation.models;

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

import qu.master.blockchain.documents.attestation.model.beans.Enterprise;
import qu.master.blockchain.documents.attestation.model.beans.EnterpriseService;

public class Repository {
	
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
			String sql = " Select * From enterprise ";
			Statement stmn = connection.createStatement();
			ResultSet rs = stmn.executeQuery(sql);
			
			
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String publicKey = rs.getString("public_key");
				
				enterprises.add(new Enterprise(id, name, publicKey));
			}
		}
		
		return enterprises;
	}
	
	public List<EnterpriseService> getEnterprisesServicesList(String enterpriseId) throws Exception {
		
		List<EnterpriseService> services = new ArrayList<EnterpriseService>();
		
		try (Connection connection = getConnection()) {
			String sql = " Select * From EnterpriseService Where enterprise_id = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, enterpriseId);
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
