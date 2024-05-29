package com.victormorais.personalplanner.controller;

import com.victormorais.personalplanner.domain.entities.Usuario;
import com.victormorais.personalplanner.domain.model.LoginDTO;
import com.victormorais.personalplanner.domain.model.UsuarioDTO;
import com.victormorais.personalplanner.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                       @RequestHeader("UsuarioLogado") String idUsuarioLogado) {

        /*String errorMsg = validaUsuarioAdmin(idUsuarioLogado);

        if(errorMsg!= null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMsg);
        }*/

        // Agora, chama o serviço para criar o usuário
        Usuario user = usuarioService.criarUsuario(usuarioDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity listarUsuarios(@RequestHeader("UsuarioLogado") String idUsuarioLogado) {

        String errorMsg = validaUsuarioAdmin(idUsuarioLogado);

        if(errorMsg!= null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMsg);
        }

        // Agora, chama o serviço para listar os usuários
        List<Usuario> users = usuarioService.listarUsuarios();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity deletarUsuario(@RequestHeader("UsuarioLogado") String idUsuarioLogado,
                                         @PathVariable("idUsuario") String idUsuario) {

        String errorMsg = validaUsuarioAdmin(idUsuarioLogado);

        if(errorMsg!= null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMsg);
        }

        // Agora, chama o serviço para deletar o usuário
        String msg = usuarioService.deletarUsuario(idUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @GetMapping("/{username}/dica")
    public ResponseEntity obterDicaSenha(@PathVariable("username") String username) {

        // Agora, chama o serviço para obter a dica de senha do usuário
        String dica = usuarioService.getDicaSenha(username);

        return ResponseEntity.status(HttpStatus.OK).body(dica);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        // Verifica as credenciais do usuário
        String returnMsg = usuarioService.login(loginDTO.getUsername(), loginDTO.getSenha());

        if (returnMsg != null) {
            return ResponseEntity.ok(returnMsg);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    private String validaUsuarioAdmin(String idUsuario){

        String msg = null;

        // Verifica se o cabeçalho de autorização está presente e contém o ID do usuário
        if (idUsuario == null || idUsuario.isEmpty()) {
            msg = "Cabeçalho de autorização ausente ou inválido";
        }

        if(!usuarioService.isUsuarioAdmin(idUsuario)){
            msg = "Usuário não autorizado a realizar esta operação";
        }

        return msg;
    }

}