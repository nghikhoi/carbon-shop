package uit.carbon_shop.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.model.CompanyDTO;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompanyMapper {

    CompanyDTO updateCompanyDTO(Company company, @MappingTarget CompanyDTO companyDTO);

    @Mapping(target = "id", ignore = true)
    Company updateCompany(CompanyDTO companyDTO, @MappingTarget Company company);

}
