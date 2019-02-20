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
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import qu.master.blockchain.documentsattestation.Logger;
import qu.master.blockchain.documentsattestation.models.beans.Client;
import qu.master.blockchain.documentsattestation.models.beans.Document;
import qu.master.blockchain.documentsattestation.models.beans.DocumentSignature;
import qu.master.blockchain.documentsattestation.models.beans.DocumentStatus;
import qu.master.blockchain.documentsattestation.models.beans.Enterprise;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseService;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseServiceType;
import qu.master.blockchain.documentsattestation.models.beans.FileRecord;
import qu.master.blockchain.documentsattestation.models.beans.RequestStatus;
import qu.master.blockchain.documentsattestation.models.beans.SealedDocument;
import qu.master.blockchain.documentsattestation.models.beans.SignRequest;
import qu.master.blockchain.documentsattestation.models.beans.VerifyRequest;

public class BeansRepository {
	
	private static String databaseUrl = "jdbc:sqlite:database" + File.separator + "app.db";
	private static String createScript = "database" + File.separator + "create_database.sql";
	
	private Connection connection;
	
	public static void createDatabase() throws Exception {
		
		try (Connection connection = getConnectionAsStatic()) {
			
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
				String shortName = rs.getString("short_name");
				String publicKey = rs.getString("public_key");
								
				enterprises.add(new Enterprise(id, name, shortName, publicKey));
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
	
	public List<Document> getDocuments(String id) throws Exception {
		try (Connection connection = getConnection()) {
			String sql = " Select * From Document ";
			if (id != null && !id.isEmpty()) {
				sql += " Where id = ? ";
			}
			
			PreparedStatement ps = connection.prepareStatement(sql);
			
			if (id != null && !id.isEmpty()) {
				ps.setString(1, id);
			}

			List<Document> documents = new ArrayList<Document>();
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String userId = rs.getString("owner_id");
				String title = rs.getString("title");
				String userSign = rs.getString("user_sign");
				String hash = rs.getString("hash");
				//Long creationTime = rs.getLong("creation_time");
				Document document = new Document();
				document.setId(id);
				document.setOwner(new Client(userId, null, null));
				document.setTitle(title);
				document.setUserSign(userSign);
				document.setHash(hash);
				//document.setCreationTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(creationTime), getLocalOffset()));
				
				documents.add(document);
				
			}
			
			return documents;
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
			String sql = " Insert Into SignRequest(id, user_id, enterprise_id, service_id, document_id, request_time, comments, status, contract_address) Values(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
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
			ps.setString(9, req.getContractAddress());
			
			return ps.execute();
		}
	}
	
	public boolean addVerifyRequest(VerifyRequest req) throws Exception {
		try (Connection connection = getConnection()) {
			String sql = " Insert Into VerifyRequest(id, user_id, enterprise_id, request_time, contract_address, document_id, status, document_location, service_id) Values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, req.getId());
			ps.setString(2, req.getUserId());
			ps.setString(3, req.getEnterpriseId());
			ps.setLong(4, req.getRequestTime().toEpochSecond(getLocalOffset()));
			ps.setString(5, req.getContractAddress());
			ps.setString(6, req.getDocumentId());
			ps.setInt(7, req.getStatus().getId());
			ps.setString(8, req.getDocumentLocation());
			ps.setString(9, req.getServiceId());
			return ps.execute();
		}
	}
	
	public boolean updateSignRequestStatus(String requestId, RequestStatus newStatus) throws Exception {
		
		try (Connection connection = getConnection()) {
			String sql = " Update SignRequest Set status = ? Where id = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, newStatus.getId());
			ps.setString(2, requestId);
			System.out.println(" req id = before update = " + requestId);
			return ps.execute();
		}
	}
	
	public boolean updateVerifyRequestStatus(String requestId, RequestStatus newStatus) throws Exception {
		try (Connection connection = getConnection()) {
			String sql = " Update VerifyRequest Set status = ? Where id = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, newStatus.getId());
			ps.setString(2, requestId);
			return ps.execute();
		}
	}
	
	public List<SignRequest> getSignRequestsByClient(String clientId) throws Exception{
		try (Connection connection = getConnection()) {
			String sql = getSignRequestQuery(" sr.user_id = ? ");
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, clientId);
			ResultSet rs = ps.executeQuery();
			List<SignRequest> result = new ArrayList<>();
			
			while (rs.next()) {
				result.add(readSignRequest(rs));
			}
			
			return result;
		}
	}
	
