package pl.marcin.homeFinanceREST.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.homeFinanceREST.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
