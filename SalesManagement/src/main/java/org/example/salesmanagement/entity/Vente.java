package org.example.salesmanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/*Table:personnalier le nom de la table sinon la table sera crée avec le meme nom que la classe*/
@Table(name="sales")
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    @Temporal(TemporalType.DATE)
    private Date date;

    private int quantite;
    @JsonProperty("isInvoiced")
    private boolean isInvoiced=false;

    public double getTotalPrice() {
        return produit.getPrix() * quantite;
    }

}
