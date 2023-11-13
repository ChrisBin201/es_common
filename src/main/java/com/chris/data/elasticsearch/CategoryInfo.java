package com.chris.data.elasticsearch;

import com.chris.data.entity.product.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "category_info")
public class CategoryInfo {
    @Id
    private long id;
    private String name;
    @JsonProperty("parent_id")
    @Field(name = "parent_id")
    private long parentId;
    @JsonProperty("child_categories")
    @Field(name = "child_categories")
    private List<CategoryInfo> childCategories = new ArrayList<>();

    public static CategoryInfo from(Category category) {
        return CategoryInfo.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParentId())
                .childCategories(new ArrayList<>())
                .build();
    }
}
