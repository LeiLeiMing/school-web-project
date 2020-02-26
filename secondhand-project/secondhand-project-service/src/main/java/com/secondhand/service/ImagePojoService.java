package com.secondhand.service;

import com.secondhand.mapper.ImageMapper;
import com.secondhand.project.pojo.ImagePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by LeiMing on 2020/2/6 10:27
 */
@Service
public class ImagePojoService {

    @Autowired
    private ImageMapper imageMapper;

    /**
     * 获取首页的轮播图
     * @return
     */
    public List<ImagePojo> getIndexLunboImage() {
        Example example = new Example(ImagePojo.class);
        //条件查询:indexlunbo为1的数据
        example.createCriteria()
                .andEqualTo("indexlunbo",1);
        return imageMapper.selectByExample(example);
    }
}
