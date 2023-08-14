package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.CropResponseDto;
import com.betrybe.agrix.controller.dto.CropsDto;
import com.betrybe.agrix.controller.dto.FarmDto;
import com.betrybe.agrix.controller.dto.ResponseDto;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.service.CropService;
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
  private CropService cropService;

  /**
   * Construtor da classe FarmController.
   *
   * @param farmService instancia da camada de servico recebida
   *                    por injecao de dependencia.
   * @param cropService instancia da camada de servico crops recebida
   *                    por injecao de dependencia.
   */
  @Autowired
  public FarmController(FarmService farmService, CropService cropService) {
    this.farmService = farmService;
    this.cropService = cropService;
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

  /**
   * Rota POST /farmid/crops que salva uma crop em uma farm especificada.
   *
   * @param farmId id da Farm
   * @param cropsDto camada Dto para a criacao de crops
   * @return retorna uma CropResponseDto com somente as informacoes da Crop
   *     omitindo informacoes da farm.
   */
  @PostMapping("/{farmId}/crops")
  public ResponseEntity<ResponseDto> createCropByFarmId(@PathVariable Long farmId, @RequestBody
      CropsDto cropsDto) {
    Optional<Farm> farmToSave = this.farmService.getFarmById(farmId);

    if (farmToSave.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          new ResponseDto("Fazenda não encontrada!", null));
    }

    Farm farmFound = farmToSave.get();
    Crop cropToSave = cropsDto.toCrop(farmFound);
    Crop cropSaved = this.cropService.saveCropByFarmId(cropToSave);
    CropResponseDto cropResponseDto = new CropResponseDto(
        cropSaved.getId(), cropSaved.getName(),
        cropSaved.getPlantedArea(), cropSaved.getFarm().getId());
    ResponseDto okResponse = new ResponseDto("Plantacao registrada", cropResponseDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(okResponse);
  }

}
