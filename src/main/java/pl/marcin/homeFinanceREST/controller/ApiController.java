package pl.marcin.homeFinanceREST.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.homeFinanceREST.entity.Operation;
import pl.marcin.homeFinanceREST.services.DbOperations;
import pl.marcin.homeFinanceREST.services.XMLToEntity;
import pl.marcin.homeFinanceREST.xmlModel.XMLOperation;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    private XMLToEntity xmlToEntity;
    private DbOperations dbOperations;

    public ApiController(XMLToEntity xmlToEntity, DbOperations dbOperations) {
        this.xmlToEntity = xmlToEntity;
        this.dbOperations = dbOperations;
    }

    @PostMapping("/post/file")
    public String fileHandler(@RequestParam("file") MultipartFile file) throws JAXBException, IOException {
        List<XMLOperation> xmlOperations = xmlToEntity.convertXMLToOperations(file);
        List<Operation> operationsFromXML = xmlToEntity.getOperationsFromXML(xmlOperations);
        dbOperations.saveOperationsToDatabase(operationsFromXML);
        return "OK";
    }

    @PostMapping("/post")
    public Operation saveSingleOperation(@RequestBody Operation operation){
        dbOperations.saveSingleOperationToDatabase(operation);
        return operation;
    }

    @GetMapping("/operations/{fromDate}/{toDate}")
    public List<Operation> getAllOperations(@PathVariable String fromDate, @PathVariable String toDate){
        return dbOperations.getOperationsByDate(fromDate, toDate);
    }

    @PutMapping("/put/{id}")
    public Operation updateSingleOperation(@RequestBody Operation operation){
        dbOperations.saveSingleOperationToDatabase(operation);
        return operation;
    }

    @GetMapping("/operation/{id}")
    public Operation getSingleOperationById(@PathVariable long id){
        return dbOperations.getSingleOperationById(id);
    }

}
