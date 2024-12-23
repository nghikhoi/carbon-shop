package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.PagedProjectDTO;
import uit.carbon_shop.model.PagedQuestionDTO;
import uit.carbon_shop.model.QuestionDTO;
import uit.carbon_shop.model.UserAskDTO;
import uit.carbon_shop.model.UserRole;
import uit.carbon_shop.service.IdGeneratorService;
import uit.carbon_shop.service.ProjectService;
import uit.carbon_shop.service.QuestionService;


@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('" + UserRole.Fields.SELLER_OR_BUYER + "', '" + UserRole.Fields.MEDIATOR + "')")
@SecurityRequirement(name = "bearer-jwt")
@RequiredArgsConstructor
public class UserController {

    private final QuestionService questionService;
    private final IdGeneratorService idGeneratorService;
    private final ProjectService projectService;

    @PostMapping("/question")
    public ResponseEntity<QuestionDTO> newQuestion(@RequestBody @Valid final UserAskDTO userAskDTO, Authentication authentication) {
        var userId = Long.parseLong(authentication.getName());
        var question = new QuestionDTO();
        question.setId(idGeneratorService.generateId());
        question.setQuestion(userAskDTO.getQuestion());
        question.setAskedBy(userId);
        questionService.create(question);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/questions")
    public ResponseEntity<PagedQuestionDTO> viewQuestions(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @PageableDefault(size = 20) final Pageable pageable
    ) {
        return ResponseEntity.ok(new PagedQuestionDTO(questionService.findAll(filter, pageable)));
    }

    @GetMapping("/projects")
    public ResponseEntity<PagedProjectDTO> viewAllProject(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "projectId") @PageableDefault(size = 20) final Pageable pageable
    ) {
        return ResponseEntity.ok(new PagedProjectDTO(projectService.findAll(filter, pageable)));
    }

}
