package com.malcomjones.ironman;

import java.util.Base64;

/**
 * Created by majones95 on 5/4/18.
 */

public class ConsentString {
    private String version;
    private String created;
    private String modified;
    private String cmpId;
    private String cmpVersion;
    private String consentScreen;
    private String consentLanguage;
    private String vendorListVersion;
    private String purposesAllowed;
    private String maxVendorId;
    private String encodingType;
    private String bitFieldSection;
    private String bitField;
    private String rangeSection;
    private String defaultConsent;
    private String numEntries;
    private String rangeEntry;
    private String singleOrRange;
    private String singleVendorId;
    private String startVendorId;
    private String endVendorId;

    private String consentString;

    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();

    public ConsentString() {
        this.version = binaryConverter("version", "1");
        this.created = binaryConverter("created", "15100811449");
        this.modified = binaryConverter("modified", "15100811450");
    }

//    public void setVersion(String version){
//        this.version = binaryConverter("version", version);
//    }
//
//    public void setCreated(String created) {
//        this.created = binaryConverter("created", created);
//    }
//
//    public void setModified(String modified) {
//        this.modified = binaryConverter("modified", modified);
//    }

    public void setCmpId(String cmpId){
        this.cmpId = binaryConverter("cmpID", cmpId);
    }

    public void setCmpVersion(String cmpVersion) {
        this.cmpVersion = binaryConverter("version", version);
    }

    public void setConsentScreen(String consentScreen) {
        this.consentScreen = binaryConverter("version", version);
    }

    public void setConsentLanguage(String consentLanguage) {
        this.consentLanguage = binaryConverter("version", version);
    }

    public void setVendorListVersion(String vendorListVersion) {
        this.vendorListVersion = binaryConverter("version", version);
    }

    public void setPurposesAllowed(String purposesAllowed) {
        this.purposesAllowed = binaryConverter("version", version);
    }

    public void setMaxVendorId(String maxVendorId) {
        this.maxVendorId = binaryConverter("version", version);
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = binaryConverter("version", version);
    }

    public void setBitFieldSection(String bitFieldSection) {
        this.bitFieldSection = binaryConverter("version", version);
    }

    public void setBitField(String bitField) {
        this.bitField = binaryConverter("version", version);
    }

    public void setRangeSection(String rangeSection) {
        this.rangeSection = binaryConverter("version", version);
    }

    public void setDefaultConsent(String defaultConsent) {
        this.defaultConsent = binaryConverter("version", version);
    }

    public void setNumEntries(String numEntries) {
        this.numEntries = binaryConverter("version", version);
    }

    public void setRangeEntry(String rangeEntry) {
        this.rangeEntry = binaryConverter("version", version);
    }

    public void setSingleOrRange(String singleOrRange) {
        this.singleOrRange = binaryConverter("version", version);
    }

    public void setSingleVendorId(String singleVendorId) {
        this.singleVendorId = binaryConverter("version", version);
    }

    public void setStartVendorId(String startVendorId) {
        this.startVendorId = binaryConverter("version", version);
    }

    public void setEndVendorId(String endVendorId) {
        this.endVendorId = binaryConverter("version", version);
    }

    public void setConsentString(String consentString) {
        this.consentString = binaryConverter("version", version);
    }

    private String binaryConverter(String id, String s){
        Long l = Long.parseLong(s);
        String b = Long.toBinaryString(l);
        String binary ="";
        int maxLength;
        switch(id){
            case "version":
                maxLength = 6;
                binary = String.format("%06d", b);
                binary = binary.substring(binary.length()-maxLength);
                System.out.println("Version: "+ binary);
                break;
            case "created":
                maxLength = 36;
                binary = String.format("%036d", b);
                binary = binary.substring(binary.length()-maxLength);
                System.out.println("Created: "+ binary);
                break;
            case "modified":
                maxLength = 36;
                binary = String.format("%036d", b);
                binary = binary.substring(binary.length()-maxLength);
                System.out.println("Modified: "+ binary);
                break;
            case "cmpId":
                maxLength = 12;
                binary = String.format("%06d", b);
                binary = binary.substring(binary.length()-maxLength);
                System.out.println("cmpId: "+ binary);
                break;
        }
        return binary;
    }

    public String createConsentString(){
        consentString = version + created + modified;
        return consentString;
    }

    public static void main(String[] args){
        ConsentString cs = new ConsentString();
        System.out.print("Consent String: "+ cs.createConsentString());

    }
}