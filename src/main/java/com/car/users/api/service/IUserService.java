package com.car.users.api.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.domain.model.User;

/**
 * Interface para definir os serviços relacionados a operações de usuário.
 */
public interface IUserService {

    /**
     * Busca um usuário pelo login.
     *
     * @param login o login do usuário
     * @return o usuário encontrado
     */
    User find(String login);

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return uma lista de {@link UserDTO}
     */
    List<UserDTO> find();

    /**
     * Busca um usuário pelo ID.
     *
     * @param id o ID do usuário
     * @return o {@link UserDTO} do usuário encontrado
     */
    UserDTO find(Integer id);

    /**
     * Insere um novo usuário no sistema.
     *
     * @param userDTO os dados do usuário a ser inserido
     * @return o {@link UserDTO} do usuário inserido
     */
    UserDTO insert(UserDTO userDTO);

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param id      o ID do usuário a ser atualizado
     * @param userDTO os novos dados do usuário
     * @return o {@link UserDTO} atualizado
     */
    UserDTO update(Integer id, UserDTO userDTO);

    /**
     * Deleta um usuário pelo ID.
     *
     * @param id o ID do usuário a ser deletado
     */
    void delete(Integer id);

    /**
     * Atualiza um usuário existente no sistema. Este método é utilizado
     * principalmente para atualizações que não envolvem o DTO, como atualizações
     * de estado internas.
     *
     * @param user o usuário a ser atualizado
     */
    void update(User user);

    /**
     * Atualiza a imagem de um usuário.
     *
     * @param id          o ID do usuário cuja imagem será atualizada
     * @param imageFile   o arquivo de imagem
     * @throws IOException se ocorrer um erro ao processar o arquivo de imagem
     */
    void updateUserImage(Integer id, MultipartFile imageFile) throws IOException;

}
