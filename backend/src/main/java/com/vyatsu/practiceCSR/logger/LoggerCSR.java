package com.vyatsu.practiceCSR.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LoggerCSR {
    private static final Logger logger = LoggerFactory.getLogger(LoggerCSR.class);

    public static void createDebugMsg(enumDebugLog typeLog, Long manageFromId, Long controlToId){
        switch (typeLog){
            case CREATE_USER -> logger.debug("Создание пользователя #" + controlToId + " администратором #" + manageFromId);
            case CREATE_REPORT -> logger.debug("Создание отчета #" + controlToId + " администратором #" + manageFromId);
            case CREATE_SERVICE -> logger.debug("Создание услуги #" + controlToId + " администратором #" + manageFromId);
            case CREATE_TEMPLATE -> logger.debug("Создание шаблона #" + controlToId + " администратором #" + manageFromId);

            case UPDATE_USER -> logger.debug("Редактирование данных пользователя #" + controlToId + " администратором #" + manageFromId);
            case UPDATE_TEMPLATE -> logger.debug("Редактирование шаблона #" + controlToId + " администратором #" + manageFromId);
            case COPY_REPORT -> logger.debug("Копирование в отчет #" + controlToId + " данных отчета #" + manageFromId);

            case DROP_USER -> logger.debug("Удаление пользователя #" + controlToId + " администратором #" + manageFromId);
            case DROP_SERVICE -> logger.debug("Удаление услуги #" + controlToId + " администратором #" + manageFromId);
            case DROP_TEMPLATE -> logger.debug("Удаление шаблона #" + controlToId + " администратором #" + manageFromId);

            case SAVE_REPORT -> logger.debug("Сохранение отчета #" + controlToId + " пользователем #" + manageFromId);
            case COMPLETE_REPORT -> logger.debug("Завершение отчета #" + controlToId + " пользователем #" + manageFromId);
            case COMPLETE_TIMEOUT_REPORT -> logger.debug("Несвоевременное завершение отчета #" + controlToId + " пользователем #" + manageFromId);
        }
    }

    public static void createInfoMsg(enumInfoLog typeLog, Long manageFromId, Long controlToId){
        switch (typeLog){
            case AUTH_USER -> logger.info("Успешная авторизация пользователя #" + controlToId);
            case VIEW_USER_REPORT -> logger.info("Просмотр отчета #" + controlToId + " администратором #" + manageFromId);
            case GENERATE_SUMMARY_REPORT -> logger.info("Создание итогового отчета по шаблону #" + controlToId + " администратором #" + manageFromId);
        }
    }

    public static void createErrMsg(enumErrLog typeLog, Long controlToId){
        if (Objects.requireNonNull(typeLog) == enumErrLog.BAD_AUTH_USER) {
            logger.info("Неуспешная авторизация пользователя #" + controlToId);
        }
    }
}
