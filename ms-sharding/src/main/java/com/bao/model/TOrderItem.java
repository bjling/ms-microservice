package com.bao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TOrderItem {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_0.item_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    private Integer itemId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_0.order_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_0.user_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_0.item_name
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    private String itemName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_0.item_id
     *
     * @return the value of t_order_item_0.item_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_0.item_id
     *
     * @param itemId the value for t_order_item_0.item_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_0.order_id
     *
     * @return the value of t_order_item_0.order_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_0.order_id
     *
     * @param orderId the value for t_order_item_0.order_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_0.user_id
     *
     * @return the value of t_order_item_0.user_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_0.user_id
     *
     * @param userId the value for t_order_item_0.user_id
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_0.item_name
     *
     * @return the value of t_order_item_0.item_name
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_0.item_name
     *
     * @param itemName the value for t_order_item_0.item_name
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }
}