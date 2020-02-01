package model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private Long parentId;

    @OneToMany(targetEntity = Toy.class)
    private List<Toy> toys;

    public Category() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Toy> getToys() {
        return toys;
    }

    public void setToys(List<Toy> toys) {
        this.toys = toys;
    }
}
