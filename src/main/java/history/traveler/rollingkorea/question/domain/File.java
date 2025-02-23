package history.traveler.rollingkorea.question.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.sql.Blob;

@Embeddable
@Getter
@NoArgsConstructor
@Builder
public class File {

    @Lob
    @Column(name = "file_data")
    private Blob fileData;

    @Column(name = "file_name")
    private String fileName; // file_name 추가

    public File(Blob fileData, String fileName) {
        this.fileData = fileData;
        this.fileName = fileName;
    }

}
