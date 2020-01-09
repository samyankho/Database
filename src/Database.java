import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* READ THE PDF INSTRUCTION BEFORE GETTING STARTED!
 * 
 * Resources:
 * 	- Tutorial Series on Java Collections (ArrayList and HashMap):
 * 		https://www.eecs.yorku.ca/~jackie/teaching/tutorials/index.html#java_collections
 *	- Recording of lecture on implementing compareTo and using Arrays.sort(...):
 *		https://youtu.be/mDpDRLEy-7Y
 */

public class Database {
	/*
	 * Each entry in a 'departments' map contains
	 * a unique department id and its associated information object.
	 */
	HashMap<Integer, DepartmentInfo> departments;

	/*
	 * Each entry in a 'employees' map contains
	 * a unique employee id and its associated information object.
	 */
	HashMap<String, EmployeeInfo> employees;

	/**
	 * Initialize an empty database.
	 */
	public Database() {
		departments = new HashMap<>();
		employees = new HashMap<>();
	}

	/**
	 * Add a new employee entry.
	 * @param id id of the new employee
	 * @param info information object of the new employee
	 * @throws IdAlreadyExistsExceptoin if 'id' is an existing employee id
	 */
	public void addEmployee(String id, EmployeeInfo info) throws IdAlreadyExistsExceptoin {
		/* Your Task */
			if(employees.containsKey(id)) {
				throw new IdAlreadyExistsExceptoin("IdAlreadyExists");
			}
			else {
				employees.put(id, info);
			}
		
	}

	/**
	 * Remove an existing employee entry.
	 * @param id id of some employee
	 * @throws IdNotFoundException if 'id' is not an existing employee id
	 */
	public void removeEmployee(String id) throws IdNotFoundException {
		/* Your Task */
		if(employees.containsKey(id)) {
			employees.remove(id);
		}
		else {
			throw new IdNotFoundException("idNotFound");
		}
	}

	/**
	 * Add a new department entry.
	 * @param id id of the new department
	 * @param info information object of the new department
	 * @throws IdAlreadyExistsExceptoin if 'id' is an existing department id
	 */
	public void addDepartment(Integer id, DepartmentInfo info) throws IdAlreadyExistsExceptoin {
		/* Your Task */
		if(departments.containsKey(id)) {
			throw new IdAlreadyExistsExceptoin("IdAlreadyExists");
		}
		else {
			departments.put(id, info);
		}
	}

	/**
	 * Remove an existing department entry.
	 * @param id id of some employee
	 * @throws IdNotFoundException if 'id' is not an existing employee id
	 */
	public void removeDepartment(Integer id) throws IdNotFoundException {
		/* Your Task */
		if(departments.containsKey(id)) {
			departments.remove(id);
		}
		else {
			throw new IdNotFoundException("idNotFound");
		}
	}

	/**
	 * Change the department of employee with id 'eid' to a new department with id 'did'.
	 * 
	 * You can assume that 'did' denotes a department different from
	 * the current department of the employee denoted by 'eid'.
	 * @param eid id of some employee
	 * @param did id of some department
	 * @throws IdNotFoundException if either eid is a non-existing employee id or did is a non-existing department id.
	 */
	public void changeDepartment(String eid, Integer did) throws IdNotFoundException {
		/* Your Task */
		if(!employees.containsKey(eid) || !departments.containsKey(did)) {
			throw new IdNotFoundException("eid or did NotFound");
		}
		else {
			employees.get(eid).setDepartmentId(did);
		}
	}

	/**
	 * Retrieve the name of employee with id 'id'.
	 * @param id id of some employee
	 * @return name of the employee with id 'id'
	 * @throws IdNotFoundException if 'id' is not an existing employee id
	 */
	public String getEmployeeName(String id) throws IdNotFoundException {
		/* Your Task */
		if(!employees.containsKey(id)) {
			throw new IdNotFoundException(id);
		}
		else {				
				return employees.get(id).getName();
			
		}
	
	}

