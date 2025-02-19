package history.traveler.rollingkorea.question.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class File {

    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    @Column(name = "file_name")
    private String fileName; // file_name 추가

    public File(byte[] fileData, String fileName) {
        this.fileData = fileData;
        this.fileName = fileName;
    }
}
