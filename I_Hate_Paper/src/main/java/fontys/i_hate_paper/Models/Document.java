package fontys.i_hate_paper.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @Column (unique = true)
    private String Title;
    //@Column (nullable = false)
    private Integer CategoryID;
    private String Description;
    private LocalDateTime Deadline;
    private Date Added_At = new Date();
    private boolean Is_Handled = false;
    private String Document_Path;


}
