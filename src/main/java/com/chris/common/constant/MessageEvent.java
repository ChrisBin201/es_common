package com.chris.common.constant;

import org.springframework.stereotype.Component;

@Component
public class MessageEvent {

    public static final String PRODUCT_TOPIC = "product_";
    public static final String UPLOAD_PRODUCT = PRODUCT_TOPIC + "upload_public";
    public static final String UPLOAD_PRODUCT_ROLLBACK = PRODUCT_TOPIC + "upload_rollback";
    public static final String REVIEW_PRODUCT = PRODUCT_TOPIC + "review_public";
    public static final String REVIEW_PRODUCT_ROLLBACK = PRODUCT_TOPIC + "review_rollback";

    public static final String DELETE_PRODUCT = PRODUCT_TOPIC + "delete_public";
    public static final String DELETE_PRODUCT_ROLLBACK = PRODUCT_TOPIC + "delete_rollback";

    public static final String UPDATE_PRODUCT_INVENTORY = "update_product_item_inventory_public";
    public static final String UPDATE_PRODUCT_ROLLBACK = "update_product_item_inventory_rollback";

    public static final String UPDATE_ROOT_CATEGORY = "update_root_category_public";
    public static final String UPDATE_ROOT_CATEGORY_ROLLBACK = "update_root_category_rollback";

    public static final String DELETE_CATEGORY = "delete_category_public";
    public static final String DELETE_CATEGORY_ROLLBACK = "delete_category_rollback";

//    public static final String DELETE_CHILD_CATEGORY = "delete_child_category_public";
//    public static final String DELETE_CHILD_CATEGORY_ROLLBACK = "delete_child_category_rollback";

    public static final String ORDER_TOPIC = "order_";
    public static final String CREATE_ORDER = ORDER_TOPIC + "create_public";
    public static final String CREATE_ORDER_ROLLBACK = ORDER_TOPIC + "create_rollback";

    public static final String UPDATE_ORDER_STATUS = ORDER_TOPIC + "update_status_public";
    public static final String UPDATE_ORDER_STATUS_ROLLBACK = ORDER_TOPIC + "update_status_rollback";

    public static final String UPDATE_SALES = ORDER_TOPIC + "update_sales_public";
    public static final String UPDATE_SALES_ROLLBACK = ORDER_TOPIC + "update_sales_rollback";

    public static final String CREATE_RATING = ORDER_TOPIC + "create_rating_public";
    public static final String CREATE_RATING_ROLLBACK = ORDER_TOPIC + "create_rating_rollback";

    public static final String UPDATE_RATING = ORDER_TOPIC + "update_rating_public";
    public static final String UPDATE_RATING_ROLLBACK = ORDER_TOPIC + "update_rating_rollback";


    public static final String PAYMENT_TOPIC = "payment_";

    public static final String COMPLETE_CHECKOUT = PAYMENT_TOPIC + "complete_checkout_public";
    public static final String COMPLETE_CHECKOUT_ROLLBACK = PAYMENT_TOPIC + "complete_checkout_rollback";

    public static final String COMPLETE_PAYOUT = PAYMENT_TOPIC + "complete_payout_public";
    public static final String COMPLETE_PAYOUT_ROLLBACK = PAYMENT_TOPIC + "complete_payout_rollback";

    public static final String COMPLETE_REFUND = PAYMENT_TOPIC + "complete_refund_public";
    public static final String COMPLETE_REFUND_ROLLBACK = PAYMENT_TOPIC + "complete_refund_rollback";

}
