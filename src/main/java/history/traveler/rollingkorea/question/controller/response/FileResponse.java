package history.traveler.rollingkorea.question.controller.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public record FileResponse(byte[] fileData, String fileName) {

    // Constructor is implicitly provided by the record type.

    // Convert a File entity to FileResponse
    public static FileResponse fromFile(history.traveler.rollingkorea.question.domain.File file) {
        return new FileResponse(file.getFileData(), file.getFileName());
    }

    // Create HTTP headers for file download
    public static HttpHeaders getFileHeaders(FileResponse fileResponse) {
        HttpHeaders headers = new HttpHeaders();

        // Set Content-Disposition to force file download with the file name
        headers.add("Content-Disposition", "attachment; filename=\"" + fileResponse.fileName() + "\"");

        // Set Content-Type to application/octet-stream for binary file data
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return headers;
    }

    // Override toString to provide meaningful information about the file
    @Override
    public String toString() {
        return "FileResponse{" +
                "fileName='" + fileName + '\'' +
                ", fileDataLength=" + (fileData != null ? fileData.length : 0) + " bytes" +
                '}';
    }
}
