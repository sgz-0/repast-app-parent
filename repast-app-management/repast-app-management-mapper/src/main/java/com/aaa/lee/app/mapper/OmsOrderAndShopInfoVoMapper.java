package com.aaa.lee.app.mapper;

import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OmsOrderAndShopInfoVoMapper extends Mapper<OmsOrderAndShopInfoVo> {
  List<OmsOrderAndShopInfoVo> getOrderAndShopInfo(Long memberId);
}
