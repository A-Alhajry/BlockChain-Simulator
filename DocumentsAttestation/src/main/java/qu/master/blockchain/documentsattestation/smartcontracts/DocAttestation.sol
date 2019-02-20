pragma solidity ^0.4.0;

contract CertifyDocumentContract {
    
    string private docHash;
    string private digiSign;
    string private pubKey;
    string private docLoc;
    string private srvId;
    address private owner;
    
    constructor() public {
        owner = msg.sender;
    }
    
    function setCertification(string _docHash, string _digiSign, string _pubKey, string _docLoc, string _srvId) public {
        
        docHash = _docHash;
        digiSign = _digiSign;
        pubKey = _pubKey;
        docLoc = _docLoc;
        srvId = _srvId;
    }
    
    function getCertification() public constant returns (string, string, string, string, string) {
        return (docHash, digiSign, pubKey, docLoc, srvId);
    }
    
}