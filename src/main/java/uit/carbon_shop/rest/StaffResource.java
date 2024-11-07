package uit.carbon_shop.rest;

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
import uit.carbon_shop.model.StaffDTO;
import uit.carbon_shop.service.StaffService;
import uit.carbon_shop.util.ReferencedException;
import uit.carbon_shop.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/staffs", produces = MediaType.APPLICATION_JSON_VALUE)
public class StaffResource {

    private final StaffService staffService;

    public StaffResource(final StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<List<StaffDTO>> getAllStaffs() {
        return ResponseEntity.ok(staffService.findAll());
    }

    @GetMapping("/{staffId}")
    public ResponseEntity<StaffDTO> getStaff(@PathVariable(name = "staffId") final Long staffId) {
        return ResponseEntity.ok(staffService.get(staffId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createStaff(@RequestBody @Valid final StaffDTO staffDTO) {
        final Long createdStaffId = staffService.create(staffDTO);
        return new ResponseEntity<>(createdStaffId, HttpStatus.CREATED);
    }

    @PutMapping("/{staffId}")
    public ResponseEntity<Long> updateStaff(@PathVariable(name = "staffId") final Long staffId,
            @RequestBody @Valid final StaffDTO staffDTO) {
        staffService.update(staffId, staffDTO);
        return ResponseEntity.ok(staffId);
    }

    @DeleteMapping("/{staffId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteStaff(@PathVariable(name = "staffId") final Long staffId) {
        final ReferencedWarning referencedWarning = staffService.getReferencedWarning(staffId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        staffService.delete(staffId);
        return ResponseEntity.noContent().build();
    }

}
