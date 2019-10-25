package pl.marcin.homeFinanceREST.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.homeFinanceREST.entity.CategorizedItem;
import pl.marcin.homeFinanceREST.entity.Operation;
import pl.marcin.homeFinanceREST.repository.CategorizedItemRepository;
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

    private OperationsRepository operationsRepository;

    private CategorizedItemRepository itemRepository;

    public OperationsServices(OperationsRepository operationsRepository, CategorizedItemRepository itemRepository) {
        this.operationsRepository = operationsRepository;
        this.itemRepository = itemRepository;
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

        List<CategorizedItem> items = itemRepository.findAll();

        List<Operation> filteredOperations = operations.stream()
                .filter(this::checkIfOperationNotDuplicated)
                .map(o -> categorizeOperation(o, items))
                .collect(Collectors.toList());

        operationsRepository.saveAll(filteredOperations);

    }

    public Operation saveSingleOperationToDatabase(Operation operation, boolean isUpdate) {
        if (checkIfOperationNotDuplicated(operation) && !isUpdate) {
            return operationsRepository.save(operation);
        }else if (isUpdate){
            return operationsRepository.save(operation);
        }
        return null;
    }

    public List<Operation> getOperationsByDate(String fromDate, String toDate) {

        return operationsRepository.findOperationsByOrderDateOrderByOrderDateDesc(
                LocalDate.parse(fromDate), LocalDate.parse(toDate)
        );
    }

    public Operation getSingleOperationById(long id) {
        return operationsRepository.findById(id).orElse(null);
    }

    public void deleteOperation(long id){
        operationsRepository.deleteById(id);
    }

    private boolean checkIfOperationNotDuplicated(Operation operationToCheck) {
        Operation checkOperationInDatabase = operationsRepository.findOperationByOrderDateDescriptionAmount(
                operationToCheck.getOrderDate(),
                operationToCheck.getDescription(),
                operationToCheck.getAmount()
        );
        return checkOperationInDatabase == null;
    }

    private Operation categorizeOperation(Operation operation, List<CategorizedItem> items){

        for (CategorizedItem item : items){
            if (operation.getDescription().toLowerCase().contains(item.getItem().toLowerCase())){
                operation.setCategory(item.getCategory());
                return operation;
            }
        }
        return operation;
    }


}
