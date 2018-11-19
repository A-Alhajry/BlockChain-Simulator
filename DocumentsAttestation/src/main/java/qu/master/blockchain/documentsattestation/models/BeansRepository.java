package qu.master.blockchain.documentsattestation.models;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import qu.master.blockchain.documentsattestation.models.beans.Client;
import qu.master.blockchain.documentsattestation.models.beans.Document;
import qu.master.blockchain.documentsattestation.models.beans.DocumentStatus;
import qu.master.blockchain.documentsattestation.models.beans.Enterprise;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseService;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseServiceType;
import qu.master.blockchain.documentsattestation.models.beans.FileRecord;
import qu.master.blockchain.documentsattestation.models.beans.RequestStatus;
import qu.master.blockchain.documentsattestation.models.beans.SignRequest;

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
	
	public List<Client> getClients() throws Exception{
		try (Connection connection = getConnection()) {
			String sql = " Select * From Client ";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			List<Client> result = new ArrayList<>();
			
			while (rs.next()) {
				String id = rs.getString("id");
				String fullName = rs.getString("full_name");
				String publicKey = rs.getString("public_key");
				
				result.add(new Client(id, fullName, publicKey));
			}
			
			return result;
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
	
	public boolean addDocument(Document document) throws Exception{
		
		try (Connection connection = getConnection()) {
			String sql = " Insert Into Document(id, title, creation_time, hash, user_sign, owner_id) Values(?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, document.getId());
			ps.setString(2, document.getTitle());
			ps.setTimestamp(3, Timestamp.from(document.getCreationTime().toInstant(ZoneOffset.UTC)));
			ps.setString(4, document.getHash());
			ps.setString(5, document.getUserSign());
			ps.setString(6, document.getOwner().getId());
			
			return ps.execute();
		}
	}
	
	public boolean addDocumentStatus(String documentId, DocumentStatus status) throws Exception {
		try (Connection connection = getConnection()) {
			String sql = " Insert Into DocumentStatus(id, timestamp, type_id) Values(?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, status.getId());
			ps.setTimestamp(2, Timestamp.from(status.getStatusTime().toInstant(getLocalOffset())));
			ps.setInt(3, status.getType().getId());
			
			return ps.execute();
		}
	}
	
	public boolean addSignRequest(SignRequest req) throws Exception{
		try (Connection connection = getConnection()) {
			String sql = " Insert Into SignRequest(id, user_id, enterprise_id, service_id, document_id, request_time, comments, status) Values(?, ?, ?, ?, ?, ?, ?, ?) ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, req.getId());
			ps.setString(2, req.getUserId());
			ps.setString(3, req.getEnterpriseId());
			ps.setString(4, req.getServiceId());
			ps.setString(5, req.getDocumentId());
//			ps.setTimestamp(6, Timestamp.from(req.getRequestTime().toInstant(ZoneOffset.UTC)));
			ps.setLong(6, req.getRequestTime().toEpochSecond(getLocalOffset()));
			ps.setString(7, req.getComments());
			ps.setInt(8, req.getStatus().getId());
			
			return ps.execute();
		}
	}
	
	public List<SignRequest> getSignRequestsByClient(String clientId) throws Exception{
		try (Connection connection = getConnection()) {
			String sql = " Select sr.*, e.name e_name, s.title s_title, d.title d_title, c.full_name c_name From SignRequest sr  ";
			sql += " Inner Join Enterprise e On e.id = sr.enterprise_id ";
			sql += " Inner Join EnterpriseService s On s.id = sr.service_id ";
			sql += " Inner Join Document d On d.id = sr.document_id ";
			sql += " Inner Join Client c On c.id = sr.user_id ";
			sql += " Order By request_time desc ";
			PreparedStatement ps = connection.prepareStatement(sql);
			//ps.setString(1, clientId);
			ResultSet rs = ps.executeQuery();
			List<SignRequest> result = new ArrayList<>();
			
			while (rs.next()) {

				SignRequest req = new SignRequest();
				req.setId(rs.getString("id"));
				req.setUserId(rs.getString("user_id"));
				req.setEnterpriseId(rs.getString("enterprise_id"));
				req.setServiceId(rs.getString("service_id"));
				req.setDocumentId(rs.getString("document_id"));
				req.setRequestTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(rs.getInt("request_time")), getLocalOffset()));
				//req.setRequestTime(rs.getTimestamp("request_time").toLocalDateTime());
				req.setComments(rs.getString("comments"));
				req.setStatus(RequestStatus.getStatusById(rs.getInt("status")));
				
				String enterpriseName = rs.getString("e_name");
				String serviceName = rs.getString("s_title");
				String documentTitle = rs.getString("d_title");
				String clientName = rs.getString("c_name");
				
				req.setClient(new Client(clientId, clientName, null));
				req.setEnterprise(new Enterprise(req.getEnterpriseId(), enterpriseName, null));
				req.setService(new EnterpriseService(req.getServiceId(), serviceName, null, null));
				req.setDocument(new Document(req.getDocumentId(), documentTitle));
				
				result.add(req);
				
			}
			
			return result;
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
	
	private ZoneOffset getLocalOffset() {
		return ZoneOffset.systemDefault().getRules().getOffset(Instant.now());
	}
}
