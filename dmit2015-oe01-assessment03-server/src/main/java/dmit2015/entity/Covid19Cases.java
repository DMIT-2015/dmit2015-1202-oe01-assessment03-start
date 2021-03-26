package dmit2015.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "assessment03_covid19cases")
public class Covid19Cases implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "The Zone field is required.")
    @Column(nullable = false, length = 25)
    private String zone;

    @Min(value = 0, message = "The Active Cases must be zero or more")
    @Column(nullable = false)
    private Integer activeCases;

    @NotNull(message = "The Report Data field is required.")
    @Column(nullable = false)
    private LocalDate reportDate;

}
