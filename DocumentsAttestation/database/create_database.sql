Drop Table If Exists Client;

Drop Table If Exists Enterprise;

Drop Table If Exists Admin;

Drop Table If Exists Document;

Drop Table If Exists DocumentSignature;

Drop Table If Exists DocumentStatusType;

Drop Table If Exists DocumentStatus;

Drop Table If Exists EnterpriseServiceType;

Drop Table If Exists EnterpriseService;

Drop Table If Exists SignRequest;

Drop Table If Exists SignResponse;

Drop Table If Exists VerifyRequest;

Drop Table If Exists VerifyResponse;

Drop Table If Exists FileRecordType;

Drop Table If Exists FileRecord;


Create Table If Not Exists Client (
  id varchar,
  full_name varchar,
  username varchar,
  password varchar,
  public_key varchar
);

Create Table If Not Exists Enterprise (
  id varchar,
  name varchar,
  public_key varchar
);

Create Table If Not Exists Admin (
  id varchar,
  full_name varchar,
  public_key varchar,
  enterprise_id varchar
);

Create Table If Not Exists Document (
  id varchar,
  title varchar,
  creation_time datetime,
  hash varchar,
  user_sign varchar,
  owner_id varchar
);

Create Table If Not Exists DocumentSignature (
  id varchar,
  sign varchar,
  enterprise_id varchar,
  timestamp datetime
);

Create Table If Not Exists DocumentStatusType (
  id integer,
  name varchar
);

Create Table If Not Exists DocumentStatus (
  id varchar,
  timestamp datetime,
  type_id integer
);

Create Table If Not Exists EnterpriseServiceType (
  id int,
  name varchar
);

Create Table If Not Exists EnterpriseService (
  id varchar,
  enterprise_id varchar,
  title varchar,
  type_id int,
  desc varchar,
  supported_files varchar
);

Create Table If Not Exists SignRequest (
  id varchar,
  user_id varchar,
  enterprise_id varchar,
  service_id varchar,
  document_id varchar,
  request_time integer,
  contract_address varchar,
  comments varchar,
  status integer
);

Create Table If Not Exists SignResponse (
  id varchar,
  status integer,
  response_time datetime,
  document_sign_id varchar,
  contract_address varchar
);

Create Table If Not Exists VerifyRequest (
  id varchar,
  user_id varchar,
  enterprise_id varchar,
  request_time integer,
  contract_address varchar,
  document_sign_id varchar,
  status integer
);

Create Table If Not Exists VerifyResponse (
  id varchar,
  response_time varchar,
  status integer
);

Create Table If Not Exists FileRecordType (
  id int,
  name varchar
);

Create Table If Not Exists FileRecord (
  id varchar,
  bean_id,
  type_id int,
  class_name varchar,
  address varchar
);

Delete From Client;
Delete From Admin;
Delete From Enterprise;
Delete From DocumentStatusType;
Delete From EnterpriseService;
Delete From EnterpriseServiceType;

Insert Into Client(id, full_name, public_key, username, password) Values('6aefdda3-c1c8-4a8e-a5d6-7d314b6a2994', 'Abdulrahman Alkhayarin', '0x5c26cebcc951b5fcb8ce30423bd89e1fa59d3c96', 'aalkhayarin', 'mypassword');

Insert Into Enterprise(id, name, public_key) Values('8bdb2178-8484-4fde-a4b1-d2e7fcacb771', 'Qatar University', '0x3e173db2f7eeb59894fc9c969c85334abfc170ae');
Insert Into Enterprise(id, name, public_key) Values('16f1fd8b-b80b-43e4-9e27-9aa78b7f6a5f', 'Hamad Medical Corporation', '0x28c2add8276941044e1f112afa72c2b44ce1894a');
Insert Into Enterprise(id, name, public_key) Values('3771af1f-28d2-4734-952e-71031f15cc9d', 'Ministry Of Interior', '0xf9448908e474bcf95bb7e681a6a7058bab5e7750');

Insert Into EnterpriseServiceType(id, name) Values(1, 'Sign Service');
Insert Into EnterpriseServiceType(id, name) Values(2, 'Verify Service');

Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('c38169f6-d514-4ab7-9176-2fd56426ea72', '8bdb2178-8484-4fde-a4b1-d2e7fcacb771', 'Graduation Statement', 1, '', 'pdf');
Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('e44bcd93-d10f-4a6e-982b-b54bdd2fcf49', '8bdb2178-8484-4fde-a4b1-d2e7fcacb771', 'Academic Transcript', 1,'', 'pdf');
Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('795c538d-438a-4a22-96f4-2ff440e74364', '8bdb2178-8484-4fde-a4b1-d2e7fcacb771', 'Degree Evaluation', 1,'', 'pdf');

Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('a89f08dc-dbc1-48b6-94ac-a41e62298fbe', '16f1fd8b-b80b-43e4-9e27-9aa78b7f6a5f', 'Birth Certificate', 1,'', 'pdf');
Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('e122ddeb-e086-4d9e-b2e4-c41847ca9c6f', '16f1fd8b-b80b-43e4-9e27-9aa78b7f6a5f', 'Job Certificate', 1,'', 'pdf');

Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('ffc156fe-7fc1-4f51-8e22-63dd67a9c98a', '3771af1f-28d2-4734-952e-71031f15cc9d', 'Certificate Of Good Conduct', 1, '', 'pdf');


Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('9fd907fd-70d0-42d9-b6bb-b14cfe6ebd9b', '8bdb2178-8484-4fde-a4b1-d2e7fcacb771', 'Graduation Statement', 2, '', 'pdf');
Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('6be08d4c-eaad-4f0a-aa05-a12c8386af49', '8bdb2178-8484-4fde-a4b1-d2e7fcacb771', 'Academic Transcript', 2, '', 'pdf');
Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('d36aef7a-cd0b-407f-b75c-c1282f8def10', '8bdb2178-8484-4fde-a4b1-d2e7fcacb771', 'Degree Evaluation', 2, '', 'pdf');

Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('df7b139d-e997-4e74-82dc-f0746d31c0e0', '16f1fd8b-b80b-43e4-9e27-9aa78b7f6a5f', 'Birth Certificate', 2, '', 'pdf');
Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('3fde54e6-e1c0-471f-b6cc-c9ed362297fd', '16f1fd8b-b80b-43e4-9e27-9aa78b7f6a5f', 'Job Certificate', 2, '', 'pdf');

Insert Into EnterpriseService(id, enterprise_id, title, type_id, desc, supported_files) Values('7c62797c-1a42-4842-b130-ac87f6bea81f', '3771af1f-28d2-4734-952e-71031f15cc9d', 'Certificate Of Good Conduct', 2, '', 'pdf');

Insert Into DocumentStatusType(id, name) Values(1, 'Created');
Insert Into DocumentStatusType(id, name) Values(2, 'Sent To Sign');
Insert Into DocumentStatusType(id, name) Values(3, 'Signed');
Insert Into DocumentStatusType(id, name) Values(4, 'Sent To Verify');
Insert Into DocumentStatusType(id, name) Values(5, 'Verifed');
Insert Into DocumentStatusType(id, name) Values(6, 'Sign Rejected');
Insert Into DocumentStatusType(id, name) Values(7, 'Verify Rejected');
Insert Into DocumentStatusType(id, name) Values(8, 'Deleted');
