package fontys.i_hate_paper.Service.Impl;
import fontys.i_hate_paper.DTOs.CategoryDTO;
import fontys.i_hate_paper.Exception.ValidationException;
import fontys.i_hate_paper.Mapper.CategoryMapper;
import fontys.i_hate_paper.Models.Category;
import fontys.i_hate_paper.Repository.CategoryRepository;
import fontys.i_hate_paper.Service.CategoryService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        String catName = categoryDTO.getName().trim();

        if (catName.isBlank()) {
            logger.warn("Category name is null or blank");
            throw new ValidationException("CATEGORY_IS_NULL","Category name is null or blank");
        }

        Optional<Category> CatFind = categoryRepository.findByNameIgnoreCase(catName);

        if (CatFind.isEmpty()) {
            Category newCategory = new Category();
            newCategory.setName(catName);
            Category savedCat = categoryRepository.save(newCategory);
            return categoryMapper.toDTO(savedCat);
        }

        return categoryMapper.toDTO(CatFind.get());
    }
}
