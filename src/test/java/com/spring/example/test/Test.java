package com.spring.example.test;

public class Test{
    private int id;
    private String data;
    private String data1;
    
    public Test(int id,String data){
    	this.id = id;
    	this.data = data;
    }
    
    public Test(String data){
    	this.data = data;
    }
    
	public Test(int id, String data, String data1) {
		super();
		this.id = id;
		this.data = data;
		this.data1 = data1;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public String getData1() {
		return data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", data=" + data + ", data1=" + data1 + "]";
	}
    
}
