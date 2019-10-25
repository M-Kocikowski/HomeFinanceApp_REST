package pl.marcin.homeFinanceREST.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.homeFinanceREST.entity.CategorizedItem;

public interface CategorizedItemRepository extends JpaRepository<CategorizedItem, Long> {
}
