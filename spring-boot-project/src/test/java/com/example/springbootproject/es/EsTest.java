package com.example.springbootproject.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.example.springbootproject.SpringBootProjectApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //         ????????????           ??????BUG           ????????????              //
 * //          ??????:                                                  //
 * //                 ?????????????????????????????????????????????;                      //
 * //                 ?????????????????????????????????????????????.                      //
 * //                 ?????????????????????????????????????????????;                      //
 * //                 ?????????????????????????????????????????????.                      //
 * //                 ?????????????????????????????????????????????;                      //
 * //                 ?????????????????????????????????????????????.                      //
 * //                 ?????????????????????????????????????????????;                      //
 * //                 ??????????????????????????????????????????????                      //
 * ////////////////////////////////////////////////////////////////////
 *
 * @date : 2022/6/19 16:34
 * @author: linzhou
 * @description : EsTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringBootProjectApplication.class)
@AutoConfigureMockMvc
public class EsTest{


    @Autowired
    private ElasticsearchClient client;

    /**
     * ????????????
     *
     * @throws IOException
     */
    @Test
    public void createTest() throws IOException {

        //?????????RestHighLevelClient????????????
        CreateIndexResponse indexResponse = client.indices().create(c -> c.index("user"));
    }

    /**
     * ????????????????????????
     *
     * @throws IOException
     */
    @Test
    public void existsTest() throws IOException {
        BooleanResponse booleanResponse = client.indices().exists(e -> e.index("user"));
        System.out.println(booleanResponse.value());
    }

    /**
     * ????????????
     *
     * @throws IOException
     */
    @Test
    public void queryTest() throws IOException {
        GetIndexResponse getIndexResponse = client.indices().get(i -> i.index("user"));
        System.out.println(getIndexResponse.result());
    }

    /**
     * ????????????
     *
     * @throws IOException
     */
    @Test
    public void deleteTest() throws IOException {
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(d -> d.index("user"));
        System.out.println(deleteIndexResponse.acknowledged());
    }

    @Test
    public void addDocumentTest() throws IOException {

        User user = new User("user1", 10);
        IndexResponse indexResponse = client.index(i -> i
                .index("user")

                //??????id
                .id("1")

                //??????user??????
                .document(user));
        System.out.println(indexResponse.index());
    }
    @Test
    public void updateDocumentTest() throws IOException {
        UpdateResponse<User> updateResponse = client.update(u -> u
                        .index("user")
                        .id("1")
                        .doc(new User("user2", 13))
                , User.class);
    }

    @Test
    public void existDocumentTest() throws IOException {
        BooleanResponse indexResponse = client.exists(e -> e.index("user").id("1"));
        System.out.println(indexResponse.value());
    }
    @Test
    public void getDocumentTest() throws IOException {
        GetResponse<User> getResponse = client.get(g -> g
                        .index("user")
                        .id("2")
                , User.class
        );
        System.out.println(getResponse.source());
    }
    @Test
    public void deleteDocumentTest() throws IOException {
        DeleteResponse deleteResponse = client.delete(d -> d
                .index("user")
                .id("1")
        );
        System.out.println(deleteResponse.id());
    }

    @Test
    public void bulkTest() throws IOException {
        List<User> userList = new ArrayList<>();
        userList.add(new User("user1", 11));
        userList.add(new User("user2", 12));
        userList.add(new User("user3", 13));
        userList.add(new User("user4", 14));
        userList.add(new User("user5", 15));
        List<BulkOperation> bulkOperationArrayList = new ArrayList<>();
        //???????????????bulk???
        for(User user : userList){
            bulkOperationArrayList.add(BulkOperation.of(o->o.index(i->i.document(user))));
        }

        BulkResponse bulkResponse = client.bulk(b -> b.index("user")
                .operations(bulkOperationArrayList));

    }



    @Test
    public void searchTest() throws IOException {
        SearchResponse<User> search = client.search(s -> s
                .index("user")
                //??????name????????????hello???document(??????????????????????????????)
                .query(q -> q
                        .term(t -> t
                                .field("name")
                                .value(v -> v.stringValue("lin"))
                        ))
                //?????????????????????0???????????????3???document
                .from(0)
                .size(3)
                //???age????????????
                .sort(f->f.field(o->o.field("age").order(SortOrder.Desc))),User.class
        );
        long value = search.hits().total().value();
        System.out.println("total:"+value);
        for (Hit<User> hit : search.hits().hits()) {
            System.out.println(hit.source());
        }
    }

}
