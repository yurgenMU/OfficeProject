//package model;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "roles")
//public class Role extends AbstractEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//
//    private Set<User> users;
//
////    public Role() {
////    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
//
//}
