package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Models.Document;
import fontys.i_hate_paper.Repository.DocumentRepository;
import fontys.i_hate_paper.Service.DocumentService;
import fontys.i_hate_paper.mapper.DocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository docRepository;

    public DocumentDTO addAFile(DocumentDTO documentDTO) {
        Document file = DocumentMapper.toFile(documentDTO); //make this a normal file first
        docRepository.save(file); //save this into database/list
        return DocumentMapper.toFileDTO(file); //give me back the dto of what I saved
    }


    public List<DocumentDTO> getTheFiles() {
        List<DocumentDTO> secrets = new ArrayList<>();
        for (Document file : docRepository.findAll()){
            secrets.add(DocumentMapper.toFileDTO(file));
        }
       return secrets;
    }

    @Override
    public DocumentDTO checkReturn(DocumentDTO documentDTO) {
        Document file = DocumentMapper.toFile(documentDTO);
        if (docRepository.findById(file.getId()).isPresent()) {
            return DocumentMapper.toFileDTO(file);
        }
        return null;
    }



}
