package com.example.paymeintegration.service;

import com.example.paymeintegration.model.Card;
import com.example.paymeintegration.model.User;
import com.example.paymeintegration.model.dto.*;
import com.example.paymeintegration.repository.CardRepository;
import com.example.paymeintegration.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class Services {
    @Autowired
    Environment environment;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

//    public GeneralResultData createCard(ReqCardDto reqCardDto) {
//        String url = "/api";
//        ReqCardsCreateDto reqCardsCreateDto = new ReqCardsCreateDto();
//        reqCardsCreateDto.setId(1);
//        reqCardsCreateDto.setMethod("cards.create");
//        ReqParamsDto paramsDto = new ReqParamsDto();
//        paramsDto.setCard(reqCardDto);
//        paramsDto.setSave(true);
//        reqCardsCreateDto.setParams(paramsDto);
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("X-Auth", environment.getProperty("payme-auth"));
//            HttpEntity<?> req = new HttpEntity<>(reqCardsCreateDto, headers);
//            System.out.println(new Gson().toJson(req));
//            HttpEntity<ResAllResultDto> res = restTemplate.exchange(url, HttpMethod.POST, req, ResAllResultDto.class);
//
//            if (!Objects.isNull(res.getBody().getResult())) {
//                Optional<User> user = userRepository.findById(2L);
//                Card card = new Card();
//                card.setNumber(reqCardDto.getNumber());
//                card.setCardName(reqCardDto.getNumber());
//                card.setExpireDate(reqCardDto.getExpire());
//                card.setCurrencyCode("codi");
//                card.setToken(res.getBody().getResult().getCard().getToken());
//                card.setType(res.getBody().getResult().getCard().getType());
//                card.setRecurrent(res.getBody().getResult().getCard().getRecurrent());
//                card.setVerify(res.getBody().getResult().getCard().getVerify());
//                card.setUser(user.get());
//                cardRepository.save(card);
//                return new GeneralResultData(true, null, "Muvofaqiyatli saqlandi", null);
//            } else {
//                return new GeneralResultData(false, 2, "Bunday karta topilmadi", res);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new GeneralResultData(false, 3, "Boglanishda xatolik : " + e.getMessage(), null);
//        }
//    }


    public GeneralResultData createCard(ReqCardDto reqCardDto) {
        String url = "/api";
        ReqCardsCreateDto reqCardsCreateDto = new ReqCardsCreateDto();
        reqCardsCreateDto.setId(1);
        reqCardsCreateDto.setMethod("cards.create");
        ReqParamsDto paramsDto = new ReqParamsDto();
        paramsDto.setCard(reqCardDto);
        paramsDto.setSave(true);
        reqCardsCreateDto.setParams(paramsDto);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Auth", environment.getProperty("payme-auth"));
            HttpEntity<?> req = new HttpEntity<>(reqCardsCreateDto, headers);
            System.out.println(new Gson().toJson(req));
            HttpEntity<Object> res = restTemplate.exchange(url, HttpMethod.POST, req, Object.class);
            Map<String, Object> map = new ObjectMapper().convertValue(res.getBody(), Map.class);
            if (map.containsKey("result")) {
                Map<String, ResCardDto> map2 = new ObjectMapper().convertValue(map.get("result"), new TypeReference<Map<String, ResCardDto>>() {
                });
//                System.out.println(  map2.get("result"));
                if (!Objects.isNull(map2.get("card"))) {
                    Optional<User> user = userRepository.findById(2L);
                    Card card = new Card();
                    card.setNumber(reqCardDto.getNumber());
                    card.setCardName(reqCardDto.getNumber());
                    card.setExpireDate(reqCardDto.getExpire());
                    card.setCurrencyCode("codi");
                    System.out.println(map2.get("card").getToken());
                    card.setToken(map2.get("card").getToken());
                    card.setType(map2.get("card").getType());
                    card.setRecurrent(map2.get("card").getRecurrent());
                    card.setVerify(map2.get("card").getVerify());
                    card.setUser(user.get());
                    cardRepository.save(card);
                    return new GeneralResultData(true, null, "Muvofaqiyatli saqlandi", null);
                }
            } else {
                Map<String, Object> map2 = new ObjectMapper().convertValue(map.get("error"), new TypeReference<Map<String, Object>>() {
                });
                return new GeneralResultData(false, (Integer) map2.get("code"), (String) map2.get("message"), map2.get("data"));
            }
            return new GeneralResultData(false, 2, "Bunday karta topilmadi", res);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new GeneralResultData(false, 3, "Boglanishda xatolik : " + e.getMessage(), null);
        }
    }

    public GeneralResultData removeCardByToken(String token) {
        String url = "/api";
        Optional<Card> card = cardRepository.findByToken(token);
        if (card.isPresent()) {
            HashMap<String, Object> map = new HashMap<>();

            map.put("id", card.get().getUser().getId());
            map.put("method", "cards.remove");

            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("token", token);

            map.put("params", map2);

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Auth", environment.getProperty("payme-auth"));
            HttpEntity<?> req = new HttpEntity<>(map, headers);
            HttpEntity<Object> res = restTemplate.exchange(url, HttpMethod.POST, req, Object.class);
            Map<String, Object> map3 = new ObjectMapper().convertValue(res.getBody(), Map.class);

            if (map3.containsKey("result")) {
                Map<String, Object> map4 = new ObjectMapper().convertValue(map3.get("result"), new TypeReference<Map<String, Object>>() {
                });
                return new GeneralResultData(true, null, "Muvafaqiyatli o'chirildi", null);
            } else {
                Map<String, Object> map4 = new ObjectMapper().convertValue(map3.get("error"), new TypeReference<Map<String, Object>>() {
                });
                return new GeneralResultData(false, (Integer) map4.get("code"), (String) map4.get("message"), map4.get("data"));
            }
        } else {
            return new GeneralResultData(false, 2, "Malumot topilmadi", null);
        }
    }
}
