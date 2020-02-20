package com.example.upload.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
public class Upload implements Serializable {

    private static final long serialVersionUID = -5528679710818606000L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String fileUrl;
}
