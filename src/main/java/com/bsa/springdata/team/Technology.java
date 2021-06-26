package com.bsa.springdata.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "technologies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Technology {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String link;

    public static Technology fromDto(String name, String description, String link) {
        return Technology.builder()
                .name(name)
                .description(description)
                .link(link)
                .build();
    }
}