package com.domi.order.mapper;

import com.domi.order.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OrderMapper extends IMapper<Order>{
	
	public void paymentOrderScan(@Param("date") Date date);

}
