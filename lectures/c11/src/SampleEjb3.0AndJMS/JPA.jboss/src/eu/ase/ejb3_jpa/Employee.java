package eu.ase.ejb3_jpa;

import java.io.Serializable;
import javax.persistence.*;
 
@Entity
@Table( name = "EMPLOYEES" )
public class Employee implements Serializable {
	//@Id @GeneratedValue private int EmpNo;
	@Id private int empNo;
	@Basic private String empName;
	@Basic private float empSalary;
	 
	public Employee() {}
	 
	public Employee(int empNo, String empName, float empSalary) {
		this.empNo = empNo;   
		this.empName = empName;
	   	this.empSalary = empSalary;
	}

	// getter methods
	public Integer getEmpNo(){ return this.empNo;}
	public String getEmpName(){ return this.empName; }
	public Float getEmpSalary(){ return this.empSalary; }

	// setter remote methods
	public void setEmpNo(Integer empNo){ this.empNo = empNo; }
	public void setEmpName(String empName){ this.empName = empName; }
	public void setEmpSalary(Float empSalary){ this.empSalary = empSalary; }
	 
	public String toString() {
	      return "Employee row in DB: empNo = " + this.getEmpNo() + ", empName = " + this.getEmpName() + ", empSalary = " + this.getEmpSalary();
	}
}
