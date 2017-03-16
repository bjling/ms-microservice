package com.bao.mapper;

import com.bao.model.TOrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TOrderItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item_0
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    int deleteByPrimaryKey(Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item_0
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    int insert(TOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item_0
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    int insertSelective(TOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item_0
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    TOrderItem selectByPrimaryKey(Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item_0
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    int updateByPrimaryKeySelective(TOrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item_0
     *
     * @mbg.generated Wed Mar 15 22:35:41 CST 2017
     */
    int updateByPrimaryKey(TOrderItem record);
}