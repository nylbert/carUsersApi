package com.car.users.api.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.car.users.api.domain.dto.CarDTO;

/**
 * Interface para definir os serviços relacionados a operações de carros.
 */
public interface ICarService {

    /**
     * Insere um novo carro associado a um usuário no sistema.
     *
     * @param carDTO os dados do carro a ser inserido
     * @param userId o ID do usuário proprietário do carro
     * @return o {@link CarDTO} do carro inserido
     */
    CarDTO insert(CarDTO carDTO, Integer userId);

    /**
     * Lista todos os carros de um usuário específico.
     *
     * @param userId o ID do usuário
     * @return uma lista de {@link CarDTO} pertencentes ao usuário
     */
    List<CarDTO> find(Integer userId);

    /**
     * Busca um carro específico de um usuário pelo ID do carro.
     *
     * @param id     o ID do carro
     * @param userId o ID do usuário proprietário do carro
     * @return o {@link CarDTO} do carro encontrado
     */
    CarDTO find(Integer id, Integer userId);

    /**
     * Deleta um carro específico de um usuário.
     *
     * @param id     o ID do carro a ser deletado
     * @param userId o ID do usuário proprietário do carro
     */
    void delete(Integer id, Integer userId);

    /**
     * Atualiza os dados de um carro existente de um usuário.
     *
     * @param id      o ID do carro a ser atualizado
     * @param userId  o ID do usuário proprietário do carro
     * @param carDTO  os novos dados do carro
     * @return o {@link CarDTO} atualizado
     */
    CarDTO update(Integer id, Integer userId, CarDTO carDTO);

    /**
     * Deleta todos os carros de um usuário específico.
     *
     * @param userId o ID do usuário cujos carros serão deletados
     */
    void delete(Integer userId);

    /**
     * Atualiza a imagem de um carro específico de um usuário.
     *
     * @param id          o ID do carro cuja imagem será atualizada
     * @param userId      o ID do usuário proprietário do carro
     * @param imageFile   o arquivo de imagem
     * @throws IOException se ocorrer um erro ao processar o arquivo de imagem
     */
    void updateCarImage(Integer id, MultipartFile imageFile) throws IOException;
    

    /**
     * Deleta todos os carros .
     *
     */
    void delete();

}
