package fontys.i_hate_paper.UnitTest;


import fontys.i_hate_paper.Configuration.DocumentStorageProperties;
import fontys.i_hate_paper.Service.Impl.StorageServiceImpl;
import fontys.i_hate_paper.Service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;
import java.nio.file.Files;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StorageServiceTests {

    private StorageService storageService;
    private DocumentStorageProperties docProps;

    @BeforeEach
    public void setup() {

        storageService = new StorageServiceImpl(docProps);
    }

    @Test
    public void Should_Store_Document(){
        //Arrange
        MultipartFile incoming_doc = mock(MultipartFile.class);
        String fileName = "Name of this document.ext";
        FileOutputStream fileOutputStream = mock(FileOutputStream.class);

        //Act
        storageService.storeDocument(incoming_doc, fileName);

        //Assert

    }

    @Test
    public void Should_Throw_Exception_When_Path_Is_Invalid(){

    }

    @Test
    public void Should_Throw_Exception_When_Save_Fails(){

    }

    @Test
    public void Should_Load_Document(){

    }

    @Test
    public void Should_Throw_Storage_Exception_When_File_Is_Not_Found(){

    }

    @Test
    public void Should_Throw_Storage_Exception_When_File_Path_Is_Invalid(){

    }
}
