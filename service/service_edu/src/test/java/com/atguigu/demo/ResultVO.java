package com.atguigu.demo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.List;

@Data
public class ResultVO {
    private String log_id;
    private Integer words_result_num;
    private String words_result;
}
