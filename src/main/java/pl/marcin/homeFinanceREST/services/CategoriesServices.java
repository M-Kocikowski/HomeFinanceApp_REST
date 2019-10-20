package pl.marcin.homeFinanceREST.services;

import org.springframework.stereotype.Service;
import pl.marcin.homeFinanceREST.entity.Category;
import pl.marcin.homeFinanceREST.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoriesServices {

    private CategoryRepository repository;

    public CategoriesServices(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories(){
        return repository.findAll();
    }

    public Category getSingleCategory(long id){
        return repository.findById(id).orElse(null);
    }

    public Category saveCategoryToDatabase(Category category){
        return repository.save(category);
    }
}
