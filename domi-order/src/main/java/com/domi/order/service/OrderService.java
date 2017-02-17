package com.domi.order.service;

import com.domi.order.bean.DomiResult;
import com.domi.order.dao.OrderDao;
import com.domi.order.pojo.Order;
import com.domi.order.utils.ValidateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author 卡卡
 * Created by jing on 2017/2/17.
 * 将json反序列化为Order对象，对Order对象做校验，生成订单ID
 */
@Service
public class OrderService {

   private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private OrderDao orderDao;

    public DomiResult createOrder(String json) {
        Order order = null;

        try {
            order = MAPPER.readValue(json, Order.class);
            ValidateUtil.validate(order);
        } catch (Exception e) {
            return DomiResult.build(400, "请求参数有误！");
        }

        try {
            //生成订单ID，规则为：userid+当前时间戳
            String orderId = order.getUserId() + "" + System.currentTimeMillis();
            order.setOrderId(orderId);
            //设置订单的初始转给我为未付款
            order.setStatus(1);

            order.setCreateTime(new Date());
            order.setUpdateTime(order.getCreateTime());

            orderDao.createOrder(order);
            return DomiResult.ok(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DomiResult.build(400, "报错订单失败！");
    }
}
