package pl.marcin.homeFinanceREST.xmlModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "account-history")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLAccountHistory {

    @XmlElement(name = "operations")
    private XMLOperations XMLOperations;

    public XMLOperations getXMLOperations() {
        return XMLOperations;
    }

    public void setXMLOperations(XMLOperations XMLOperations) {
        this.XMLOperations = XMLOperations;
    }

}
