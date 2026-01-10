package fontys.i_hate_paper.Mapper;

import fontys.i_hate_paper.DTOs.CategoryDTO;
import fontys.i_hate_paper.Models.Category;
import org.springframework.stereotype.Component;


@Component
public class CategoryMapper {

    public CategoryDTO toCatDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setId(category.getId());

        return categoryDTO;
    }

    public Category toCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        return category;
    }
}
