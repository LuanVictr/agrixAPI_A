package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.FarmDto;
import com.betrybe.agrix.controller.dto.ResponseDto;
import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.service.FarmService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cria a classe FarmController, um RestController que ira
 * conter todas as funcoes do endpoint /farms.
 */
@RestController
@RequestMapping("/farms")
public class FarmController {

  private FarmService farmService;

  /**
   * Construtor da classe FarmController.
   *
   * @param farmService instancia da camada de servico recebida
   *                    por injecao de dependencia.
   */
  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  /**
   * Cria o endpoint POST /farms , onde é possivel criar e salvar
   * no banco de dados uma nova farm.
   *
   * @param farmDto Recebe uma nova farm no modelo Dto
   * @return retorna uma ResponseEntity com as informacoes
   *     da nova farm criada
   */
  @PostMapping
  public ResponseEntity<Farm> createFarm(@RequestBody FarmDto farmDto) {
    Farm farmToSave = farmDto.toFarm();
    Farm createdFarm = this.farmService.createFarm(farmToSave);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdFarm);
  }

  /**
   * Cria a rota GET /farms que retorna todas as farms registradas.
   *
   * @return retorna uma lista com todas as farms registradas
   */
  @GetMapping
  public ResponseEntity<List<Farm>> getAllFarms() {
    List<Farm> allFarms = this.farmService.getFarms();
    return ResponseEntity.status(HttpStatus.OK).body(allFarms);
  }

  /**
   * Cria a rota GET /farms/id que retorna a farm buscada pelo id.
   *
   * @return retorna uma Farm do banco de dados.
   */
  @GetMapping("/{farmId}")
  public ResponseEntity<ResponseDto<Farm>> getFarmById(@PathVariable Long farmId) {
    Optional<Farm> farmToFound = this.farmService.getFarmById(farmId);

    if (farmToFound.isEmpty()) {
      ResponseDto<Farm> responseNotFound = new ResponseDto<>(
          "Fazenda não encontrada!", null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseNotFound);
    }

    Farm farmFound = farmToFound.get();
    ResponseDto<Farm> responseDto = new ResponseDto<Farm>(
        "Fazenda encontrada com sucesso", farmFound);
    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

}
