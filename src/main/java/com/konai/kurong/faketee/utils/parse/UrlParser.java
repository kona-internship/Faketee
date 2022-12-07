package com.konai.kurong.faketee.utils.parse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class UrlParser {
    private final String PATH_COR = "corporation";

    public Long parseCor(HttpServletRequest request){
        Long corId = null;
        List<String> urlPathList = Arrays.asList(request.getRequestURI().split("/"));
        int index = urlPathList.indexOf(PATH_COR);
        if(urlPathList.size()>index+1) {
            corId = Long.parseLong(urlPathList.get(index + 1));
        }
        return corId;
    }
}
