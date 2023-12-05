package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Audited
@Entity
public class FileInfo {
    @Id
    private UUID id;
    private String fileName;
    private String fileType;
    private long size;
    private Date created;
    @Digits(integer = 3, fraction = 13)
    private Double latitude;
    @Digits(integer = 3, fraction = 13)
    private Double longitude;
    private Date finishTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInfo fileInfo = (FileInfo) o;
        return id.equals(fileInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
