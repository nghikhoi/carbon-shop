package uit.carbon_shop.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.ReviewCompany;
import uit.carbon_shop.domain.User;
import uit.carbon_shop.model.ReviewCompanyDTO;
import uit.carbon_shop.repos.ReviewCompanyRepository;
import uit.carbon_shop.repos.UserRepository;
import uit.carbon_shop.util.NotFoundException;
import uit.carbon_shop.util.ReferencedWarning;


@Service
public class ReviewCompanyService {

    private final ReviewCompanyRepository reviewCompanyRepository;
    private final UserRepository userRepository;

    public ReviewCompanyService(final ReviewCompanyRepository reviewCompanyRepository,
            final UserRepository userRepository) {
        this.reviewCompanyRepository = reviewCompanyRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewCompanyDTO> findAll() {
        final List<ReviewCompany> reviewCompanies = reviewCompanyRepository.findAll(Sort.by("reviewCompanyId"));
        return reviewCompanies.stream()
                .map(reviewCompany -> mapToDTO(reviewCompany, new ReviewCompanyDTO()))
                .toList();
    }

    public ReviewCompanyDTO get(final Long reviewCompanyId) {
        return reviewCompanyRepository.findById(reviewCompanyId)
                .map(reviewCompany -> mapToDTO(reviewCompany, new ReviewCompanyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ReviewCompanyDTO reviewCompanyDTO) {
        final ReviewCompany reviewCompany = new ReviewCompany();
        mapToEntity(reviewCompanyDTO, reviewCompany);
        return reviewCompanyRepository.save(reviewCompany).getReviewCompanyId();
    }

    public void update(final Long reviewCompanyId, final ReviewCompanyDTO reviewCompanyDTO) {
        final ReviewCompany reviewCompany = reviewCompanyRepository.findById(reviewCompanyId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(reviewCompanyDTO, reviewCompany);
        reviewCompanyRepository.save(reviewCompany);
    }

    public void delete(final Long reviewCompanyId) {
        reviewCompanyRepository.deleteById(reviewCompanyId);
    }

    private ReviewCompanyDTO mapToDTO(final ReviewCompany reviewCompany,
            final ReviewCompanyDTO reviewCompanyDTO) {
        reviewCompanyDTO.setReviewCompanyId(reviewCompany.getReviewCompanyId());
        reviewCompanyDTO.setText(reviewCompany.getText());
        reviewCompanyDTO.setStar(reviewCompany.getStar());
        reviewCompanyDTO.setReviewImage(reviewCompany.getReviewImage());
        return reviewCompanyDTO;
    }

    private ReviewCompany mapToEntity(final ReviewCompanyDTO reviewCompanyDTO,
            final ReviewCompany reviewCompany) {
        reviewCompany.setText(reviewCompanyDTO.getText());
        reviewCompany.setStar(reviewCompanyDTO.getStar());
        reviewCompany.setReviewImage(reviewCompanyDTO.getReviewImage());
        return reviewCompany;
    }

    public ReferencedWarning getReferencedWarning(final Long reviewCompanyId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ReviewCompany reviewCompany = reviewCompanyRepository.findById(reviewCompanyId)
                .orElseThrow(NotFoundException::new);
        final User reviewCompanyIdUser = userRepository.findFirstByReviewCompanyId(reviewCompany);
        if (reviewCompanyIdUser != null) {
            referencedWarning.setKey("reviewCompany.user.reviewCompanyId.referenced");
            referencedWarning.addParam(reviewCompanyIdUser.getUserId());
            return referencedWarning;
        }
        return null;
    }

}
