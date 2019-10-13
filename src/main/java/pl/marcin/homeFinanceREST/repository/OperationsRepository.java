package pl.marcin.homeFinanceREST.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.marcin.homeFinanceREST.entity.Operation;

import java.util.List;

public interface OperationsRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM Operation o WHERE o.orderDate = ?1 AND o.description = ?2 AND o.amount = ?3")
    Operation findOperationByOrderDateDescriptionAmount(String orderDate, String description, Double amount);

    @Query("SELECT o FROM Operation o ORDER BY o.orderDate DESC")
    List<Operation> findOperationsOrderByOrderDateDesc();
}
