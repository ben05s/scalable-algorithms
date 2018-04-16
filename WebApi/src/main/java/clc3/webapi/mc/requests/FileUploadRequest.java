package clc3.webapi.mc.requests;

public class FileUploadRequest {
    String fileName;
    String fileContent;

    public FileUploadRequest() {}

    public String getFileName() { return this.fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileContent() { return this.fileContent; }
    public void setFileContent(String fileContent) { this.fileContent = fileContent; }
}