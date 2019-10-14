package pl.marcin.homeFinanceREST.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.homeFinanceREST.entity.Operation;
import pl.marcin.homeFinanceREST.xmlModel.XMLAccountHistory;
import pl.marcin.homeFinanceREST.xmlModel.XMLOperation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLToEntity {

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
            operation.setOrderDate(LocalDate.parse(o.getOrderDate()));
            operation.setDescription(o.getDescription());
            operation.setAmount(o.getAmount());
            operations.add(operation);

        }
        return operations;
    }

}
