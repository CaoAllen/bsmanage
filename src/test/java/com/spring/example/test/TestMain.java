package com.spring.example.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.modelmapper.PropertyMap;
import org.springframework.util.NumberUtils;

import com.spring.example.domain.Site;
import com.spring.example.utils.DTOUtils;

public class TestMain {
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
	
	public static void main(String[] args) {
		Long id = null;
		if(Long.compare(id, 0) > 0 ){
			System.out.println(12);
		}
		Site site = new Site();
		if(site.getSiteId() > 0){

			System.out.println(23);
		}
	}
}
