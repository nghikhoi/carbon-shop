package uit.carbon_shop.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uit.carbon_shop.domain.FileDocument;
import uit.carbon_shop.model.FileDocumentDTO;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FileDocumentMapper {

    FileDocumentDTO updateFileDocumentDTO(FileDocument fileDocument,
            @MappingTarget FileDocumentDTO fileDocumentDTO);

    @Mapping(target = "id", ignore = true)
    FileDocument updateFileDocument(FileDocumentDTO fileDocumentDTO,
            @MappingTarget FileDocument fileDocument);

}
