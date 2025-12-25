package fontys.i_hate_paper.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String fileUUID;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    private String description;
    private LocalDate deadline;
    private Date added_At = new Date();
    private Boolean is_Handled = false;
    private String documentStoredName;


}
