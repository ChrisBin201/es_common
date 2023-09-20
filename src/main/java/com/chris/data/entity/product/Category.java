package com.chris.data.entity.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import com.chris.data.entity.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Entity
@Table(name = "category")
//@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
//@Where(clause = "deleted=false")
public class Category extends Auditable<String,Category> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(name = "parent_id")
    @JsonProperty("parent_id")
    @Field(name = "parent_id")
    private Long parentId;
    @JsonIgnore
    private boolean deleted = false;
}
