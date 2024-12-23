package uit.carbon_shop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Question;
import uit.carbon_shop.model.QuestionDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.QuestionRepository;
import uit.carbon_shop.util.NotFoundException;


@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AppUserRepository appUserRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(final QuestionRepository questionRepository,
            final AppUserRepository appUserRepository, final QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.appUserRepository = appUserRepository;
        this.questionMapper = questionMapper;
    }

    public Page<QuestionDTO> findAll(final String filter, final Pageable pageable) {
        Page<Question> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = questionRepository.findAllById(longFilter, pageable);
        } else {
            page = questionRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(question -> questionMapper.updateQuestionDTO(question, new QuestionDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public Page<QuestionDTO> findByAnswerIsNull(final Pageable pageable) {
        final Page<Question> page = questionRepository.findByAnswerNull(pageable);
        return new PageImpl<>(page.getContent()
                .stream()
                .map(question -> questionMapper.updateQuestionDTO(question, new QuestionDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public QuestionDTO get(final Long id) {
        return questionRepository.findById(id)
                .map(question -> questionMapper.updateQuestionDTO(question, new QuestionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final QuestionDTO questionDTO) {
        final Question question = new Question();
        question.setId(questionDTO.getId());
        questionMapper.updateQuestion(questionDTO, question, appUserRepository);
        return questionRepository.save(question).getId();
    }

    public void update(final Long id, final QuestionDTO questionDTO) {
        final Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        questionMapper.updateQuestion(questionDTO, question, appUserRepository);
        questionRepository.save(question);
    }

    public void delete(final Long id) {
        questionRepository.deleteById(id);
    }

}
