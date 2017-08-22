package com.spring.example.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.modelmapper.PropertyMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.spring.example.domain.Site;
import com.spring.example.utils.DTOUtils;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;
import net.minidev.json.JSONValue;

public class TestMain {
	private static int a = 0;
	public static void main1(String[] args) {
		List<Test> tests = new ArrayList<>(Arrays.asList(new Test( "D"),new Test(2,"B1","234"),new Test(3,"C1")));
		List<Test> testsDB = new ArrayList<>(Arrays.asList(new Test(1, "A"),new Test(2, "B","123"),new Test(3, "C")));
//		List<Test> testsDB = new ArrayList<>();
		
		List<Test> testsUpdate = new ArrayList<>();
		Map<Integer, Test> map = new HashMap<>();
		List<Test> testsAdd = new ArrayList<>();
		for (Test p1 : tests) {
			if(!(p1.getId() > 0)){
				testsAdd.add(p1);
			}else{
				for (Test p2 : testsDB) {
					if(p1.getId() == p2.getId()){
						if(!map.containsKey(p2.getId())){
							map.put(p2.getId(), p2);
						}
				        PropertyMap<Test,Test> propertyMap = new PropertyMap<Test, Test>() {
				            @Override
				            protected void configure() {
				                skip(destination.getData1());
				            }
				        };
						DTOUtils.mapTo(p1, p2, propertyMap);
						testsUpdate.add(p2);
					}
				}
			}
		}
		if(testsAdd.size() > 0){
			for (Test test : testsAdd) {
				System.out.println(test.toString());
			}
			System.out.println("========testsAdd=========");
		}
		if(testsUpdate.size() > 0){
			for (Test test : testsUpdate) {
				System.out.println(test.toString());
			}
			System.out.println("========testsUpdate=========");
		}
		if(testsDB.size() > 0){
			Predicate<Test> predicate = (s) -> map.containsKey(s.getId());
			testsDB.removeIf(predicate);
			for (Test test : testsDB) {
				System.out.println(test.toString());
			}
			System.out.println("========testsDB=========");
//			System.out.println(testsDB.size());
		}
		
	}
	
	public static void main2(String[] args) {
		Long id = null;
		if(Long.compare(id, 0) > 0 ){
			System.out.println(12);
		}
		Site site = new Site();
		if(site.getSiteId() > 0){

			System.out.println(23);
		}
	}
	public static void main3(String[] args) {
		TestParent testParent = new TestParent();
		testParent.setId(1);
		testParent.setData("A");
		TestChild testChild = new TestChild();
		create(testChild);
//		DTOUtils.mapTo(testParent, testChild);
		System.out.println(testChild.toString());
//		TestMain.a++;
		//working
//		Test test = null;
//		test.sayHello();
		
//		final byte b1=1,b2=2,b3;
//		b3 = (b1+b2);
//		
//		System.getProperties().list(System.out);
//		System.out.println(System.getProperty("user.name"));
//		System.out.println(System.getProperty("java.library.path"));
		
//		Random random = new Random(47);
//		int i = random.nextInt(100) + 1;
//		System.out.println(i);
//		int j = 0x1f;
//		System.out.println(Integer.toBinaryString(j));
//		System.out.println(Integer.toHexString(j));
//		System.out.println(Integer.toOctalString(j));
	}
	
	public static void main4(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        String json = "[{\"a\":\"111\",\"b\":\"222\",\"c\":\"333\"},{\"a\":\"1000\",\"b\":\"2000\",\"c\":\"000\"},{\"a\":\"999\",\"b\":\"300\",\"c\":\"700\"}]";
//		String json = "{\"a\":\"111\",\"b\":\"222\",\"c\":\"333\"}";
//        JSONArray array = (JSONArray) JSONValue.parse(json);
//        System.out.println(array.size());
        ObjectMapper mapper = new ObjectMapper(); 
//        for (int i = 0; i < array.size(); i++) {
//        	JSONObject object = (JSONObject) JSONValue.parse(array.get(i).toString());
//			System.out.println(object.get("a"));
//		}
		org.codehaus.jackson.JsonNode node = mapper.readTree(json);
		System.out.println(node.size());
		System.out.println(node.isArray());
		for (int i = 0; i < node.size(); i++) {
			org.codehaus.jackson.JsonNode childNode = node.get(i);
			System.out.println(childNode.get("a").asText());
			System.out.println(childNode.get("a").asInt());
		}
	}
	
	public static void main(String[] args) {
//		int total = 100090;
//		BigDecimal bigDecimal = new BigDecimal(total/100.00);
//		System.out.println(bigDecimal.doubleValue());
//		System.out.println(bigDecimal);
		try {
			main4(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void create(TestParent testParent){
		testParent.setId(1);
		testParent.setData("A");
	}

}
