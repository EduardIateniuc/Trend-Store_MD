package md.donesk.trendshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Long customerId;

    @NotBlank
    @Column(name = "address_line_1")
    private String addressLine1;

    @Size(max = 255)
    @Column(name = "address_line_2")
    private String addressLine2;

    @NotBlank
    @Size(max = 20)
    private String postalCode;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
