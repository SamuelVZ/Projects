package com.revature.dto;

import com.revature.model.User;

import java.util.Objects;

public class ResponseReimbursementDto {

    private int id;
    private int amount;
    private String dateSubmitted;
    private String dateResolved;
    private String description;

    private String employeeUsername;
    private String manager_username;

    private String statusName;
    private String typeName;

    public ResponseReimbursementDto() {
    }

    public ResponseReimbursementDto(int id, int amount, String dateSubmitted, String dateResolved, String description, String employeeUsername, String manager_username, String statusName, String typeName) {
        this.id = id;
        this.amount = amount;
        this.dateSubmitted = dateSubmitted;
        this.dateResolved = dateResolved;
        this.description = description;
        this.employeeUsername = employeeUsername;
        this.manager_username = manager_username;
        this.statusName = statusName;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "ResponseReimbursementDto{" +
                "id=" + id +
                ", amount=" + amount +
                ", dateSubmitted='" + dateSubmitted + '\'' +
                ", dateResolved='" + dateResolved + '\'' +
                ", description='" + description + '\'' +
                ", employeeUsername='" + employeeUsername + '\'' +
                ", manager_username='" + manager_username + '\'' +
                ", statusName='" + statusName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseReimbursementDto that = (ResponseReimbursementDto) o;
        return id == that.id && amount == that.amount && Objects.equals(dateSubmitted, that.dateSubmitted) && Objects.equals(dateResolved, that.dateResolved) && Objects.equals(description, that.description) && Objects.equals(employeeUsername, that.employeeUsername) && Objects.equals(manager_username, that.manager_username) && Objects.equals(statusName, that.statusName) && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, dateSubmitted, dateResolved, description, employeeUsername, manager_username, statusName, typeName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(String dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public String getManager_username() {
        return manager_username;
    }

    public void setManager_username(String manager_username) {
        this.manager_username = manager_username;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
