package uit.carbon_shop.service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Question;
import uit.carbon_shop.model.QuestionDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface QuestionMapper {

    @Mapping(target = "askedBy", ignore = true)
    QuestionDTO updateQuestionDTO(Question question, @MappingTarget QuestionDTO questionDTO);

    @AfterMapping
    default void afterUpdateQuestionDTO(Question question, @MappingTarget QuestionDTO questionDTO) {
        questionDTO.setAskedBy(question.getAskedBy() == null ? null : question.getAskedBy().getUserId());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "askedBy", ignore = true)
    Question updateQuestion(QuestionDTO questionDTO, @MappingTarget Question question,
            @Context AppUserRepository appUserRepository);

    @AfterMapping
    default void afterUpdateQuestion(QuestionDTO questionDTO, @MappingTarget Question question,
            @Context AppUserRepository appUserRepository) {
        final AppUser askedBy = questionDTO.getAskedBy() == null ? null : appUserRepository.findById(questionDTO.getAskedBy())
                .orElseThrow(() -> new NotFoundException("askedBy not found"));
        question.setAskedBy(askedBy);
    }

}
