package uit.carbon_shop.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.FileDocument;
import uit.carbon_shop.model.FileDocumentDTO;
import uit.carbon_shop.repos.FileDocumentRepository;
import uit.carbon_shop.util.NotFoundException;


@Service
public class FileDocumentService {

    private final FileDocumentRepository fileDocumentRepository;
    private final FileDocumentMapper fileDocumentMapper;

    public FileDocumentService(final FileDocumentRepository fileDocumentRepository,
            final FileDocumentMapper fileDocumentMapper) {
        this.fileDocumentRepository = fileDocumentRepository;
        this.fileDocumentMapper = fileDocumentMapper;
    }

    public List<FileDocumentDTO> findAll() {
        final List<FileDocument> fileDocuments = fileDocumentRepository.findAll(Sort.by("id"));
        return fileDocuments.stream()
                .map(fileDocument -> fileDocumentMapper.updateFileDocumentDTO(fileDocument, new FileDocumentDTO()))
                .toList();
    }

    public FileDocumentDTO get(final Long id) {
        return fileDocumentRepository.findById(id)
                .map(fileDocument -> fileDocumentMapper.updateFileDocumentDTO(fileDocument, new FileDocumentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FileDocumentDTO fileDocumentDTO) {
        final FileDocument fileDocument = new FileDocument();
        fileDocumentMapper.updateFileDocument(fileDocumentDTO, fileDocument);
        return fileDocumentRepository.save(fileDocument).getId();
    }

    public void update(final Long id, final FileDocumentDTO fileDocumentDTO) {
        final FileDocument fileDocument = fileDocumentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        fileDocumentMapper.updateFileDocument(fileDocumentDTO, fileDocument);
        fileDocumentRepository.save(fileDocument);
    }

    public void delete(final Long id) {
        fileDocumentRepository.deleteById(id);
    }

}
