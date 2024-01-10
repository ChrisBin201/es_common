package com.chris.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Slf4j
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_by", "created_date", "last_modified_by", "last_modified_date"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Auditable<T,Model> implements Serializable {

    @CreatedBy
    @Column(name = "created_by")
    @JsonProperty("created_by")
    protected T createdBy;

//    @CreatedDate
    @JsonProperty("created_date")
    @Column(name = "created_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Transient // ignore from elasticsearch
    protected LocalDateTime createdDate;

    @LastModifiedBy
    @JsonProperty("last_modified_by")
    @Column(name = "last_modified_by")
    protected T lastModifiedBy;

//    @LastModifiedDate
    @JsonProperty("last_modified_date")
    @Column(name = "last_modified_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Transient // ignore from elasticsearch
    protected LocalDateTime lastModifiedDate;

//    @Column(name = "status", length = 20)
//    protected String status;

    public boolean isSameObject(Model obj){
        if(this == null || obj == null){
            return false;
        }

        Field[] thisFields = getAllFields(this.getClass());
        Field[] objFields = getAllFields(obj.getClass());

        //check if objects are the same by fields
        for(Field thisField : thisFields){
            for(Field objField : objFields){
                if(thisField.getName().equals(objField.getName())){
                    try {
                        thisField.setAccessible(true);
                        objField.setAccessible(true);
                        if(thisField.get(this) != null && objField.get(obj) != null){
                            if(!thisField.get(this).equals(objField.get(obj))){
                                return false;
                            }
                        }
                        else if(thisField.get(this) == null && objField.get(obj) != null){
                            return false;
                        }
                        else if(thisField.get(this) != null && objField.get(obj) == null){
                            return false;
                        }
                    } catch (IllegalAccessException e) {
                        log.error("Error while comparing objects", e);
                    }
                }
            }
        }

        return true;
    }

    private static Field[] getAllFields(Class<?> modelClass) {
        Field[] fields = Arrays.stream(modelClass.getDeclaredFields()).filter(field -> !Arrays.asList(AUDITABLE_FIELDS).contains(field.getName())).toArray(Field[]::new);
        Class<?> superClass = modelClass.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            Field[] superFields = getAllFields(superClass);
            Field[] allFields = new Field[fields.length + superFields.length];
            System.arraycopy(fields, 0, allFields, 0, fields.length);
            System.arraycopy(superFields, 0, allFields, fields.length, superFields.length);
            fields = allFields;
        }
        return fields;
    }

    private static String[] AUDITABLE_FIELDS = {"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"};
}
