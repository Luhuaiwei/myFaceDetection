package hw.model;  
public class Users {        
    private Integer id ;            //编号  
    private String username;        //用户名  
    private String password;        //密码  
    private String headphoto;       //头像  
    public Integer getId() {  
        return id;  
    }  
    public void setId(Integer id) {  
        this.id = id;  
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
    public String getHeadphoto() {  
        return headphoto;  
    }  
    public void setHeadphoto(String headphoto) {  
        this.headphoto = headphoto;  
    }   
}  