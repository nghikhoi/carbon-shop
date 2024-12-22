package uit.carbon_shop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.CompanyReview;
import uit.carbon_shop.model.CompanyReviewDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.CompanyReviewRepository;
import uit.carbon_shop.util.NotFoundException;


@Service
public class CompanyReviewService {

    private final CompanyReviewRepository companyReviewRepository;
    private final CompanyRepository companyRepository;
    private final AppUserRepository appUserRepository;
    private final CompanyReviewMapper companyReviewMapper;

    public CompanyReviewService(final CompanyReviewRepository companyReviewRepository,
            final CompanyRepository companyRepository, final AppUserRepository appUserRepository,
            final CompanyReviewMapper companyReviewMapper) {
        this.companyReviewRepository = companyReviewRepository;
        this.companyRepository = companyRepository;
        this.appUserRepository = appUserRepository;
        this.companyReviewMapper = companyReviewMapper;
    }

    public Page<CompanyReviewDTO> findAll(final String filter, final Pageable pageable) {
        Page<CompanyReview> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = companyReviewRepository.findAllById(longFilter, pageable);
        } else {
            page = companyReviewRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(companyReview -> companyReviewMapper.updateCompanyReviewDTO(companyReview, new CompanyReviewDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public CompanyReviewDTO get(final Long id) {
        return companyReviewRepository.findById(id)
                .map(companyReview -> companyReviewMapper.updateCompanyReviewDTO(companyReview, new CompanyReviewDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CompanyReviewDTO companyReviewDTO) {
        final CompanyReview companyReview = new CompanyReview();
        companyReviewMapper.updateCompanyReview(companyReviewDTO, companyReview, companyRepository, appUserRepository);
        return companyReviewRepository.save(companyReview).getId();
    }

    public void update(final Long id, final CompanyReviewDTO companyReviewDTO) {
        final CompanyReview companyReview = companyReviewRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        companyReviewMapper.updateCompanyReview(companyReviewDTO, companyReview, companyRepository, appUserRepository);
        companyReviewRepository.save(companyReview);
    }

    public void delete(final Long id) {
        companyReviewRepository.deleteById(id);
    }

}
