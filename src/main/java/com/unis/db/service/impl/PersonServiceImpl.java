package com.unis.db.service.impl;

import com.unis.db.common.utils.DateUtils;
import com.unis.db.model.PersonStructured;
import com.unis.db.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author xuli
 * @date 2019/4/22
 */
@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    /**
     * 人脸卡口 共103个，
     * 例子:50010500001311000xxx
     */
    private static final String DEVICE_PREFIX = "50010500001311000";
    /**
     * 性别代码
     */
    private static final int[] GENDER_TYPE = {0, 1, 2, 9};
    /**
     * 颜色种类
     */
    private static final int[] COLOR = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 99};
    /**
     * 上衣款式
     */
    private static final int[] COAT_STYLE = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 99};
    /**
     * 裤子款式
     */
    private static final int[] TROUSERS_STYLE = {1, 2, 3, 4, 5, 6, 7, 8, 99};
    private PersonStructured person = new PersonStructured();

    private Random random = new Random();

    @Override
    public String makePersonData(long passTime, long recordID, boolean partitionState) {
        person.setRecordID(recordID);
        person.setPersonID(Integer.toString(random.nextInt(100000000)));
        //50010500001311000400-499
        person.setDeviceID(DEVICE_PREFIX + (random.nextInt(100) + 400));
        person.setSnapTimestamp(passTime);
        person.setLeftTopX(0.0000);
        person.setLeftTopY(0.0000);
        person.setRightBtmX(0.0000);
        person.setRightBtmY(0.0000);
        person.setPersonAppearTime(passTime);
        person.setPersonDisappearTime(passTime + 1000);
        person.setGenderCode(GENDER_TYPE[random.nextInt(4)]);
        person.setAgeUpLimit(random.nextInt(45) + 35);
        person.setAgeLowerLimit(person.getAgeUpLimit() - 15 - random.nextInt(10));
        person.setEthicCode(random.nextInt(56) + 1);
        person.setAccompanyNumber(random.nextInt(2) + 1);
        person.setSkinColor(1);
        person.setHairStyle(random.nextInt(12) + 1);
        person.setHairColor(COLOR[random.nextInt(COLOR.length)]);
        person.setFaceStyle(1);
        person.setFacialFeature("");
        person.setPhysicalFeature("");
        person.setUmbrellaColor(COLOR[random.nextInt(COLOR.length)]);
        person.setRespiratorColor(11);
        person.setCapStyle(1);
        person.setCapColor(COLOR[random.nextInt(COLOR.length)]);
        person.setGlassStyle(random.nextInt(10) + 1);
        person.setGlassColor(COLOR[random.nextInt(COLOR.length)]);
        person.setScarfColor(COLOR[random.nextInt(COLOR.length)]);
        person.setBagStyle(random.nextInt(13) + 1);
        person.setBagColor(COLOR[random.nextInt(COLOR.length)]);
        person.setCoatStyle(COAT_STYLE[random.nextInt(COAT_STYLE.length)]);
        person.setCoatColor(COLOR[random.nextInt(COLOR.length)]);
        person.setTrousersStyle(TROUSERS_STYLE[random.nextInt(TROUSERS_STYLE.length)]);
        person.setTrousersColor(COLOR[random.nextInt(COLOR.length)]);
        person.setShoesStyle(random.nextInt(13) + 1);
        person.setShoesColor(COLOR[random.nextInt(COLOR.length)]);
        person.setHeightUpLimit(0);
        person.setHeightLowerLimit(0);
        person.setBodyType(1);
        person.setGesture(random.nextInt(11) + 1);
        person.setStatus(random.nextInt(4) + 1);
        person.setBodyFeature(0);
        person.setHabitualMovement(random.nextInt(19) + 1);
        person.setBehavior(random.nextInt(7) + 1);
        person.setBehaviorDescription("");
        person.setAppendant(random.nextInt(10) + 1);
        person.setAppendantDescription("");
        person.setCoatLength(random.nextInt(3) + 1);
        person.setTrousersLen(random.nextInt(2) + 1);
        person.setIsDriver(random.nextInt(2));
        person.setVehicleClass(0);
        person.setIsForeigner(random.nextInt(2));
        person.setPassportType(random.nextInt(51) + 11);
        person.setImmigrantTypeCode("");
        person.setBodySpecialMark(0);
        person.setImageReliability(0);
        person.setImageUrl("/picture/2018-08-17T08-29-20Z/1534495880605_3865.jpg");
        person.setImageUrlPart("");
        person.setStorageTime(0);
        String data = person.toString();
        if (partitionState) {
            data = data + "," + DateUtils.longToString(person.getSnapTimestamp(), "yyyy-MM-dd");
        }
        return data;
    }
}
