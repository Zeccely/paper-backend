package fontys.i_hate_paper.UnitTest;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Exception.StorageException;
import fontys.i_hate_paper.Mapper.DocumentMapper;
import fontys.i_hate_paper.Models.Category;
import fontys.i_hate_paper.Models.Document;
import fontys.i_hate_paper.Repository.DocumentRepository;
import fontys.i_hate_paper.Service.CategoryService;
import fontys.i_hate_paper.Service.DocumentService;
import fontys.i_hate_paper.Service.Impl.DocumentServiceImpl;
import fontys.i_hate_paper.Service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentServiceTests {

    private DocumentService documentService;
    private DocumentRepository docRepository;
    private DocumentMapper documentMapper;
    private CategoryService categoryService;
    private StorageService storageService;

    @BeforeEach
    public void setUp()
    {
        docRepository = mock(DocumentRepository.class);
        documentMapper = mock(DocumentMapper.class);
        categoryService = mock(CategoryService.class);
        storageService = mock(StorageService.class);
        documentService = new DocumentServiceImpl(docRepository, documentMapper, categoryService, storageService);
    }

    @Test
    public void Should_Save_Document(){

        //Arrange
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setCategoryName("category");
        documentDTO.setTitle("title");
        documentDTO.setDescription("description");
        documentDTO.setCategoryId(1);
        documentDTO.setDeadline("2027-05-28");
        MultipartFile thisfile = mock(MultipartFile.class);
        documentDTO.setFile(thisfile);
        when(thisfile.getOriginalFilename()).thenReturn("filename.extension");
        Category category = new Category();
        when(categoryService.getOrCreateCategory("category")).thenReturn(category);

        Document savedDocument = new Document();
        when(documentMapper.toDoc(any(DocumentDTO.class))).thenReturn(savedDocument);

        DocumentDTO returnedDTO = new DocumentDTO();
        when(documentMapper.toDocDTO(any(Document.class))).thenReturn(returnedDTO);

        //Act
        DocumentDTO testing = documentService.saveDocument(documentDTO);

        //Assert
        verify(storageService).storeDocument(eq(thisfile), endsWith(".extension"));
        verify(categoryService).getOrCreateCategory("category");
        verify(docRepository).save(savedDocument);
    }

    @Test
    public void Should_Not_Save_Document_When_StoreDocument_Throws(){

        //Arrange
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setCategoryName("category");
        documentDTO.setTitle("title");
        documentDTO.setDescription("description");
        documentDTO.setCategoryId(1);
        documentDTO.setDeadline("2027-05-28");
        MultipartFile multipartFile = mock(MultipartFile.class);
        documentDTO.setFile(multipartFile);
        when(multipartFile.getOriginalFilename()).thenReturn("filename.extension");
        Category category = new Category();
        when(categoryService.getOrCreateCategory("category")).thenReturn(category);

        Document savedDocument = new Document();
        when(documentMapper.toDoc(any(DocumentDTO.class))).thenReturn(savedDocument);

        DocumentDTO returnedDTO = new DocumentDTO();
        when(documentMapper.toDocDTO(any(Document.class))).thenReturn(returnedDTO);

        doThrow(new StorageException("DOCUMENT_SAVE_FAILED", "Failed to save document: (filename and stack)"))
                .when(storageService)
                .storeDocument(any(MultipartFile.class), anyString()); //if a method returns void you cannot when/then throw

        //Act
        assertThrows(StorageException.class, () -> {documentService.saveDocument(documentDTO);});
        verify(docRepository, never()).save(any(Document.class));
    }


}
