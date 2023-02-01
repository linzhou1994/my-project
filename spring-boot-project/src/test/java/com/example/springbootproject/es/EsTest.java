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
 * //         佛祖保佑           永无BUG           永不修改              //
 * //          佛曰:                                                  //
 * //                 写字楼里写字间，写字间里程序员;                      //
 * //                 程序人员写程序，又拿程序换酒钱.                      //
 * //                 酒醒只在网上坐，酒醉还来网下眠;                      //
 * //                 酒醉酒醒日复日，网上网下年复年.                      //
 * //                 但愿老死电脑间，不愿鞠躬老板前;                      //
 * //                 奔驰宝马贵者趣，公交自行程序员.                      //
 * //                 别人笑我忒疯癫，我笑自己命太贱;                      //
 * //                 不见满街漂亮妹，哪个归得程序员?                      //
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
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void createTest() throws IOException {

        //写法比RestHighLevelClient更加简洁
        CreateIndexResponse indexResponse = client.indices().create(c -> c.index("user"));
    }

    /**
     * 判断索引是否存在
     *
     * @throws IOException
     */
    @Test
    public void existsTest() throws IOException {
        BooleanResponse booleanResponse = client.indices().exists(e -> e.index("user"));
        System.out.println(booleanResponse.value());
    }

    /**
     * 查询索引
     *
     * @throws IOException
     */
    @Test
    public void queryTest() throws IOException {
        GetIndexResponse getIndexResponse = client.indices().get(i -> i.index("user"));
        System.out.println(getIndexResponse.result());
    }

    /**
     * 删除索引
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

                //设置id
                .id("1")

                //传入user对象
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
        //遍历添加到bulk中
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
                //查询name字段包含hello的document(不使用分词器精确查找)
                .query(q -> q
                        .term(t -> t
                                .field("name")
                                .value(v -> v.stringValue("lin"))
                        ))
                //分页查询，从第0页开始查询3个document
                .from(0)
                .size(3)
                //按age降序排序
                .sort(f->f.field(o->o.field("age").order(SortOrder.Desc))),User.class
        );
        long value = search.hits().total().value();
        System.out.println("total:"+value);
        for (Hit<User> hit : search.hits().hits()) {
            System.out.println(hit.source());
        }
    }

}
