package com.vyatsu.practiceCSR.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerCSR {
    private static final Logger logger = LoggerFactory.getLogger(LoggerCSR.class);

    public static void createWarnMsg(EnumWarnLog typeLog, Long manageFromId, Long controlToId){
        switch (typeLog){
            case CREATE_USER -> logger.warn("Создание пользователя #" + controlToId + " администратором #" + manageFromId);
            case CREATE_REPORT -> logger.warn("Создание отчета #" + controlToId + " администратором #" + manageFromId);
            case CREATE_SERVICE -> logger.warn("Создание услуги #" + controlToId + " администратором #" + manageFromId);
            case CREATE_TEMPLATE -> logger.warn("Создание шаблона #" + controlToId + " администратором #" + manageFromId);

            case UPDATE_USER -> logger.warn("Редактирование данных пользователя #" + controlToId + " администратором #" + manageFromId);
            case UPDATE_TEMPLATE -> logger.warn("Редактирование шаблона #" + controlToId + " администратором #" + manageFromId);
            case COPY_REPORT -> logger.warn("Копирование в отчет #" + controlToId + " данных отчета #" + manageFromId);

            case DROP_USER -> logger.warn("Удаление пользователя #" + controlToId + " администратором #" + manageFromId);
            case DROP_SERVICE -> logger.warn("Удаление услуги #" + controlToId + " администратором #" + manageFromId);
            case DROP_TEMPLATE -> logger.warn("Удаление шаблона #" + controlToId + " администратором #" + manageFromId);

            case SAVE_REPORT -> logger.warn("Сохранение отчета #" + controlToId + " пользователем #" + manageFromId);
            case COMPLETE_REPORT -> logger.warn("Завершение отчета #" + controlToId + " пользователем #" + manageFromId);
            case COMPLETE_TIMEOUT_REPORT -> logger.warn("Несвоевременное завершение отчета #" + controlToId + " пользователем #" + manageFromId);

            case AUTH_USER -> logger.warn("Успешная авторизация пользователя #" + controlToId);
            case VIEW_USER_REPORT -> logger.warn("Просмотр отчета #" + controlToId + " администратором #" + manageFromId);
            case GENERATE_SUMMARY_REPORT -> logger.warn("Создание итогового отчета по шаблону #" + controlToId + " администратором #" + manageFromId);
            case BAD_AUTH_USER -> logger.warn("Неуспешная авторизация пользователя #" + controlToId);
        }
    }

    public static void createMsg(String msg){
        logger.warn(msg);
    }
}
