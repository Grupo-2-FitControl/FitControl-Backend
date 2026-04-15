package tu_paquete.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tu_paquete.dto.MemberDTO;
import tu_paquete.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    @GetMapping
    public List<MemberDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MemberDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public MemberDTO create(@RequestBody MemberDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public MemberDTO update(@PathVariable Long id, @RequestBody MemberDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}