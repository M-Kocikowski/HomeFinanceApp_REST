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
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLToDatabase {

    private OperationsRepository repository;

    public XMLToDatabase(OperationsRepository repository) {
        this.repository = repository;
    }

    public List<XMLOperation> convertXMLToOperations(MultipartFile file) throws JAXBException, IOException {

        JAXBContext context = JAXBContext.newInstance(XMLAccountHistory.class);
        XMLAccountHistory XMLAccountHistory = (XMLAccountHistory) context.createUnmarshaller()
                .unmarshal(file.getInputStream());
        return XMLAccountHistory.getXMLOperations().getXMLOperationList();

    }

    public List<Operation> getOperationsFromXML(List<XMLOperation> xmlOperations){

        List<Operation> operations = new ArrayList<>();
        for (XMLOperation o : xmlOperations) {

            Operation operation = new Operation();
            operation.setType(o.getType());
            operation.setOrderDate(o.getOrderDate());
            operation.setDescription(o.getDescription());
            operation.setAmount(o.getAmount());
            operations.add(operation);
            System.out.println(repository.findOperationByOrderDateDescriptionAmount
                    (operation.getOrderDate(), operation.getDescription(), operation.getAmount()));

        }
        return operations;
    }

    public void saveOperationToDatabase(List<Operation> operations){

        operations.stream()
                .filter(o -> repository.findOperationByOrderDateDescriptionAmount(o.getOrderDate(), o.getDescription(), o.getAmount()) == null)
                .forEach(o -> repository.save(o));

    }
}
