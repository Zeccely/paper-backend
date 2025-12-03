package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.DTOs.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> findCatByID(int id);
}
