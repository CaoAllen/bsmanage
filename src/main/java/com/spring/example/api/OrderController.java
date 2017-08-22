package com.spring.example.api;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.example.Constants;
import com.spring.example.model.OrderItem;
import com.spring.example.service.OrderServiceImpl;

@RestController
@RequestMapping(value = Constants.URI_API + "/order")
public class OrderController {

	private OrderServiceImpl orderService;
	
	@Inject
	public OrderController(OrderServiceImpl orderService){
		this.orderService = orderService;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<OrderItem>> getSites(
			@RequestParam(value = "orderNo") String orderNo,
			@RequestParam(value = "status") int status,
			@RequestParam(value = "start") String start,
			@RequestParam(value = "end") String end){
		try {
//			List<OrderItem> sites = orderService.queryAllOrders();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
