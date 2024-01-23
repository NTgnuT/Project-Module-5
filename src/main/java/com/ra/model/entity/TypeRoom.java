package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TypeRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String type;
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;
    @OneToMany(mappedBy = "typeRoom", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Room> rooms;
}
