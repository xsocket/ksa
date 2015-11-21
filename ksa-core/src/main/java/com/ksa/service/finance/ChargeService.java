package com.ksa.service.finance;

import java.util.List;

import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;

/**
 * 费用数据管理服务接口。
 *
 * @author 麻文强
 *
 * @since v0.0.1
 */
public interface ChargeService {

    /**
     * 获取托单对应的费用信息。
     * @param bookingNoteId 托单标识
     * @return 收入与支出费用混合在一起的费用列表。
     * @throws RuntimeException
     */
    List<Charge> loadBookingNoteCharges( String bookingNoteId ) throws RuntimeException;
    
    /**
     * 获取托单对应的费用信息。
     * @param bookingNoteId 托单标识
     * @return 收入与支出费用混合在一起的费用列表。
     * @throws RuntimeException
     */
    List<Charge> loadBookingNoteCharges( String bookingNoteId, int direction, int nature ) throws RuntimeException;
    
    
    /**
     * 获取托单对应的费用信息，并将收入与支出分别放入传入的收入列表和支出列表中。
     * @param bookingNoteId 托单标识
     * @param incomes   存放查询所获取的收入费用列表
     * @param expenses  存放查询所获取的支出费用列表
     * @return 收入与支出费用混合在一起的费用列表。
     * @throws RuntimeException
     */
    List<Charge> loadBookingNoteCharges( String bookingNoteId, List<Charge> incomes, List<Charge> expenses ) throws RuntimeException;
    
    /**
     * 保存托单对应的费用数据。
     * @param note 托单
     * @param incomes 新的收入费用列表
     * @param expenses 新的支出费用列表
     * @return 保存后的收入与支出费用混合在一起的费用列表。
     * @throws RuntimeException
     */
    /*List<Charge> saveBookingNoteCharges( BookingNote note, List<Charge> incomes, List<Charge> expenses ) throws RuntimeException;*/
    
    /**
     * 保存托单对应的费用数据。
     * @param note 托单
     * @param incomes 新的收入费用列表
     * @param expenses 新的支出费用列表
     * @return 保存后的收入与支出费用混合在一起的费用列表。
     * @throws RuntimeException
     */
    @Deprecated
    List<Charge> saveBookingNoteCharges( BookingNote note, List<Charge> incomes, List<Charge> expenses, int nature ) throws RuntimeException;
    // 境内外统一管理
    
    /**
     * 保存托单对应的费用数据。
     * @param note 托单
     * @param incomes 新的收入费用列表
     * @param expenses 新的支出费用列表
     * @return 保存后的收入与支出费用混合在一起的费用列表。
     * @throws RuntimeException
     */
    List<Charge> saveBookingNoteCharges( BookingNote note, List<Charge> incomes, List<Charge> expenses ) throws RuntimeException;
    
    /**
     * 保存托单对应的费用数据。
     * @param note 托单
     * @param incomes 新的收入费用列表
     * @param expenses 新的支出费用列表
     * @return 保存后的收入与支出费用混合在一起的费用列表。
     * @throws RuntimeException
     */
    List<Charge> saveBookingNoteCharges( BookingNote note, List<Charge> charges, int direction, int nature ) throws RuntimeException;
    
    /**
     * 更新托单的业务状态
     * @param note 托单业务数据，其中包含了托单的标识 和 新的状态标识
     * @return 更新后的托单
     * @throws RuntimeException
     */
    BookingNote updateBookingNoteChargeState( BookingNote note ) throws RuntimeException;
    
    BookingNote updateBookingNoteChargeState( BookingNote note, int direction, int nature ) throws RuntimeException;
    
    BookingNote updateBookingNoteChargeState( BookingNote note, int nature ) throws RuntimeException;

    List<Charge> loadBookingNoteCharges( String bookingNoteId, List<Charge> incomes, List<Charge> expenses, int nature )
            throws RuntimeException;
}
