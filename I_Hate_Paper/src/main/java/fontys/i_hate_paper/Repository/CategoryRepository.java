package fontys.i_hate_paper.Repository;

import fontys.i_hate_paper.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
