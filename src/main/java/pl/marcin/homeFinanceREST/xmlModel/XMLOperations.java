package pl.marcin.homeFinanceREST.xmlModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "operations")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLOperations {

    @XmlElement(name = "operation")
    private List<XMLOperation> XMLOperationList = null;

    public List<XMLOperation> getXMLOperationList() {
        return XMLOperationList;
    }

    public void setXMLOperationList(List<XMLOperation> XMLOperationList) {
        this.XMLOperationList = XMLOperationList;
    }
}
