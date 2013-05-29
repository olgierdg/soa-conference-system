package test;

public class Test implements java.io.Serializable{
	private String a;
	
	public Test(){
		this.a = "aaa";
	}
	
	public String getA(){
		return a;
	}
	
	public void setA(String a){
		this.a = a;
	}

	 
	 @Override
	 public String toString() {
		 return "Product [a=" + a + "]";
	 }
}
