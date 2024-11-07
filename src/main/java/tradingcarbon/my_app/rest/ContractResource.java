package tradingcarbon.my_app.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tradingcarbon.my_app.model.ContractDTO;
import tradingcarbon.my_app.service.ContractService;
import tradingcarbon.my_app.util.ReferencedException;
import tradingcarbon.my_app.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractResource {

    private final ContractService contractService;

    public ContractResource(final ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        return ResponseEntity.ok(contractService.findAll());
    }

    @GetMapping("/{constractId}")
    public ResponseEntity<ContractDTO> getContract(
            @PathVariable(name = "constractId") final Long constractId) {
        return ResponseEntity.ok(contractService.get(constractId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createContract(@RequestBody @Valid final ContractDTO contractDTO) {
        final Long createdConstractId = contractService.create(contractDTO);
        return new ResponseEntity<>(createdConstractId, HttpStatus.CREATED);
    }

    @PutMapping("/{constractId}")
    public ResponseEntity<Long> updateContract(
            @PathVariable(name = "constractId") final Long constractId,
            @RequestBody @Valid final ContractDTO contractDTO) {
        contractService.update(constractId, contractDTO);
        return ResponseEntity.ok(constractId);
    }

    @DeleteMapping("/{constractId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteContract(
            @PathVariable(name = "constractId") final Long constractId) {
        final ReferencedWarning referencedWarning = contractService.getReferencedWarning(constractId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        contractService.delete(constractId);
        return ResponseEntity.noContent().build();
    }

}
