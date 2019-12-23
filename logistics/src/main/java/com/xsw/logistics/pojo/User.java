package com.xsw.logistics.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements Serializable {
	private static final long serialVersionUID = -140576845449532264L;
	

	private Long userId;

    private String username;

    private String realname;

    private String password;

    private String salt;

    private Integer status;

    /*
     * @JsonFormat  JackSon转换工具在返回json格式日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private Long roleId;
    
    
    private String rolename;
    
    
   

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

	public User(Long userId, String username, String realname, String password, String salt, Integer status,
			Date createDate, Long roleId) {
		super();
		this.userId = userId;
		this.username = username;
		this.realname = realname;
		this.password = password;
		this.salt = salt;
		this.status = status;
		this.createDate = createDate;
		this.roleId = roleId;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", realname=" + realname + ", password=" + password
				+ ", salt=" + salt + ", status=" + status + ", createDate=" + createDate + ", roleId=" + roleId
				+ ", rolename=" + rolename + "]";
	}
  
	
}