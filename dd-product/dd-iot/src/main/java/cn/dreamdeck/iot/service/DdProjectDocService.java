package cn.dreamdeck.iot.service;



import cn.dreamdeck.model.iot.DdProjectDoc;

import java.util.List;

public interface DdProjectDocService {

    /**
     * list
     *
     * @param docType
     * @return
     */
    List<DdProjectDoc> docList(Integer docType, Integer projectId);

    /**
     * 添加
     *
     * @param ddProjectDoc
     * @return
     */
    Boolean saveProjectDoc(DdProjectDoc ddProjectDoc);


    /**
     * 删除分组
     *
     * @param ddProjectDoc
     * @return
     */
    Boolean deleteProjectDoc(DdProjectDoc ddProjectDoc);
}
