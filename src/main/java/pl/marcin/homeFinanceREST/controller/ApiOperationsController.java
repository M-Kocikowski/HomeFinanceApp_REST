package pl.marcin.homeFinanceREST.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.homeFinanceREST.entity.Operation;
import pl.marcin.homeFinanceREST.services.OperationsServices;
import pl.marcin.homeFinanceREST.xmlModel.XMLOperation;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiOperationsController {

    private OperationsServices operationsServices;

    public ApiOperationsController(OperationsServices operationsServices) {
        this.operationsServices = operationsServices;
    }

    @PostMapping("/post/file")
    public String fileHandler(@RequestParam("file") MultipartFile file) throws JAXBException, IOException {
        List<XMLOperation> xmlOperations = operationsServices.convertXMLToOperations(file);
        List<Operation> operationsFromXML = operationsServices.getOperationsFromXML(xmlOperations);
        operationsServices.saveOperationsToDatabase(operationsFromXML);
        return "OK";
    }

    @PostMapping("/post")
    public Operation saveSingleOperation(@RequestBody Operation operation){
        operationsServices.saveSingleOperationToDatabase(operation);
        return operation;
    }

    @GetMapping("/{fromDate}/{toDate}")
    public List<Operation> getAllOperations(@PathVariable String fromDate, @PathVariable String toDate){
        return operationsServices.getOperationsByDate(fromDate, toDate);
    }

    @PutMapping("/put/{id}")
    public Operation updateSingleOperation(@RequestBody Operation operation){
        operationsServices.saveSingleOperationToDatabase(operation);
        return operation;
    }

    @GetMapping("/{id}")
    public Operation getSingleOperationById(@PathVariable long id){
        return operationsServices.getSingleOperationById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSingleOperation(@PathVariable long id){
        operationsServices.deleteOperation(id);
    }

}
