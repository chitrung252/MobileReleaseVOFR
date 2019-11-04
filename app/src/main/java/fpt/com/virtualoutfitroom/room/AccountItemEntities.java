package fpt.com.virtualoutfitroom.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Account")
public class AccountItemEntities {
    @PrimaryKey
    @ColumnInfo(name = "AccountId")
    @NonNull
    private String accountId;

    @ColumnInfo(name= "FirstName")
    private String firstName;

    @ColumnInfo(name= "LastName")
    private String lastName;

    @ColumnInfo(name= "Email")
    private String email;

    @ColumnInfo(name= "Address")
    private String address;

    @ColumnInfo(name= "PhoneNumber")
    private String phone_number;

    @ColumnInfo(name= "Username")
    private String username;

    @ColumnInfo(name= "Password")
    private String password;

    @ColumnInfo(name= "RoleId")
    private int roleId;

    @ColumnInfo(name= "RoleName")
    private String roleName;

    @ColumnInfo(name= "ImageUser")
    private String imageUser;

    @NonNull
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(@NonNull String accountId) {
        this.accountId = accountId;
    }

    public AccountItemEntities() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
