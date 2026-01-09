package fontys.i_hate_paper.UnitTest;

import fontys.i_hate_paper.Exception.ValidationException;
import fontys.i_hate_paper.Mapper.CategoryMapper;
import fontys.i_hate_paper.Models.Category;
import fontys.i_hate_paper.Repository.CategoryRepository;
import fontys.i_hate_paper.Service.Impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTests {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper; //I didn't really test this yet
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setup() {
        categoryRepository = mock(CategoryRepository.class);
        categoryMapper = mock(CategoryMapper.class);
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    void Should_Get_Category_From_String() {

        //Arrange
        String categoryName = "CategoryThatExists    ";
        String cleanName = "categorythatexists";
        Category existing = new Category();
        existing.setName(cleanName);
        when(categoryRepository.findByNameIgnoreCase(cleanName)).thenReturn(Optional.of(existing));

        //Act
        Category testing = categoryService.getOrCreateCategory(categoryName);

        //Assert
        assertNotNull(testing);
        assertEquals(cleanName, testing.getName());
        verify(categoryRepository, times(1)).findByNameIgnoreCase(cleanName);
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    void Should_Create_Category_From_String_When_Category_Does_Not_Exist() {

        //Arrange
        String categoryName = "CategoryThatDoesNotExist     ";
        String cleanName = "categorythatdoesnotexist";
        when(categoryRepository.findByNameIgnoreCase(cleanName)).thenReturn(Optional.empty());
        Category savedCategory = new Category();
        savedCategory.setName(cleanName);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        //Act
        Category testing = categoryService.getOrCreateCategory(categoryName);

        //Assert
        assertNotNull(testing);
        assertEquals(cleanName, testing.getName());
        verify(categoryRepository, times(1)).findByNameIgnoreCase(cleanName);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void Should_Throw_Validation_Exception_When_Category_Is_Blank() {

        //Arrange
        String categoryName = "    ";

        //Act and Assert
        ValidationException testing = assertThrows(ValidationException.class, () -> {
            categoryService.getOrCreateCategory(categoryName);
        });
        //Assert
        assertEquals("Category name is blank", testing.getMessage());
    }

    @Test
    void Should_Throw_Validation_Exception_When_Category_Is_NULL() {

        //Arrange
        String categoryName = null;

        //Act and Assert
        ValidationException testing = assertThrows(ValidationException.class, () -> {
            categoryService.getOrCreateCategory(categoryName);
        });

        //Assert
        assertEquals("Category name is null", testing.getMessage());
    }

    @Test
    void Should_Throw_Validation_Exception_When_Category_Is_Empty() {}

}
