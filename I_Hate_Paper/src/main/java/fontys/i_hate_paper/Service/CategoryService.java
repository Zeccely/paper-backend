package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.DTOs.CategoryDTO;
import fontys.i_hate_paper.Models.Category;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    Category getOrCreateCategory(String catName);
    List<CategoryDTO> getCategories();
}
