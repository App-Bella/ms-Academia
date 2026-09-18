package com.pontebella.ms_academia.exception;

// Para violaciones de reglas de negocio: cupo lleno, progreso inválido,
// matrícula duplicada, etc. (no es un 404, es un 400/409).
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}