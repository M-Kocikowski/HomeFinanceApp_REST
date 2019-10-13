package pl.marcin.homeFinanceREST.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.homeFinanceREST.entity.Operation;
import pl.marcin.homeFinanceREST.repository.OperationsRepository;
import pl.marcin.homeFinanceREST.services.XMLToDatabase;
import pl.marcin.homeFinanceREST.xmlModel.XMLOperation;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    private XMLToDatabase xmlToDatabase;

    private OperationsRepository repository;

    public ApiController(XMLToDatabase xmlToDatabase, OperationsRepository repository) {
        this.xmlToDatabase = xmlToDatabase;
        this.repository = repository;
    }

    @PostMapping("/file")
    public String fileHandler(@RequestParam("file") MultipartFile file) throws JAXBException, IOException {
        List<XMLOperation> xmlOperations = xmlToDatabase.convertXMLToOperations(file);
        List<Operation> operationsFromXML = xmlToDatabase.getOperationsFromXML(xmlOperations);
        xmlToDatabase.saveOperationToDatabase(operationsFromXML);
        return "OK";
    }

    @GetMapping("/operations")
    public List<Operation> getAllOperations(){
        List<Operation> operationsOrderByOrderDateDesc = repository.findOperationsOrderByOrderDateDesc();
        operationsOrderByOrderDateDesc.forEach(a -> System.out.println(a.getDescription()));
        return operationsOrderByOrderDateDesc;
    }
}
