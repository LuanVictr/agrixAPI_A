package com.betrybe.agrix.controller.dto;

/**
 * Dto da camada de resposta da aplicacao.
 *
 * @param message mensagem a ser enviada na resposta
 * @param info informação a ser enviada na resposta
 * @param <T> tipo generico para reaproveitamento da Dto
 */
public record ResponseDto<T>(String message, T info) {
}
