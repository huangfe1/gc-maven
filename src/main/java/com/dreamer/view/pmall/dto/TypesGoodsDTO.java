package com.dreamer.view.pmall.dto;


import java.util.List;

public class TypesGoodsDTO {

	private Integer typeId;

	private String typeName;

	private List<MallGoodsDto> goodsDtos;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<MallGoodsDto> getGoodsDtos() {
        return goodsDtos;
    }

    public void setGoodsDtos(List<MallGoodsDto> goodsDtos) {
        this.goodsDtos = goodsDtos;
    }
}
