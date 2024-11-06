package org.example.autologin.domain;
import jakarta.persistence.*;

@Table(name = "autouser")//说明此实体类对应于数据库的user表
@Entity//说明此类是个实体类
public class User {
    // 注意属性名要与数据表中的字段名一致
    // 主键自增int(10)对应long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;
    public long getUid() {
        return uid;
    }
    public void setUid(long uid) {
        this.uid = uid;
    }
    @Column(name="uname")
    // 用户名属性varchar对应String
    private String uname;

    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    @Column(name="password")
    // 密码属性varchar对应String
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name="email")
    private String email;
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
