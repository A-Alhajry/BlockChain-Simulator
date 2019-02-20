package qu.master.blockchain.documentsattestation.controllers;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import qu.master.blockchain.documentsattestation.AppUtils;
import qu.master.blockchain.documentsattestation.Logger;
import qu.master.blockchain.documentsattestation.models.BeansRepository;
import qu.master.blockchain.documentsattestation.models.FilesManager;
import qu.master.blockchain.documentsattestation.models.UsersManager;
import qu.master.blockchain.documentsattestation.models.beans.AttestationFile;
import qu.master.blockchain.documentsattestation.models.beans.Client;
import qu.master.blockchain.documentsattestation.models.beans.CryptoModel;
import qu.master.blockchain.documentsattestation.models.beans.Document;
import qu.master.blockchain.documentsattestation.models.beans.DocumentSignature;
import qu.master.blockchain.documentsattestation.models.beans.DocumentStatus;
import qu.master.blockchain.documentsattestation.models.beans.DocumentStatusType;
import qu.master.blockchain.documentsattestation.models.beans.Enterprise;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseService;
import qu.master.blockchain.documentsattestation.models.beans.EnterpriseServiceType;
import qu.master.blockchain.documentsattestation.models.beans.FileRecord;
import qu.master.blockchain.documentsattestation.models.beans.RequestStatus;
import qu.master.blockchain.documentsattestation.models.beans.SealedDocument;
import qu.master.blockchain.documentsattestation.models.beans.SignRequest;
import qu.master.blockchain.documentsattestation.models.beans.VerifyRequest;
import qu.master.blockchain.documentsattestation.smartcontracts.CertificationFromContract;
import qu.master.blockchain.documentsattestation.smartcontracts.ContractsManager;

public class ApplicationController {
	
	private BeansRepository beansRepo;
	
	public ApplicationController() {
		beansRepo = new BeansRepository();
	}
	
