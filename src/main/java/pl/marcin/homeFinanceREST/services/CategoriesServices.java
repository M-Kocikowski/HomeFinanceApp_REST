package pl.marcin.homeFinanceREST.services;

import org.springframework.stereotype.Service;
import pl.marcin.homeFinanceREST.entity.CategorizedItem;
import pl.marcin.homeFinanceREST.entity.Category;
import pl.marcin.homeFinanceREST.repository.CategorizedItemRepository;
import pl.marcin.homeFinanceREST.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoriesServices {

    private CategoryRepository categoryRepository;

    private CategorizedItemRepository itemRepository;

    public CategoriesServices(CategoryRepository categoryRepository, CategorizedItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getSingleCategory(long id){
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategoryToDatabase(Category category){
        return categoryRepository.save(category);
    }

    public CategorizedItem saveItemCategorization(CategorizedItem item){
        return itemRepository.save(item);
    }
}
