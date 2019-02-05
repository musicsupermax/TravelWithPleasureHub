package com.kh021j.travelwithpleasurehub.tickets.apiparser.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buying_link")
    private String linkForBuying;

    @Column(name = "price")
    private Double price;
}
