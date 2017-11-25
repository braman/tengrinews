package tengrinews.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;
    private String username;
    private String password;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<RoleEntity> roles;
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
}
