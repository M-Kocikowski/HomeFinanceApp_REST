package pl.marcin.homeFinanceREST.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.homeFinanceREST.entity.Operation;
import pl.marcin.homeFinanceREST.repository.OperationsRepository;
import pl.marcin.homeFinanceREST.xmlModel.XMLAccountHistory;
import pl.marcin.homeFinanceREST.xmlModel.XMLOperation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationsServices {

    private OperationsRepository repository;

    public OperationsServices(OperationsRepository repository) {
        this.repository = repository;
    }

    public List<XMLOperation> convertXMLToOperations(MultipartFile file) throws JAXBException, IOException {

        JAXBContext context = JAXBContext.newInstance(XMLAccountHistory.class);
        XMLAccountHistory XMLAccountHistory = (XMLAccountHistory) context.createUnmarshaller()
                .unmarshal(file.getInputStream());
        return XMLAccountHistory.getXMLOperations().getXMLOperationList();

    }

    public List<Operation> getOperationsFromXML(List<XMLOperation> xmlOperations) {

        List<Operation> operations = new ArrayList<>();
        for (XMLOperation o : xmlOperations) {

            Operation operation = new Operation();
            operation.setType(o.getType());
            operation.setOrderDate(LocalDate.parse(o.getOrderDate()));
            operation.setDescription(o.getDescription());
            operation.setAmount(o.getAmount());
            operations.add(operation);

        }
        return operations;
    }

    public void saveOperationsToDatabase(List<Operation> operations) {

        List<Operation> filteredOperations = operations.stream()
                .filter(this::checkIfOperationNotDuplicated)
                .collect(Collectors.toList());

        repository.saveAll(filteredOperations);

    }

    public Operation saveSingleOperationToDatabase(Operation operation, boolean isUpdate) {
        if (checkIfOperationNotDuplicated(operation) && !isUpdate) {
            return repository.save(operation);
        }else if (isUpdate){
            return repository.save(operation);
        }
        return null;
    }

    public List<Operation> getOperationsByDate(String fromDate, String toDate) {

        return repository.findOperationsByOrderDateOrderByOrderDateDesc(
                LocalDate.parse(fromDate), LocalDate.parse(toDate)
        );
    }

    public Operation getSingleOperationById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteOperation(long id){
        repository.deleteById(id);
    }

    private boolean checkIfOperationNotDuplicated(Operation operationToCheck) {
        Operation checkOperationInDatabase = repository.findOperationByOrderDateDescriptionAmount(
                operationToCheck.getOrderDate(),
                operationToCheck.getDescription(),
                operationToCheck.getAmount()
        );
        return checkOperationInDatabase == null;

    }


}