	public List<SignRequest> getSignRequestsByEnterprise(String enterpriseId) throws Exception {
		
		try (Connection connection = getConnection()) {
			String sql = getSignRequestQuery("sr.enterprise_id = ? And status = ? ");
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, enterpriseId);
			ps.setInt(2, RequestStatus.IN_PROGRESS.getId());
			ResultSet rs = ps.executeQuery();
			List<SignRequest> result = new ArrayList<SignRequest>();
			while (rs.next()) {
				result.add(readSignRequest(rs));
			}
			
			return result;
			
		}
	}
	
	public List<VerifyRequest> getVerifyRequestsByClient(String clientId) throws Exception {
		
		try(Connection connection = getConnection()){
			String sql = getVerifyRequestQuery(" vr.user_id = ? ");
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, clientId);
			ResultSet rs = ps.executeQuery();
			List<VerifyRequest> result = new ArrayList<VerifyRequest>();
			
			while(rs.next()) {
				result.add(readVerifyRequest(rs));
			}
			
			return result;
		}
	}
	
	public List<VerifyRequest> getVerifyRequestsByEnterprise(String enterpriseId) throws Exception {
		
		try(Connection connection = getConnection()){
			String sql = getVerifyRequestQuery(" vr.enterprise_id = ? And vr.status = ? ");
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, enterpriseId);
			ps.setInt(2, RequestStatus.IN_PROGRESS.getId());
			ResultSet rs = ps.executeQuery();
			List<VerifyRequest> result = new ArrayList<VerifyRequest>();
			
			while(rs.next()) {
				result.add(readVerifyRequest(rs));
			}
			
			return result;
		}
	}
	
	public boolean addSealedDocuments(List<SealedDocument> sealedDocuments) throws Exception{
		
		try (Connection connection = getConnection()) {
			String sql = "Insert Into SealedDocument(id, document_id, document_location, party_id, party_type, secret_key, timestamp) Values(?, ?, ?, ?, ?, ?, ?) ; ";
			PreparedStatement ps = connection.prepareStatement(sql);
			
			for(SealedDocument doc : sealedDocuments) {
				ps.setString(1, doc.getId());
				ps.setString(2, doc.getDocumentId());
				ps.setString(3, doc.getDocumentLocation());
				ps.setString(4, doc.getPartyId());
				ps.setString(5, doc.getPartyType());
				ps.setString(6, doc.getSecretKey());
				ps.setLong(7, doc.getTimestamp().toEpochSecond(getLocalOffset()));
				ps.addBatch();
				
			}
	
			return ps.executeBatch().length > 0;
			
		}
	}
	
	public List<SealedDocument> getSealedDocuments(String partyId, String documentId) throws Exception {
		try (Connection connection = getConnection()) {
			String sql = " Select sd.*, d.title From SealedDocument sd ";
			sql += " Inner Join Document d On d.id = sd.document_id ";
			
			if (partyId != null || documentId != null) {
				sql += " And ";
				if (partyId != null) {
					sql += " party_id = ? ";
				}
				
				else {
					sql += " party_id Is Not Null ";
				}
				
				if (documentId != null) {
					sql += " And document_id = ?";
				}
				
				else {
					sql += " And document_id Is Not Null ";
				}
			}
			
			sql += " Order By timestamp Desc ";

			
			PreparedStatement ps = connection.prepareStatement(sql);
			
			if (partyId != null || documentId != null) {
				int paramIndex = 1;
				if (partyId != null) {
					ps.setString(paramIndex++, partyId);
				}
				
				if (documentId != null) {
					ps.setString(paramIndex++, documentId);
				}
			}
			
			ResultSet rs = ps.executeQuery();
			List<SealedDocument> result = new ArrayList<SealedDocument>();
			
			while(rs.next()) {
				result.add(readSealedDocument(rs));
			}
			
			return result;
		}
	}
	
	public boolean addDocumentSignature(DocumentSignature sign, String documentId) throws Exception{
		try (Connection connection = getConnection()) {
			String sql = " Insert Into DocumentSignature (id, sign, timestamp, enterprise_id, document_id) Values(?, ?, ?, ?, ?); ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, sign.getId());
			ps.setString(2, sign.getSign());
			ps.setLong(3, sign.getTimestamp().toEpochSecond(getLocalOffset()));
			ps.setString(4, sign.getEnterprise().getId());
			ps.setString(5, documentId);
			return ps.execute();
			
		}
	}
	
	private SealedDocument readSealedDocument(ResultSet rs) throws Exception {
		String id  = rs.getString("id");
		String documentId = rs.getString("document_id");
		String documentLocation = rs.getString("document_location");
		String partyId = rs.getString("party_id");
		String partyType = rs.getString("party_type");
		String secretKey = rs.getString("secret_key");
		String documentTitle = rs.getString("title");
		LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(rs.getLong("timestamp")), getLocalOffset());
		
		return new SealedDocument(id, documentId, documentTitle, documentLocation, partyId, partyType, secretKey, timestamp);
		
	}
	
	private String getSignRequestQuery(String whereClause) {
		String sql = " Select sr.*, e.name e_name, e.short_name e_short_name, s.title s_title, d.title d_title, d.hash d_hash, d.user_sign d_sign, c.full_name c_name From SignRequest sr  ";
		sql += " Inner Join Enterprise e On e.id = sr.enterprise_id ";
		sql += " Inner Join EnterpriseService s On s.id = sr.service_id ";
		sql += " Inner Join Document d On d.id = sr.document_id ";
		sql += " Inner Join Client c On c.id = sr.user_id ";
		sql += " Where " + whereClause;
		sql += " Order By request_time desc ";
		return sql;
	}
	
	private String getVerifyRequestQuery(String whereClause) {
		String sql = " Select vr.*, e.name e_name, e.short_name e_short_name, s.title s_title, d.title d_title, d.hash d_hash, d.user_sign d_sign, c.full_name c_name From VerifyRequest vr  ";
		sql += " Inner Join Enterprise e On e.id = vr.enterprise_id ";
		sql += " Inner Join EnterpriseService s On s.id = vr.service_id ";
		sql += " Inner Join Document d On d.id = vr.document_id ";
		sql += " Inner Join Client c On c.id = vr.user_id ";
		sql += " Where " + whereClause;
		sql += " Order By request_time desc ";
		return sql;
	}
	
	private SignRequest readSignRequest(ResultSet rs) throws Exception {
		
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
		req.setContractAddress(rs.getString("contract_address"));
		
		String enterpriseName = rs.getString("e_name");
		String enterpriseShortName = rs.getString("e_short_name");
		String serviceName = rs.getString("s_title");
		String documentTitle = rs.getString("d_title");
		String clientName = rs.getString("c_name");
		
		String docHash = rs.getString("d_hash");
		String sign = rs.getString("d_sign");
		
		req.setClient(new Client(req.getUserId(), clientName, null));
		req.setEnterprise(new Enterprise(req.getEnterpriseId(), enterpriseName, enterpriseShortName, null));
		req.setService(new EnterpriseService(req.getServiceId(), serviceName, null, null));
		
		Document document = new Document(req.getDocumentId(), documentTitle, docHash, sign);
		document.setSignHistory(getDocumentSignatures(req.getDocumentId()));
		req.setDocument(document);
		
		return req;
	}
	
	private VerifyRequest readVerifyRequest(ResultSet rs) throws Exception {
		
		VerifyRequest req = new VerifyRequest();
		req.setId(rs.getString("id"));
		req.setUserId(rs.getString("user_id"));
		req.setEnterpriseId(rs.getString("enterprise_id"));
		req.setServiceId(rs.getString("service_id"));
		req.setDocumentId(rs.getString("document_id"));
		req.setRequestTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(rs.getInt("request_time")), getLocalOffset()));
		//req.setRequestTime(rs.getTimestamp("request_time").toLocalDateTime());
		req.setStatus(RequestStatus.getStatusById(rs.getInt("status")));
		req.setContractAddress(rs.getString("contract_address"));
		
		String enterpriseName = rs.getString("e_name");
		String enterpriseShortName = rs.getString("e_short_name");
		String serviceName = rs.getString("s_title");
		String documentTitle = rs.getString("d_title");
		String clientName = rs.getString("c_name");
		
		String docHash = rs.getString("d_hash");
		String sign = rs.getString("d_sign");
		
		req.setClient(new Client(req.getUserId(), clientName, null));
		req.setEnterprise(new Enterprise(req.getEnterpriseId(), enterpriseName, enterpriseShortName, null));
		req.setService(new EnterpriseService(req.getServiceId(), serviceName, null, null));
		
		Document document = new Document(req.getDocumentId(), documentTitle, docHash, sign);
		document.setSignHistory(getDocumentSignatures(req.getDocumentId()));
		req.setDocument(document);
		
		return req;
	}
	
	public List<DocumentSignature> getDocumentSignatures(String documentId) throws Exception{
		try(Connection connection = getConnection()) {
			String sql = " Select ds.*, e.name, e.short_name, e.public_key From DocumentSignature ds ";
			sql += " Inner Join Enterprise e On e.id = ds.enterprise_id ";
			sql += " Where ds.document_id = ? ";
			sql += " Order By ds.timestamp ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, documentId);
			List<DocumentSignature> result = new ArrayList<DocumentSignature>();
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String sign = rs.getString("sign");
				String enterpriseId = rs.getString("enterprise_id");
				LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(rs.getLong("timestamp")), getLocalOffset());
				String enterpriseName = rs.getString("name");
				String enterpriseShortName = rs.getString("short_name");
				String publicKey = rs.getString("public_key");
				
				Enterprise enterprise = new Enterprise(enterpriseId, enterpriseName, enterpriseShortName, publicKey);
				
				result.add(new DocumentSignature(id, sign, enterprise, timestamp));
				
			}
			
			return result;
			
		}
	}
	
	public List<String> getContractsAddresses(String documentId) throws Exception{
		try (Connection connection = getConnection()) {
			String sql = " Select contract_address From SignRequest Where document_id = ? order by request_time ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, documentId);
			ResultSet rs = ps.executeQuery();
			List<String> result = new ArrayList<String>();
			
			while (rs.next()) {
				result.add(rs.getString("contract_address"));
			}
			
			return result;
		}
	}
	
	public boolean deleteCompletedSignRequestsByDocument(String documentId) throws Exception {
		try (Connection connection = getConnection()) {
			String sql = " Delete From SignRequest ";
			sql += " Where document_id = ?  And status = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, documentId);
			ps.setInt(2, RequestStatus.ACCEPTED.getId());
			return ps.execute();
		}
	}
	
	public boolean deleteCompletedVerifyRequestsByDocument(String documentId) throws Exception {
		try (Connection connection = getConnection()) {
			String sql = " Delete From VerifyRequest ";
			sql += " Where document_id = ?  And status = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, documentId);
			ps.setInt(2, RequestStatus.ACCEPTED.getId());
			return ps.execute();
		}
	}
	
	private Connection getConnection() throws Exception{
		
//		if (this.connection == null || this.connection.isClosed()) {
//			this.connection = DriverManager.getConnection(databaseUrl);
//		}
		return DriverManager.getConnection(databaseUrl);
	}
	private static Connection getConnectionAsStatic() throws Exception{
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
