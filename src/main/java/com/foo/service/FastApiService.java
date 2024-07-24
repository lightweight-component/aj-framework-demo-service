package com.foo.service;

import com.ajaxjs.data.crud.CRUD_Service;
import com.ajaxjs.data.crud.FastCRUD;
import com.ajaxjs.data.crud.FastCRUD_Service;
import com.ajaxjs.data.crud.TableModel;
import com.foo.controller.FastApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FastApiService extends FastCRUD_Service implements FastApiController {
    @Autowired
    private CRUD_Service crudService;

    @Override
    public void init() {
        if (!isInit) {
            // 定义表的 CRUD
            FastCRUD<Map<String, Object>, Long> employees = new FastCRUD<>();
            employees.setDao(crudService);
            employees.setTableName("employees");

            // 列表排序按照 hire_date 字段排序，默认是 create_date，现改之
            TableModel tableModel = new TableModel();
            tableModel.setCreateDateField("hire_date");
            tableModel.setHasIsDeleted(false);

            employees.setListOrderByDate(true);
            employees.setTableModel(tableModel);

            namespaces.put("employees", employees);
            isInit = true;
        }

    }
}
