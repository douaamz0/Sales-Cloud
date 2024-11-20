package org.example.salesmanagement.dto;

import lombok.*;
import org.example.salesmanagement.entity.Vente;

import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class InvoiceResponse {
    private String clientName;
    private String clientPrenom;
    private List<Vente> sales;
    private double totalAmount;


}
