package com.heftyb.dms.users;

import com.heftyb.dms.crm.Employee;

/**
 * User data transfer object, used for mapping data from the request.
 */
public class UserDTO {
    private String username;
    private String password;
    private String matchingPassword;
    private String email;
    private Employee employee;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String matchingPassword, String email, Employee employee) {
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
        this.employee = employee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
