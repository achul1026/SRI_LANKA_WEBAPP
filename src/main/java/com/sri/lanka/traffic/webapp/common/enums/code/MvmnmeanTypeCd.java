package com.sri.lanka.traffic.webapp.common.enums.code;

import com.sri.lanka.traffic.webapp.common.converter.EnumConverter;
import com.sri.lanka.traffic.webapp.common.util.CommonUtils;

import lombok.Getter;

@Getter
public enum MvmnmeanTypeCd implements CommonEnumType<String> {
	
	MCL("MTC001","enums.MvmnmeanTypeCd.MCL","/images/counting_img01.png"),
	TWL("MTC002","enums.MvmnmeanTypeCd.TWL","/images/counting_img02.png"),
	CAR("MTC003","enums.MvmnmeanTypeCd.CAR","/images/counting_img03.png"),
	VAN("MTC004","enums.MvmnmeanTypeCd.VAN","/images/counting_img04.png"),
	MBU("MTC005","enums.MvmnmeanTypeCd.MBU","/images/counting_img05.png"),
	LBU("MTC006","enums.MvmnmeanTypeCd.LBU","/images/counting_img06.png"),
	LGV("MTC007","enums.MvmnmeanTypeCd.LGV","/images/counting_img07.png"),
	MG1("MTC008","enums.MvmnmeanTypeCd.MG1","/images/counting_img08.png"),
	MG2("MTC009","enums.MvmnmeanTypeCd.MG2","/images/counting_img09.png"),
	HG3("MTC010","enums.MvmnmeanTypeCd.HG3","/images/counting_img10.png"),
	AG3("MTC011","enums.MvmnmeanTypeCd.AG3","/images/counting_img11.png"),
	AG4("MTC012","enums.MvmnmeanTypeCd.AG4","/images/counting_img11.png"),
	AG5("MTC013","enums.MvmnmeanTypeCd.AG5","/images/counting_img11.png"),
	AG6("MTC014","enums.MvmnmeanTypeCd.AG6","/images/counting_img11.png"),
	FVH("MTC015","enums.MvmnmeanTypeCd.FVH","/images/counting_img12.png");
	
	private String code; 
	private String name;
	private String imgPath;
	
	MvmnmeanTypeCd(String code, String name, String imgPath) {
		this.code = code;
		this.name = name;
		this.imgPath = imgPath;
	}
	
	@Override
    public String getCode() {
        return code;
    }
	
	@Override
	public String getName() {
		return CommonUtils.getMessage(name);
	}
	
	public static class Converter extends EnumConverter<MvmnmeanTypeCd, String> {
        public Converter() {
            super(MvmnmeanTypeCd.class);
        }
    }
}
