package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import entities.Employee;

public class Program {
	
	public static void main(String[] args) {
		
		List<Employee> employees = new ArrayList<>();
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		Locale locale = Locale.forLanguageTag("pt-BR");
		NumberFormat money = NumberFormat.getCurrencyInstance(locale);
		
		System.out.println("Instanciando empregados...");
		initialSetup(employees); 
		
		System.out.println("");
		System.out.println("Removendo João...");
		int idxJoao = employees.indexOf(new Employee("João", LocalDate.of(0, 1, 1), new BigDecimal(0), ""));
		employees.remove(idxJoao);
		
		System.out.println("");
		System.out.println("Imprimindo os empregados:");
		System.out.println("");
		
		employees.forEach(employee -> System.out.println(employee.getName() + " | " + employee.getBirthdate().format(dateTimeFormatter).replaceAll("-", "/") + " | " + money.format(employee.getSalary()) + " | " + employee.getRole()));
		
		System.out.println("");
		System.out.println("Aumentando salários em 10%...");
		
		employees.forEach(employee -> employee.setSalary(employee.getSalary().multiply(new BigDecimal(1.1))));
		
		System.out.println("");
		System.out.println("Agrupando funcionários em um map...");
		Map<String, List<Employee>> employeesMap = new HashMap<String, List<Employee>>();
		
		populateMap(employees, employeesMap);
		
		System.out.println("");
		System.out.println("Imprimindo funcionários por função:");
		System.out.println("");
		
		for(String key : employeesMap.keySet()) {
			List<Employee> employeesValue = employeesMap.get(key);
			System.out.print(key + ": ");
			employeesValue.forEach(employee -> System.out.print(employee.getName() + " "));
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("Imprimindo funcionários que fazem aniversário no mês 10 e 12:");
		System.out.println("");
		
		employees.forEach(employee -> {
			Month month = employee.getBirthdate().getMonth();
			
			if(month == Month.OCTOBER || month == Month.DECEMBER) {
				System.out.println(employee.getName() + " | " + employee.getBirthdate().format(dateTimeFormatter).replaceAll("-", "/"));
			}
		});
		
		System.out.println("");
		System.out.println("Imprimindo funcionário com a maior idade:");
		System.out.println("");
		
		Employee oldestEmployee = getOldestEmployee(employees);
		int age = LocalDate.now().getYear() - oldestEmployee.getBirthdate().getYear();
		System.out.println("Oldest employee: " + oldestEmployee.getName() + " | " + age + " years old.");
		
		System.out.println("");
		System.out.println("Imprimindo funcionários em ordem alfabética:");
		System.out.println("");
		
		Comparator<Employee> comparatorEmployee = (e1, e2) -> e1.getName().toUpperCase().compareTo(e2.getName().toUpperCase());
		employees.sort(comparatorEmployee);
		
		employees.forEach(employee -> System.out.println(employee.getName() + " | " + employee.getBirthdate().format(dateTimeFormatter).replaceAll("-", "/") + " | " + money.format(employee.getSalary()) + " | " + employee.getRole()));
	
		System.out.println("");
		System.out.println("Imprimindo o total dos salários:");
		
		BigDecimal totalSalaries = new BigDecimal(0.0);
		for(int i = 0; i < employees.size(); i++) {
			totalSalaries = totalSalaries.add(employees.get(i).getSalary());
		}

		System.out.println("The total sum of salaries is: " + money.format(totalSalaries));
		
		System.out.println("");
		System.out.println("Imprimindo quantos salários mínimos ganham cada funcionário:");
		System.out.println("");
		
		for(int i = 0; i < employees.size(); i++) {
			Double minimunWageQuantity = employees.get(i).getSalary().divide(new BigDecimal(1212.00), 1, RoundingMode.HALF_UP).doubleValue();
			System.out.println(employees.get(i).getName() + " earns " + minimunWageQuantity + " minimun wages.");
		}
	}

	private static void initialSetup(List<Employee> employees) {
		employees.add(new Employee("Maria", LocalDate.of(2000, 10, 18), new BigDecimal(2009.44), "Operador"));
		employees.add(new Employee("João", LocalDate.of(1990, 5, 12), new BigDecimal(2284.38), "Operador"));
		employees.add(new Employee("Caio", LocalDate.of(1961, 5, 2), new BigDecimal(9836.14), "Coordenador"));
		employees.add(new Employee("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal(19119.88), "Diretor"));
		employees.add(new Employee("Alice", LocalDate.of(1995, 1, 5), new BigDecimal(2234.68), "Recepcionista"));
		employees.add(new Employee("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal(1582.72), "Operador"));
		employees.add(new Employee("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal(4071.84), "Contador"));
		employees.add(new Employee("Laura", LocalDate.of(1994, 7, 8), new BigDecimal(3017.45), "Gerente"));
		employees.add(new Employee("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal(1606.85), "Eletricista"));
		employees.add(new Employee("Helena", LocalDate.of(1996, 9, 2), new BigDecimal(2799.93), "Gerente"));
	}

	private static void populateMap(List<Employee> employees, Map<String, List<Employee>> employeesMap) {
		for(int i = 0; i < employees.size(); i++) {
			Employee employee = employees.get(i);
			String role = employee.getRole();
			
			if(employeesMap.containsKey(role)) {
				List<Employee> employeesValue = employeesMap.get(role);
				employeesValue.add(employee);
				employeesMap.put(role, employeesValue);
			}else {
				List<Employee> employeesValue = new ArrayList<>();
				employeesValue.add(employee);
				employeesMap.put(role, employeesValue);
			}
			
		}
	}
	
	private static Employee getOldestEmployee(List<Employee> employees) {
		Employee oldestEmployee = new Employee("", LocalDate.of(LocalDate.now().getYear(), 1, 1), new BigDecimal(0), "");
		
		for(int i = 0; i < employees.size(); i++) {
			Employee employee = employees.get(i);
			Integer yearBirthdate = employee.getBirthdate().getYear();
			Integer oldestYearBirthdate = oldestEmployee.getBirthdate().getYear();
			
			if(yearBirthdate < oldestYearBirthdate) {
				oldestEmployee = employee;
				continue;
			}
			
			if(yearBirthdate == oldestYearBirthdate) {
				Month monthBirthdate = employee.getBirthdate().getMonth();
				Month oldestMonthBirthdate = oldestEmployee.getBirthdate().getMonth();
				
				if(monthBirthdate.getValue() < oldestMonthBirthdate.getValue()) {
					oldestEmployee = employee;
					continue;
				}
				
				if(monthBirthdate.getValue() == oldestMonthBirthdate.getValue()) {
					int dayBirthdate = employee.getBirthdate().getDayOfMonth();
					int oldestDayBirthdate = oldestEmployee.getBirthdate().getDayOfMonth();
					
					if(dayBirthdate < oldestDayBirthdate) {
						oldestEmployee = employee;
					}
				}
			}
		}
		
		return oldestEmployee;
	}
}
	