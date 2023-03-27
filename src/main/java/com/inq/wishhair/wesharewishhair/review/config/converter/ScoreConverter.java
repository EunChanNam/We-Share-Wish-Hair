package com.inq.wishhair.wesharewishhair.review.config.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.review.enums.Score;
import org.springframework.core.convert.converter.Converter;

public class ScoreConverter implements Converter<String, Score> {

    @Override
    public Score convert(String value) {
        switch (value) {
            case "0" -> {
                return Score.S0;
            }
            case "0.5" -> {
                return Score.S0H;
            }
            case "1.0" -> {
                return Score.S1;
            }
            case "1.5" -> {
                return Score.S1H;
            }
            case "2.0" -> {
                return Score.S2;
            }
            case "2.5" -> {
                return Score.S2H;
            }
            case "3.0" -> {
                return Score.S3;
            }
            case "3.5" -> {
                return Score.S3H;
            }
            case "4.0" -> {
                return Score.S4;
            }
            case "4.5" -> {
                return Score.S4H;
            }
            case "5.0" -> {
                return Score.S5;
            }
        }
        throw new WishHairException(ErrorCode.SCORE_MISMATCH);
    }
}
