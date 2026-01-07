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
        Category cat = getOrCreateCategory(categoryDTO.getName());
        return categoryMapper.toDTO(cat);

    }

    //for document service
    public Category getOrCreateCategory(String catName) { //UNITTEST

        if (catName == null){
            logger.warn("Category name is null");
            throw new ValidationException("CATEGORY_IS_NULL","Category name is null");
        }

        String catClean = catName.toLowerCase().trim();

        if(catClean.isBlank() ) {
            logger.warn("Category name is blank");
            throw new ValidationException("CATEGORY_IS_BLANK","Category name is blank");
        }

        Optional<Category> CatFind = categoryRepository.findByNameIgnoreCase(catClean);

        if (CatFind.isPresent()) {
            return CatFind.get(); //because its optional
        }

        Category newCategory = new Category();
        newCategory.setName(catClean);
        return categoryRepository.save(newCategory);

    }

    public Category resolveCategory(Integer categoryId, String categoryName) {

        if (categoryId != null) {
            return categoryRepository.findById(categoryId)
                    .orElseThrow(() ->
                            new ValidationException("CATEGORY_NOT_FOUND", "Category not found")
                    );
        }

        return getOrCreateCategory(categoryName);
    }


}
