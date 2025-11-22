package fontys.i_hate_paper.Repository;

import fontys.i_hate_paper.Models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findByFileUUID(String fileUUID);

}
