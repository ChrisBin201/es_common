package com.chris.data.entity.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import com.chris.data.entity.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Data
@Entity
@Table(name = "category")
@SQLDelete(sql = """
    WITH recursive Vals AS (
      SELECT *
      FROM category \s
      WHERE id  = ?
      UNION ALL
      SELECT c.*
      FROM category c  INNER JOIN
      Vals v ON c.parent_id  = v.id
     )
    UPDATE category SET deleted=true WHERE id IN (SELECT id FROM Vals)
""")
@Where(clause = "deleted=false")
public class Category extends Auditable<String,Category> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "parent_id")
    @JsonProperty("parent_id")
    @Field(name = "parent_id")
    private Long parentId;
    @JsonIgnore
    private boolean deleted = false;
}
