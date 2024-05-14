package com.victormorais.personalplanner.services;

import com.victormorais.personalplanner.domain.entities.Usuario;
import com.victormorais.personalplanner.domain.model.UsuarioDTO;
import com.victormorais.personalplanner.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setDicaSenha(usuarioDTO.getDicaSenha());
        usuario.setAdmin(usuarioDTO.isAdmin());

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public String deletarUsuario(String idUsuario){
        Optional<Usuario> usuario = usuarioRepository.findById(UUID.fromString(idUsuario));
        if (usuario.isPresent() ) {
            usuarioRepository.delete(usuario.get());
            return "Usuário deletado com sucesso.";
        }else{
            return "Usuário não encontrado!";
        }

    }

    public String login(String username, String senha) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return "Login bem-sucedido" +  " \n Id do usuário:" + usuario.getIdUsuario(); // Login bem-sucedido
        }
        return null; // Login falhou
    }

    public String getDicaSenha(String username){
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null) {
            return usuario.getDicaSenha(); // Login bem-sucedido
        }else{
            return "Usuário não encontrado!";
        }
    }

    public boolean isUsuarioAdmin(String id) {
        // Busca o usuário pelo ID no banco de dados
        Usuario usuario = usuarioRepository.findById(UUID.fromString(id)).orElse(null);

        // Verifica se o usuário existe e se é um administrador
        return usuario != null && usuario.isAdmin();
    }
}
