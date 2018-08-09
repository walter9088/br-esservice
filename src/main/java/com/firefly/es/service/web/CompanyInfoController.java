package com.firefly.es.service.web;

import com.firefly.es.service.dto.CompanyDto;
import com.firefly.es.service.dto.EntityObject;
import com.firefly.es.service.handler.EsCompanyInfoService;
import com.firefly.es.service.handler.EsIndexHandler;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CompanyInfoController {



    @Autowired
    private EsCompanyInfoService service;


    private static final Logger log = LoggerFactory.getLogger(SampleController.class);

    /**
     *
     * @return
     */
    @RequestMapping("/cs.html")
    @ResponseBody
    String createIndexSchema() {

        return service.createIndexSchemaHandler();

    }


    @ResponseBody
    @RequestMapping(value = "/store.html",method = RequestMethod.POST)
    String store(@RequestBody CompanyDto dto){
        return service.storeDocument(dto);

    }

    @ResponseBody
    @RequestMapping("/query.html")
    String queryTerm(@RequestParam(value = "key",required = false) String key,@RequestParam(value = "in",required = false) String industry,
                     @RequestParam(value = "start" ,required = false) int start,@RequestParam(value = "end",required = false) int end){

        return service.queryByKeyword(key,industry,start,end);

    }

    @ResponseBody
    @RequestMapping("/all.html")
    String queryAll(){

        return service.queryAll();

    }
}
