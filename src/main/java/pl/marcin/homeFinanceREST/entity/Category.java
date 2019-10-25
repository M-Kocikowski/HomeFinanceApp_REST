package pl.marcin.homeFinanceREST.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonBackReference("category")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Operation> operations;

    @JsonBackReference("itemCategory")
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategorizedItem> items;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<CategorizedItem> getItems() {
        return items;
    }

    public void setItems(List<CategorizedItem> items) {
        this.items = items;
    }
}