	public List<Enterprise> getEnterprises(){
		try {
			return beansRepo.getEnterprisesList();
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<EnterpriseService> getSignServices(String enterpriseId) {
		try {
			return beansRepo.getEnterprisesServicesList(enterpriseId, EnterpriseServiceType.SIGN);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<EnterpriseService> getVerifyServices(String enterpriseId) {
		try {
			return beansRepo.getEnterprisesServicesList(enterpriseId, EnterpriseServiceType.VERIFY);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void createSignRequest(String password, String enterpriseId, String serviceId, String documentPath, String comments) {
		try {
			
			if (documentPath.contains(".atts")) {
				createSignRequestFromExistingDocument(password, documentPath, enterpriseId, serviceId, comments);
			}
			
			else {
				createSignRequestFromNewDocument(password, documentPath, enterpriseId, serviceId, comments);
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public List<SignRequest> getClientSignRequests() {
		try {
			BeansRepository repo = new BeansRepository();
			return repo.getSignRequestsByClient(AppUtils.getCurrentUserId());
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public List<SignRequest> getEnterpriseSignRequests(String password) {
		try {
			BeansRepository repo = new BeansRepository();
			List<SignRequest> requests = repo.getSignRequestsByEnterprise(UsersManager.getCurrentUserId());
			List<String> contractsAddresses = requests.stream().map(r -> r.getContractAddress()).collect(Collectors.toList());
			ContractsManager ctMgr = new ContractsManager(password, new File("public_keys" + File.separator + UsersManager.getCurrentUserId()));
			List<CertificationFromContract> certs = ctMgr.getCertifications(contractsAddresses);
			for(int i = 0;i < requests.size(); i++) {
				
				validateSignRequest(requests.get(i), certs.get(i), getSealedDocument(requests.get(i).getDocumentId()), password);
			}
			return requests;
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public List<VerifyRequest> getClientVerifyRequests() {
		try {
			BeansRepository repo = new BeansRepository();
			return repo.getVerifyRequestsByClient(AppUtils.getCurrentUserId());
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public List<VerifyRequest> getEnterpriseVerifyRequests(String password) {
		try {
			BeansRepository repo = new BeansRepository();
			List<VerifyRequest> requests = repo.getVerifyRequestsByEnterprise(UsersManager.getCurrentUserId());
			List<String> contractsAddresses = requests.stream().map(r -> r.getContractAddress()).collect(Collectors.toList());
			ContractsManager ctMgr = new ContractsManager(password, new File("public_keys" + File.separator + UsersManager.getCurrentUserId()));
			List<CertificationFromContract> certs = ctMgr.getCertifications(contractsAddresses);
			for(int i = 0;i < requests.size(); i++) {
				
				validateVerifyRequest(requests.get(i), certs.get(i), getSealedDocument(requests.get(i).getDocumentId()), password);
			}
			return requests;
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public boolean acceptSignRequest(String documentId, String requestId, String password) {
		try {
			BeansRepository repo = new BeansRepository();
			Enterprise enterprise = new Enterprise();
			enterprise.setId(UsersManager.getCurrentUserId());
			CryptoModel cm = new CryptoModel(password, enterprise.getId());
			String documentPath = getDocumentPath(documentId, password);
			List<DocumentSignature> signs = repo.getDocumentSignatures(documentId);
			Logger.log("Signatures Size  = " + signs.size());
			byte[] documentContent = AppUtils.readBytes(documentPath);;
			byte[] dataToSign = documentContent;
			if (!signs.isEmpty()) {
				Logger.log("Display Signs");
				for(DocumentSignature sign : signs) {
					Logger.log("Sign Data = " + sign.getSign());
					Logger.log("Sign Enterprise = " + sign.getEnterprise().getName());
					Logger.log("Sign Timestamp = " + sign.getTimestamp().toString());
				}
			}
//			
//			else {
//				List<Byte> entrpSigns = new ArrayList<Byte>();
//				for(byte docByte : documentContent) {
//					entrpSigns.add(docByte);
//				}
//				
//				for(DocumentSignature sign : signs) {
//					
//				}
//			}
			String sign = cm.signData(dataToSign);
			DocumentSignature docSign = new DocumentSignature(AppUtils.getRandomId(), sign, enterprise, LocalDateTime.now());
			repo.addDocumentSignature(docSign, documentId);
			repo.deleteCompletedSignRequestsByDocument(documentId);
			return repo.updateSignRequestStatus(requestId, RequestStatus.ACCEPTED);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public boolean rejectSignRequest(String requestId) {
		try {
			BeansRepository repo = new BeansRepository();
			return repo.updateSignRequestStatus(requestId, RequestStatus.REJECTED);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public boolean acceptVerifyRequest(String documentId, String requestId, String password) {
		try {
			BeansRepository repo = new BeansRepository();
			repo.deleteCompletedVerifyRequestsByDocument(documentId);
			return repo.updateVerifyRequestStatus(requestId, RequestStatus.ACCEPTED);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public boolean rejectVerifyRequest(String requestId) {
		try {
			BeansRepository repo = new BeansRepository();
			return repo.updateVerifyRequestStatus(requestId, RequestStatus.REJECTED);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public SealedDocument getSealedDocument(String documentId) {
		try {
			BeansRepository repo = new BeansRepository();
			return repo.getSealedDocuments(UsersManager.getCurrentUserId(), documentId).get(0);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public String getDocumentPath(String documentId, String password) {
		try {
			BeansRepository repo = new BeansRepository();
			SealedDocument doc = repo.getSealedDocuments(UsersManager.getCurrentUserId(), documentId).get(0);
			CryptoModel cm = new CryptoModel(password, UsersManager.getCurrentUserId());
			byte[] documentContent = new FilesManager().getFile(doc.getDocumentLocation());
			String tempFile = AppUtils.createTempFile();
			AppUtils.writeBytes(documentContent, tempFile);
			String documentPath = "Temp" + File.separator + doc.getDocumentTitle();
			cm.decryptData(null, doc.getSecretKey(), tempFile, documentPath);
			return documentPath;
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public String saveAndGetAttestationFile(Document document, String folder) {
		try {
			AttestationFile result = new AttestationFile();
			String documentId = document.getId();
			BeansRepository repo = new BeansRepository();
			List<DocumentSignature> signs = repo.getDocumentSignatures(documentId);
			List<SealedDocument> sealedDocuments = repo.getSealedDocuments(null, documentId);
			
			SealedDocument userSealedDoc = sealedDocuments.stream().filter((sd) -> sd.getPartyId().equals(UsersManager.getCurrentUserId())).findFirst().get();
			List<SealedDocument> entrpSealedDoc = sealedDocuments.stream().filter(sd -> !sd.getId().equals(UsersManager.getCurrentUserId())).collect(Collectors.toList()); 
			
			Collections.sort(signs, (s1, s2) -> s1.getTimestamp().compareTo(s2.getTimestamp()));
			Collections.sort(entrpSealedDoc, (sd1, sd2) -> sd1.getTimestamp().compareTo(sd2.getTimestamp()));
			
			result.DocumentId = documentId;
			result.UserSign = document.getUserSign();
			result.EntrpIds = signs.stream().map((s) -> s.getEnterprise().getId()).collect(Collectors.toList());
			result.EntrpNames = signs.stream().map((s) -> s.getEnterprise().getShortName()).collect(Collectors.toList());
			result.EntrpSigns = signs.stream().map((s) -> s.getSign()).collect(Collectors.toList());
			result.DocumentLocations = entrpSealedDoc.stream().map(sd -> sd.getDocumentLocation()).collect(Collectors.toList());
			result.DocumentLocations.add(0, userSealedDoc.getDocumentLocation());
			result.ContractsAddresses = repo.getContractsAddresses(documentId);
			
			String filePath = folder + File.separator + document.getTitle().replaceAll(".pdf", "") + ".atts";
			AppUtils.writeObject(result, filePath);
			return filePath;
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
	
	public boolean createVerifyRequest(String password, String enterpriseId, String serviceId,String attestationFilePath, String comments) throws Exception{
		
		try {
			BeansRepository repo = new BeansRepository();
			Object attestationObj = AppUtils.readObject(attestationFilePath);
			
			if (!(attestationObj instanceof AttestationFile)) {
				throw new Exception("Invalid Attestation File");
			}
			
			AttestationFile attestationFile = (AttestationFile) attestationObj;
			Document document = repo.getDocuments(attestationFile.DocumentId).get(0);
			if (!(attestationFile.UserSign.equals(document.getUserSign()))) {
				throw new Exception("Invalid Attestation File");
			}
			
			Client client = AppUtils.getCurrentClient();
			CryptoModel ucm = new CryptoModel(password, client.getId());
			CryptoModel ecm = new CryptoModel(password, enterpriseId);
			LocalDateTime now = LocalDateTime.now();
			
			List<DocumentSignature> signs = repo.getDocumentSignatures(document.getId());
			String publicKey = client.getPublicKey() + ",";
			publicKey += signs.stream().map((s) -> s.getSign()).collect(Collectors.joining(","));
			
			SealedDocument sealedDoc = repo.getSealedDocuments(client.getId(), document.getId()).get(0);
			byte[] fileContent = new FilesManager().getFile(sealedDoc.getDocumentLocation());
			String input = AppUtils.createTempFile();
			String output = AppUtils.createTempFile();
			AppUtils.writeBytes(fileContent, input);
			
			byte[] rawContent = ucm.decryptData(fileContent, sealedDoc.getSecretKey(), input, output);
			input = AppUtils.createTempFile();
			AppUtils.writeBytes(rawContent, input);
			output = AppUtils.createTempFile();
			String secretKey = ecm.encryptData(rawContent, input, output);
			String location = new FilesManager().addFile(new File(output));
			SealedDocument newSealedDocument = new SealedDocument(AppUtils.getRandomId(), document.getId(), document.getTitle(), location, enterpriseId, "Enterprise", secretKey, now);
			List<SealedDocument> sealedDocuments = new ArrayList<SealedDocument>();
			sealedDocuments.add(newSealedDocument);
			repo.addSealedDocuments(sealedDocuments);
			
			ContractsManager ctMgr = new ContractsManager(password, new File("public_keys" + File.separator + UsersManager.getCurrentUserId()));
			String contractAddress = ctMgr.deployCertifyDocument();
			ctMgr.setCertification(contractAddress, document, location, serviceId, publicKey);
			
			VerifyRequest req = new VerifyRequest();
			req.setId(AppUtils.getRandomId());
			req.setContractAddress(contractAddress);
			req.setDocumentId(document.getId());
			req.setEnterpriseId(enterpriseId);
			req.setRequestTime(now);
			req.setServiceId(serviceId);
			req.setStatus(RequestStatus.IN_PROGRESS);
			req.setUserId(client.getId());
			
			return repo.addVerifyRequest(req);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw AppUtils.getError(e);
		}
	}
		
	private void validateSignRequest(SignRequest req, CertificationFromContract cert, SealedDocument sealedDocument, String password) throws Exception {
		byte[] sealedContent = new FilesManager().getFile(cert.DocumentLocation);
		String input = AppUtils.createTempFile();
		AppUtils.writeBytes(sealedContent, input);
		String output = AppUtils.createTempFile();
		CryptoModel cm = new CryptoModel(password, UsersManager.getCurrentUserId());
		cm.decryptData(null, sealedDocument.getSecretKey(), input, output);
		byte[] documentContent = AppUtils.readBytes(output);
		String fileHash = AppUtils.getHash(documentContent);
		String[] keys = cert.PublicKey.split(",");
		req.setIsDocumentValid(req.getDocument().getHash().substring(0).equals(fileHash));
	}
	
	private void validateVerifyRequest(VerifyRequest req, CertificationFromContract cert, SealedDocument sealedDocument, String password) throws Exception {
		byte[] sealedContent = new FilesManager().getFile(cert.DocumentLocation);
		String input = AppUtils.createTempFile();
		AppUtils.writeBytes(sealedContent, input);
		String output = AppUtils.createTempFile();
		CryptoModel cm = new CryptoModel(password, UsersManager.getCurrentUserId());
		cm.decryptData(null, sealedDocument.getSecretKey(), input, output);
		byte[] documentContent = AppUtils.readBytes(output);
		String fileHash = AppUtils.getHash(documentContent);
		String[] keys = cert.PublicKey.split(",");
		req.setIsDocumentValid(req.getDocument().getHash().substring(0).equals(fileHash));
	}
	
	private boolean createSignRequestFromNewDocument(String password, String documentPath, String enterpriseId, String serviceId,String comments) throws Exception{
		Client currentClient = AppUtils.getCurrentClient();
		CryptoModel clientCrypto = new CryptoModel(password, currentClient.getId());
		CryptoModel entrpCrypto = new CryptoModel(password, enterpriseId);
		File documentFile = new File(documentPath);
		byte[] fileContent = AppUtils.readBytes(documentPath);
		String fileHash = AppUtils.getHash(fileContent);
		String userSign = clientCrypto.signData(fileContent);
		LocalDateTime now = LocalDateTime.now();
		
		Document document = new Document();
		DocumentStatus status = new DocumentStatus();
		
		document.setId(AppUtils.getRandomId());
		document.setUserSign(userSign);
		document.setHash(fileHash);
		document.setCreationTime(now);
		document.setOwner(currentClient);
		document.setRawFile(fileContent);
		document.setTitle(documentFile.getName());
		
		status.setId(AppUtils.getRandomId());
		status.setStatusTime(now);
		status.setType(DocumentStatusType.CREATED);
		
		BeansRepository repo = new BeansRepository();
		repo.addDocument(document);
		repo.addDocumentStatus(document.getId(), status);
		
		status.setType(DocumentStatusType.SENT_TO_SIGN);
		repo.addDocumentStatus(document.getId(), status);
		
		FilesManager mgr = new FilesManager();
		//String fileAddress = mgr.addFile(documentFile);
		
		//FileRecord record = new FileRecord(AppUtils.getRandomId(), document.getId(), 1, Document.class, fileAddress);
		//repo.saveBeanRecord(record);
		
		String clientFile = AppUtils.createTempFile();
		String entrpFile = AppUtils.createTempFile();
		
		String clientKey = clientCrypto.encryptData(fileContent, documentFile.getAbsolutePath(), clientFile);
		String entrpKey = entrpCrypto.encryptData(fileContent, documentFile.getAbsolutePath(), entrpFile);
		
		//System.out.println("Document Id = " + document.getId());
		//System.out.println(clientKey);
		
		String clientFileAddress = mgr.addFile(new File(clientFile));
		String entrpFileAddress = mgr.addFile(new File(entrpFile));
		
		FileRecord clientRecord = new FileRecord(AppUtils.getRandomId(), document.getId(), 1, Document.class, clientFileAddress);
		FileRecord entrpRecord = new FileRecord(AppUtils.getRandomId(), document.getId(), 1, Document.class, entrpFileAddress);
		
		repo.saveBeanRecord(clientRecord);
		repo.saveBeanRecord(entrpRecord);
		
		List<SealedDocument> sealedDocuments = new ArrayList<SealedDocument>();
		sealedDocuments.add(new SealedDocument(AppUtils.getRandomId(), document.getId(), document.getTitle(), clientFileAddress, currentClient.getId(), "Client", clientKey, now));
		sealedDocuments.add(new SealedDocument(AppUtils.getRandomId(), document.getId(), document.getTitle(), entrpFileAddress,enterpriseId, "Enterprise", entrpKey, now));
		repo.addSealedDocuments(sealedDocuments);
		ContractsManager ctMgr = new ContractsManager(password, new File("public_keys" + File.separator + UsersManager.getCurrentUserId()));
		String contractAddress = ctMgr.deployCertifyDocument();
		ctMgr.setCertification(contractAddress, document, entrpFileAddress, serviceId, currentClient.getPublicKey());
					
		SignRequest request = new SignRequest();
		request.setId(AppUtils.getRandomId());
		request.setUserId(currentClient.getId());
		request.setEnterpriseId(enterpriseId);
		request.setServiceId(serviceId);
		request.setDocumentId(document.getId());
		request.setRequestTime(now);
		request.setComments(comments);
		request.setStatus(RequestStatus.IN_PROGRESS);
		request.setContractAddress(contractAddress);
		
		Logger.log("Document Hash = " + document.getHash());
		Logger.log("User Sign Hash = " + document.getUserSign());
		Logger.log("Enterprise Id = " + enterpriseId);
		Logger.log("User Public Key = " + currentClient.getPublicKey());
		
		return repo.addSignRequest(request);
	}
	
	private boolean createSignRequestFromExistingDocument(String password, String attestationFilePath, String enterpriseId, String serviceId, String comments) throws Exception {
		BeansRepository repo = new BeansRepository();
		Object attestationObj = AppUtils.readObject(attestationFilePath);
		
		if (!(attestationObj instanceof AttestationFile)) {
			throw new Exception("Invalid Attestation File");
		}
		
		AttestationFile attestationFile = (AttestationFile) attestationObj;
		Document document = repo.getDocuments(attestationFile.DocumentId).get(0);
		if (!(attestationFile.UserSign.equals(document.getUserSign()))) {
			throw new Exception("Invalid Attestation File");
		}
		
		Client client = AppUtils.getCurrentClient();
		CryptoModel ucm = new CryptoModel(password, client.getId());
		CryptoModel ecm = new CryptoModel(password, enterpriseId);
		LocalDateTime now = LocalDateTime.now();
		
		List<DocumentSignature> signs = repo.getDocumentSignatures(document.getId());
		String publicKey = client.getPublicKey() + ",";
		publicKey += signs.stream().map((s) -> s.getSign()).collect(Collectors.joining(","));
		
		SealedDocument sealedDoc = repo.getSealedDocuments(client.getId(), document.getId()).get(0);
		byte[] fileContent = new FilesManager().getFile(sealedDoc.getDocumentLocation());
		String input = AppUtils.createTempFile();
		String output = AppUtils.createTempFile();
		AppUtils.writeBytes(fileContent, input);
		
		byte[] rawContent = ucm.decryptData(fileContent, sealedDoc.getSecretKey(), input, output);
		input = AppUtils.createTempFile();
		AppUtils.writeBytes(rawContent, input);
		output = AppUtils.createTempFile();
		String secretKey = ecm.encryptData(rawContent, input, output);
		String location = new FilesManager().addFile(new File(output));
		SealedDocument newSealedDocument = new SealedDocument(AppUtils.getRandomId(), document.getId(), document.getTitle(), location, enterpriseId, "Enterprise", secretKey, now);
		List<SealedDocument> sealedDocuments = new ArrayList<SealedDocument>();
		sealedDocuments.add(newSealedDocument);
		repo.addSealedDocuments(sealedDocuments);
		
		ContractsManager ctMgr = new ContractsManager(password, new File("public_keys" + File.separator + UsersManager.getCurrentUserId()));
		String contractAddress = ctMgr.deployCertifyDocument();
		ctMgr.setCertification(contractAddress, document, location, serviceId, publicKey);
		
		SignRequest req = new SignRequest();
		req.setId(AppUtils.getRandomId());
		req.setComments(comments);
		req.setContractAddress(contractAddress);
		req.setDocumentId(document.getId());
		req.setEnterpriseId(enterpriseId);
		req.setRequestTime(now);
		req.setServiceId(serviceId);
		req.setStatus(RequestStatus.IN_PROGRESS);
		req.setUserId(client.getId());
		
		return repo.addSignRequest(req);
	}
	
	
}