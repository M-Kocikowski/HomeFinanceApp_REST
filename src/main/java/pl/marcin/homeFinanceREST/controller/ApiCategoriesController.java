package pl.marcin.homeFinanceREST.controller;

import org.springframework.web.bind.annotation.*;
import pl.marcin.homeFinanceREST.entity.CategorizedItem;
import pl.marcin.homeFinanceREST.entity.Category;
import pl.marcin.homeFinanceREST.services.CategoriesServices;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiCategoriesController {

    private CategoriesServices categoriesServices;

    public ApiCategoriesController(CategoriesServices categoriesServices) {
        this.categoriesServices = categoriesServices;
    }

    @GetMapping("")
    public List<Category> getCategoryList(){
        return categoriesServices.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getSingleCategory(@PathVariable long id){
        return categoriesServices.getSingleCategory(id);
    }

    @PostMapping("/post")
    public Category saveCategory(@RequestBody Category category){
        return categoriesServices.saveCategoryToDatabase(category);
    }

    @PostMapping("item/post")
    public CategorizedItem saveItem(@RequestBody CategorizedItem item){
        return categoriesServices.saveItemCategorization(item);
    }

    @PutMapping("/put/{id}")
    public Category updateCategory(@RequestBody Category category){
        return categoriesServices.saveCategoryToDatabase(category);
    }
}
