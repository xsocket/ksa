package com.ksa.service.logistics;

import com.ksa.model.logistics.BookingNote;

/**
 * 托单数据管理服务接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface BookingNoteService {

    /**
     * 根据提供了托单类型，获取一个新的空白托单。
     * @param type 托单类型
     * @return 新的空白托单
     * @throws RuntimeException 所提供的托单类型不存在
     */
    BookingNote getNewBookingNote( String type ) throws RuntimeException;
    
    /**
     * 根据标识获取托单数据。
     * @param id 托单标识
     * @return 标识为 id 的托单
     * @throws RuntimeException
     */
    BookingNote loadBookingNoteById( String id ) throws RuntimeException;
    
    /**
     * 创建新的托单。
     * @param note 将要被创建的托单
     * @return 创建成功后的托单
     * @throws RuntimeException
     */
    BookingNote createBookingNote( BookingNote note ) throws RuntimeException;
    
    /**
     * 更新托单。
     * @param note 将要被更新的托单
     * @return 更新成功后的托单
     * @throws RuntimeException
     */
    BookingNote modifyBookingNote( BookingNote note ) throws RuntimeException;
    
    /**
     * 移除托单。
     * @param note 将要被移除的托单
     * @return 移除成功后的托单
     * @throws RuntimeException
     */
    BookingNote removeBookingNote( BookingNote note ) throws RuntimeException;
    
    /**
     * 变更托单类型。
     * @param note 将要被变更类型的托单
     * @return 变更类型成功后的托单
     * @throws RuntimeException
     */
    BookingNote changeBookingNoteType( BookingNote note ) throws RuntimeException;
}
