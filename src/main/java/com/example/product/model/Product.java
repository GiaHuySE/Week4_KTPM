package com.example.product.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprd")
    private int idPrd;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private String price;

    @Column(name = "describedetails")
    private String describeDetails;

    @Column(name = "link")
    private String link;

    @Column(name = "statusprd")
    private String statusPrd;

}