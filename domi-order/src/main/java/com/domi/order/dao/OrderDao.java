package com.domi.order.dao;

import com.domi.order.mapper.OrderMapper;
import com.domi.order.pojo.Order;
import com.domi.order.pojo.PageResult;
import com.domi.order.pojo.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author 卡卡
 * Created by jing on 2017/2/17.
 *
 * OrderDAO是一个接口的实现类，后期如果对订单数据的存储做改造时，保存订单就会有多套实现
 */

public class OrderDao implements IOrder {

    @Autowired
    private OrderMapper orderMapper;

    public void createOrder(Order order) {
        this.orderMapper.save(order);
    }

    @Override
    public Order queryOrderById(String orderId) {
        return null;
    }

    @Override
    public PageResult<Order> queryOrderByUserNameAndPage(String buyerNick, Integer page, Integer count) {
        return null;
    }

    @Override
    public ResultMsg changeOrderStatus(Order order) {
        return null;
    }
}
