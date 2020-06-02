package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.DdDeviceControlChannel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceControlChannelService  extends IService<DdDeviceControlChannel> {

    /**
     * 通道控制器列表
     *
     * @param page
     * @param ddDeviceControlChannel
     * @return
     */
    IPage list(Page page, DdDeviceControlChannel ddDeviceControlChannel);

    /**
     * 添加通道控制器
     *
     * @param ddDeviceControlChannel
     * @return
     */
    Boolean saveChannel(DdDeviceControlChannel ddDeviceControlChannel);

    /**
     * 修改通道控制器
     *
     * @param ddDeviceControlChannel
     * @return
     */
    Boolean updateChannel(DdDeviceControlChannel ddDeviceControlChannel);

    /**
     * 删除通道控制器
     *
     * @param ddDeviceControlChannel
     * @return
     */
    Boolean deleteById(DdDeviceControlChannel ddDeviceControlChannel);
}
