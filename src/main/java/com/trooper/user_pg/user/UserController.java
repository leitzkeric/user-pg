package com.trooper.user_pg.user;

import com.trooper.user_pg.service.PasswordHashingBCrypt;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> list(Pageable pageable){
        var page =repository.findAllByActiveTrue(pageable).map(UserDTO::new);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailDTO> save(@RequestBody @Valid UserDTO data, UriComponentsBuilder uriBuilder) {
        var passwd = new PasswordHashingBCrypt().encodePassword(data.password());
        var user = new User(data, passwd);
        repository.save(user);
        var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        user.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDTO> detail(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        return ResponseEntity.ok(new UserDetailDTO(user));
    }

    @GetMapping("/{id}/password")
    public boolean checkPassword (@PathVariable Long id, @RequestBody UserPwdDTO userPwdDTO) {
        var user = repository.getReferenceById(id);
        var isPwdOk = new PasswordHashingBCrypt().checkEncodedPassword(userPwdDTO.password(), user.getPassword());
        return isPwdOk;
    }

    @GetMapping("/login_user")
    public boolean logIn (@RequestBody LoginDTO loginDTO) {
        var user = repository.getReferenceByLogin(loginDTO.login());
        return new PasswordHashingBCrypt().checkEncodedPassword(loginDTO.password(), user.getPassword());
    }

}
