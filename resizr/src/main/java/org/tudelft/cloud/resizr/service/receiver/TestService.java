package org.tudelft.cloud.resizr.service.receiver;

import org.springframework.stereotype.Service;
import org.tudelft.cloud.resizr.model.Test;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    public List<Test> getTestList() {
        Test t1 = new Test();
        t1.setId("id1");
        t1.setName("name1");
        t1.setValue("value1");

        Test t2 = new Test();
        t2.setId("id2");
        t2.setName("name2");
        t2.setValue("value2");

        List<Test> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);

        return list;
    }

}
