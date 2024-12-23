package uit.carbon_shop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.CompanyReview;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.model.CompanyDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.CompanyReviewRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.util.NotFoundException;
import uit.carbon_shop.util.ReferencedWarning;


@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final AppUserRepository appUserRepository;
    private final ProjectRepository projectRepository;
    private final CompanyReviewRepository companyReviewRepository;

    public CompanyService(final CompanyRepository companyRepository,
            final CompanyMapper companyMapper, final AppUserRepository appUserRepository,
            final ProjectRepository projectRepository,
            final CompanyReviewRepository companyReviewRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.appUserRepository = appUserRepository;
        this.projectRepository = projectRepository;
        this.companyReviewRepository = companyReviewRepository;
    }

    public Page<CompanyDTO> findAll(final String filter, final Pageable pageable) {
        Page<Company> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = companyRepository.findAllById(longFilter, pageable);
        } else {
            page = companyRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(company -> companyMapper.updateCompanyDTO(company, new CompanyDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public CompanyDTO get(final Long id) {
        return companyRepository.findById(id)
                .map(company -> companyMapper.updateCompanyDTO(company, new CompanyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CompanyDTO companyDTO) {
        final Company company = new Company();
        company.setId(companyDTO.getId());
        companyMapper.updateCompany(companyDTO, company);
        return companyRepository.saveAndFlush(company).getId();
    }

    public void update(final Long id, final CompanyDTO companyDTO) {
        final Company company = companyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        companyMapper.updateCompany(companyDTO, company);
        companyRepository.saveAndFlush(company);
    }

    public void delete(final Long id) {
        companyRepository.deleteById(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Company company = companyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final AppUser companyAppUser = appUserRepository.findFirstByCompany(company);
        if (companyAppUser != null) {
            referencedWarning.setKey("company.appUser.company.referenced");
            referencedWarning.addParam(companyAppUser.getUserId());
            return referencedWarning;
        }
        final Project ownerCompanyProject = projectRepository.findFirstByOwnerCompany(company);
        if (ownerCompanyProject != null) {
            referencedWarning.setKey("company.project.ownerCompany.referenced");
            referencedWarning.addParam(ownerCompanyProject.getProjectId());
            return referencedWarning;
        }
        final CompanyReview companyCompanyReview = companyReviewRepository.findFirstByCompany(company);
        if (companyCompanyReview != null) {
            referencedWarning.setKey("company.companyReview.company.referenced");
            referencedWarning.addParam(companyCompanyReview.getId());
            return referencedWarning;
        }
        return null;
    }

}
