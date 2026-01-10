package fontys.i_hate_paper.UnitTest;


import fontys.i_hate_paper.Configuration.DocumentStorageProperties;
import fontys.i_hate_paper.Service.Impl.StorageServiceImpl;
import fontys.i_hate_paper.Service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageServiceTests {

    private StorageService storageService;
    private DocumentStorageProperties docProps;

    @BeforeEach
    public void setup() {
        storageService = new StorageServiceImpl(docProps);
    }

    @Test
    public void Should_Store_Document(){

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
