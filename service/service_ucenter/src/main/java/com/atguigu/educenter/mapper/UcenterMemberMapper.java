package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-10-24
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * @params day
     * @desc 统计某一天的注册人数
     * */
    Integer selectRegisterCount(@Param("day") String day);
}
