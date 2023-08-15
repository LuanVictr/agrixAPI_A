package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.CropResponseDto;
import com.betrybe.agrix.controller.dto.CropsDto;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.service.CropService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller de todos os metodos da rota /crops.
 */
@RestController
@RequestMapping("/crops")
public class CropController {
  private CropService cropService;

  /**
   * Construtor do controller CropController.
   *
   * @param cropService recebe a camada de servico por
   *                    injecao de dependencia.
   */
  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }

  /**
   * Mapea a rota GET /crops com a funcao de retornar uma lista com
   * todas as plantacoes existentes no banco.
   *
   * @return retorna uma lista de CropResponseDto de todas as plantacoes.
   */
  @GetMapping
  public ResponseEntity<List<CropResponseDto>> getAllCrops() {

    List<Crop> allCrops = this.cropService.getAllCrops();

    List<CropResponseDto> cropsResponse = allCrops.stream()
        .map(crop -> new CropResponseDto(crop.getId(), crop.getName(),
        crop.getPlantedArea(), crop.getFarm().getId()))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(cropsResponse);
  }
}
