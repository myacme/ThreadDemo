package juc.conditionThread;

import lombok.Getter;
/**
 * @author MyAcme
 */

@Getter
public enum CountryEnum {
    ONE(1, "齐国"), TWO(2, "楚国"), THREE(3, "燕国"),
    FOUR(4, "赵国"), FIVE(5, "魏国"), SIX(6, "韩国");

    private final Integer retCode;
    private final String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEachCountryEnum(int index) {
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum countryEnum : myArray) {
            if (index == countryEnum.retCode) {
                return countryEnum;
            }
        }
        return null;
    }
}
