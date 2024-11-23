package ma.rest.spring.entities;

import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "comptes")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompteList {
    @XmlElement(name = "compte")
    private List<Compte> comptes;

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}