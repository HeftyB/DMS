package com.heftyb.dms.timekeeping.services;

import com.heftyb.dms.timekeeping.TimeSheet;

import java.util.List;

public interface TimeSheetService {
    List<TimeSheet> findAllTimeSheets();

    TimeSheet findTimeSheetById(long id);

    TimeSheet saveTimeSheet(TimeSheet timeSheet);

    void deleteTimeSheetById(long id);
}
