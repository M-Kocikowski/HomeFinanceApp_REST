package pl.marcin.homeFinanceREST.services;

import org.springframework.stereotype.Service;
import pl.marcin.homeFinanceREST.entity.Operation;
import pl.marcin.homeFinanceREST.repository.OperationsRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class DbOperations {

    private OperationsRepository repository;

    public DbOperations(OperationsRepository repository) {
        this.repository = repository;
    }

    public void saveOperationToDatabase(List<Operation> operations){

        operations.stream()
                .filter(o -> repository.findOperationByOrderDateDescriptionAmount(o.getOrderDate(), o.getDescription(), o.getAmount()) == null)
                .forEach(o -> repository.save(o));

    }

    public List<Operation> getOperationsByDate(String fromDate, String toDate){

        return repository.findOperationsByOrderDateOrderByOrderDateDesc(
                LocalDate.parse(fromDate), LocalDate.parse(toDate)
        );
    }
}
