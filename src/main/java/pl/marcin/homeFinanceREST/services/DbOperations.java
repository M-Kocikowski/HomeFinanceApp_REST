package pl.marcin.homeFinanceREST.services;

import org.springframework.stereotype.Service;
import pl.marcin.homeFinanceREST.entity.Operation;
import pl.marcin.homeFinanceREST.repository.OperationsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DbOperations {

    private OperationsRepository repository;

    public DbOperations(OperationsRepository repository) {
        this.repository = repository;
    }

    public void saveOperationsToDatabase(List<Operation> operations){

        List<Operation> filteredOperations = operations.stream()
                .filter(this::checkIfOperationNotDuplicated)
                .collect(Collectors.toList());

        repository.saveAll(filteredOperations);

    }

    public void saveSingleOperationToDatabase(Operation operation){
        if (checkIfOperationNotDuplicated(operation)){
            repository.save(operation);
        }
    }

    public List<Operation> getOperationsByDate(String fromDate, String toDate){

        return repository.findOperationsByOrderDateOrderByOrderDateDesc(
                LocalDate.parse(fromDate), LocalDate.parse(toDate)
        );
    }

    public Operation getSingleOperationById(long id) {
        return repository.findById(id).orElse(null);
    }


    private boolean checkIfOperationNotDuplicated(Operation operationToCheck){
        Operation checkOperationInDatabase = repository.findOperationByOrderDateDescriptionAmount(
        operationToCheck.getOrderDate(),
        operationToCheck.getDescription(),
        operationToCheck.getAmount()
        );
        return checkOperationInDatabase == null;

    }
}