	/**
	 * Retrieve the names of all employees of the department with id 'id'.
	 * If 'id' a non-existing department id, then return an empty list.
	 * @param id id of some department
	 * @return List of names of employees whose home department has id 'id'
	 */
	public ArrayList<String> getEmployeeNames(Integer id) {
		/* Your Task */
		ArrayList<String> list = new ArrayList<>();
		for (EmployeeInfo employeeInfo : employees.values()) {
			if (employeeInfo.getDepartmentId() == id) {
				list.add(employeeInfo.getName());
			}
		}

		return list;
	}

	/**
	 * Retrieve an employee's department's information object.  
	 * @param id id of some existing employee
	 * @return The information object of the employee's home department
	 * @throws IdNotFoundException if 'id' is not an existing employee id
	 */
	public DepartmentInfo getDepartmentInfo(String id) throws IdNotFoundException {
		/* Your Task */
		DepartmentInfo departmentInfo;
		if (employees.containsKey(id)) {
			departmentInfo = new DepartmentInfo(departments.get(employees.get(id).getDepartmentId()).getName(),
					departments.get(employees.get(id).getDepartmentId()).getLocation());

		} else {
			throw new IdNotFoundException("id not found");
		}
		return departmentInfo;
	}
	
	/**
	 * Retrieve a list, sorted in increasing order, 
	 * the information objects of all stored employees.
	 * 
	 * Hints: 
	 * 1. Override the 'comareTo' method in EmployeeInfo class. 
	 * 2. Look up the Arrays.sort method in Java API. 
	 * @return A sorted list of information objects of all employees.
	 */
	public EmployeeInfo[] getSortedEmployeeInfo() {
		/* Your Task */
		EmployeeInfo[] employeeInfos = {};
		for (EmployeeInfo employeeInfo : employees.values()) {
			employeeInfos = Arrays.copyOf(employeeInfos, employeeInfos.length + 1);
			employeeInfos[employeeInfos.length - 1] = employeeInfo;
		}
		Arrays.sort(employeeInfos);

		return employeeInfos;
	}

	/**
	 * Retrieve the average salary of all employees in department with id 'id'.
	 * @param id id of some department 
	 * @return average salary of all employees in department with id 'id'
	 * @throws IdNotFoundException if id is not an existing department id
	 */
	public double getAverageSalary(Integer id) throws IdNotFoundException {
		/* Your Task */
		double average = 0;
		double total = 0;
		double numbers = 0;
		if (departments.containsKey(id)) {
			for (int i = 0; i < getSortedEmployeeInfo().length; i++) {
				if (getSortedEmployeeInfo()[i].getDepartmentId() == id) {
					total += getSortedEmployeeInfo()[i].getSalary();
					numbers++;
				}

			}
		} else {
			throw new IdNotFoundException("id not found");
		}
		average = total / numbers;

		return average;
	}
	
	/**
	 * Retrieve the information object of the department with the highest average salary among its employees.
	 * @return the information object of the department with the highest average salary among its employees
	 * 
	 * Hint: Use 'getAverageSalary(Integer id)' as a helper method.
	 */
	public DepartmentInfo getDepartmentOfHighestAverageSalary() {	
		/* Your Task */
		DepartmentInfo departmentInfo;
		Set<Integer> set = departments.keySet();
		List<Integer> list0 = new ArrayList<Integer>(set);
		List<Double> list = new ArrayList<>();
		Integer count = 0;
		for (int i = 0; i < set.size(); i++) {

			try {
				list.add(getAverageSalary(list0.get(i)));
				double max = Collections.max(list);
				if (max == getAverageSalary(list0.get(i))) {
					count = list0.get(i);
				}
			} catch (IdNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		departmentInfo = new DepartmentInfo(departments.get(count).getName(), departments.get(count).getLocation());
		return departmentInfo;
	}

	
}
