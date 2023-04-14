package it.johnson.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private UUID id;
    private String customerName;
    @Version
    private Integer version;
    private LocalDateTime createDate;
    private LocalDateTime lastDateModified;


}
