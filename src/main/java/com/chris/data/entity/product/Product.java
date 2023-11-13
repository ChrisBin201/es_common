package com.chris.data.entity.product;

import com.chris.common.handler.CommonErrorCode;
import com.chris.common.handler.CommonException;
import com.chris.data.entity.Auditable;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Table(name = "product")
@ToString
//@SQLDelete(sql = "UPDATE product SET status = 'DELETED' WHERE id=?")
//@Where(clause = "status != 'DELETED'")
public class Product extends Auditable<String,Product> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String preview;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

//    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    @Fetch(value = FetchMode.SUBSELECT)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "variations",columnDefinition = "jsonb")
    private List<Variation> variations = new ArrayList<>();
//    private Set<Variation> variations = new HashSet<>();
    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductItem> productItems = new ArrayList<>();
//    private Set<ProductItem> productItems = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;
//    @JsonIgnore
//    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private List<Comment> commentList;
//    @ManyToOne
//    private Seller seller;
    @Column(name = "seller_id")
    private Long sellerId;
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "admin_id")
//    private User userAdmin;
    @Column(name = "admin_id")
    private Long adminId;
    @ManyToMany(mappedBy = "productList")
//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(name = "promotions",columnDefinition = "jsonb")
    private List<Promotion> promotionList = new ArrayList<>();

    public static enum ProductStatus {
        ACTIVE,BLOCK,CANCEL,PENDING,DELETED;

        public static ProductStatus fromString(String status){
            try{
                return ProductStatus.valueOf(status.toUpperCase());
            } catch (Exception e) {
                throw new CommonException(HttpStatus.BAD_REQUEST.value(), CommonErrorCode.INVALID_PRODUCT_STATUS.getCode(), CommonErrorCode.INVALID_PRODUCT_STATUS.getMessage());
            }
        }

        public static String getAll(){
            return "ALL";
        }

        public static String getOutOfStock(){
            return "OUT_OF_STOCK";
        }
    }
    public static List<String> SortOptions() {
        List<String> list = new ArrayList<>(Arrays.asList(
                "ID_ASC",
                "ID_DESC",
                "NAME_ASC",
                "NAME_DESC",
                "SALE_ASC",
                "SALE_DESC",
                "PRICE_ASC",
                "PRICE_DESC",
                "NEWEST"
        ));
        return list;
    }

    public void addVariation(Variation variation){
        variations.add(variation);
    }
    public void addVariations(List<Variation> variationList){
        variations.addAll(variationList);
    }
    public void removeVariation(Variation variation){
        variations.remove(variation);
    }
    public void addProductItem(ProductItem productItem){
        productItems.add(productItem);
//        productItem.setProduct(this);
    }
    public void removeProductItem(ProductItem productItem){
        productItems.remove(productItem);
//        productItem.setProduct(null);
    }
    public void addPromotion(Promotion promotion){
        promotionList.add(promotion);
    }
    public void removePromotion(Promotion promotion){
        promotionList.remove(promotion);
    }


}

