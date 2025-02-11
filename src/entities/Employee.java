package entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee extends Person {
	BigDecimal salary;
	String role;
	
	public Employee(String name, LocalDate birthdate, BigDecimal salary, String role) {
		super(name, birthdate);
		this.salary = salary;
		this.role = role;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String position) {
		this.role = position;
	}
}
