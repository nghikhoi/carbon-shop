package uit.carbon_shop.service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.CompanyReview;
import uit.carbon_shop.model.CompanyReviewDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompanyReviewMapper {

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "reviewBy", ignore = true)
    CompanyReviewDTO updateCompanyReviewDTO(CompanyReview companyReview,
            @MappingTarget CompanyReviewDTO companyReviewDTO);

    @AfterMapping
    default void afterUpdateCompanyReviewDTO(CompanyReview companyReview,
            @MappingTarget CompanyReviewDTO companyReviewDTO) {
        companyReviewDTO.setCompany(companyReview.getCompany() == null ? null : companyReview.getCompany().getId());
        companyReviewDTO.setReviewBy(companyReview.getReviewBy() == null ? null : companyReview.getReviewBy().getUserId());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "reviewBy", ignore = true)
    CompanyReview updateCompanyReview(CompanyReviewDTO companyReviewDTO,
            @MappingTarget CompanyReview companyReview,
            @Context CompanyRepository companyRepository,
            @Context AppUserRepository appUserRepository);

    @AfterMapping
    default void afterUpdateCompanyReview(CompanyReviewDTO companyReviewDTO,
            @MappingTarget CompanyReview companyReview,
            @Context CompanyRepository companyRepository,
            @Context AppUserRepository appUserRepository) {
        final Company company = companyReviewDTO.getCompany() == null ? null : companyRepository.findById(companyReviewDTO.getCompany())
                .orElseThrow(() -> new NotFoundException("company not found"));
        companyReview.setCompany(company);
        final AppUser reviewBy = companyReviewDTO.getReviewBy() == null ? null : appUserRepository.findById(companyReviewDTO.getReviewBy())
                .orElseThrow(() -> new NotFoundException("reviewBy not found"));
        companyReview.setReviewBy(reviewBy);
    }

}
