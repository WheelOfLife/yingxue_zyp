package com.baizhi.zyp;

import com.baizhi.zyp.entity.Emp;
import com.baizhi.zyp.entity.Video;
import com.baizhi.zyp.repository.EmpRepository;
import com.baizhi.zyp.repository.VideoRepository;
import com.baizhi.zyp.service.VideoService;
import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {
    @Resource
    EmpRepository empRepository;
    @Resource
    VideoRepository videoRepository;
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    VideoService videoService;
    @Test
    public void save(){
        empRepository.save(new Emp("3","真的石头",35,new Date()));
        empRepository.save(new Emp("4","实列石头",18,new Date()));
    }
    @Test
    public void testquery() {
        Iterable<Emp> all = empRepository.findAll();
        for (Emp emp : all) {
            System.out.println(emp);
        }
    }
    @Test
    public void testqueryone() {
        Optional<Emp> byId = empRepository.findById("1");
        System.out.println(byId);
    }
    @Test
    public void del(){
        empRepository.deleteById("2");
    }
    @Test
    public void querySort(){
        Iterable<Emp> age = empRepository.findAll(Sort.by(Sort.Order.asc("age")));
        for (Emp emp : age) {
            System.out.println(emp);
        }
    }
@Test
    public void queryPage(){
        Iterable<Emp> age = empRepository.findAll(PageRequest.of(0,2));
        for (Emp emp : age) {
            System.out.println(emp);
        }
    }
    @Test
    public void queryByName(){
        List<Emp> 小石头 = empRepository.findByName("石头");
        for (Emp emp : 小石头) {
            System.out.println(emp);
        }
    }
    @Test
    public void querySearch(){
        //查询条件
        String content ="视频";
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxv")//指定索引类型
                .withTypes("video") //指定类型
                .withQuery(QueryBuilders.queryStringQuery(content)
                        .field("title").field("brief"))

                //搜寻条件
                .build();

        //查询
        List<Video> videos = elasticsearchTemplate.queryForList(nativeSearchQuery, Video.class);
        videos.forEach(video -> System.out.println(video));
    }
    @Test
    public void sevevideo(){
        HashMap<String, Object> map = videoService.selectVideoByPage(1, 100);

        List<Video> rows = (List<Video>) map.get("rows");
        for (Video row : rows) {
            row.setGroupId("1");
            row.setCategoryId("1");
            row.setUserId("1");
            System.out.println(row);
            videoRepository.save(row);
        }
       // videoRepository.save(new Video("2","这是一个好最好的视频","我是一个超级视频","1.mp4","i.jpg",new Date(),"1","1","1"));
    }
    @Test
    public void querySearchs(){

        String content ="感觉";
        //处理高亮
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>");
        field.postTags("</span>");

        //查询条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingxv")//指定索引类型
                .withTypes("video") //指定类型
                .withQuery(QueryBuilders.queryStringQuery(content)
                .field("title").field("brief"))
                .withHighlightFields(field)
                //搜寻条件
                .build();

        //高亮查询
        elasticsearchTemplate.queryForPage(nativeSearchQuery, Video.class, new SearchResultMapper() {

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                ArrayList<Video> list = new ArrayList<>();

                //获取查询结果
                SearchHit[] hits = searchResponse.getHits().getHits();
                for (SearchHit hit : hits) {
                    //原始数据
                    //System.out.println(hit.getSourceAsMap());
                    //高亮数据
                   // System.out.println(hit.getHighlightFields());
                    /*
                    *   {brief=我是一个超级视频, cover=i.jpg, path=1.mp4, groupId=1, publishDate=1586735652104, id=2, title=这是一个好最好的视频, userId=1, categoryId=1}

                        * {brief=[brief], fragments[[我是一个超级<span style='color:red'>视频</span>]], title=[title], fragments[[这是一个好最好的<span style='color:red'>视频</span>]]}
                        {brief=我是一个好视频, cover=i.jpg, path=1.mp4, groupId=1, publishDate=1586735291256, id=1, title=这是一个好视频, userId=1, categoryId=1}

                        {brief=[brief], fragments[[我是一个好<span style='color:red'>视频</span>]], title=[title], fragments[[这是一个好<span style='color:red'>视频</span>]]}

                    * */
                    //处理普通数据

                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    String id = sourceAsMap.get("id").toString();
                    String title = sourceAsMap.get("title").toString();
                    String brief = sourceAsMap.get("brief").toString();
                    String path = sourceAsMap.get("path").toString();
                    String cover = sourceAsMap.get("cover").toString();
                    //处理日期操作
                    String publishDateStr =  sourceAsMap.get("publishDate").toString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date publishDate = null;
                    try {
                        publishDate = simpleDateFormat.parse(publishDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String categoryId = sourceAsMap.get("categoryId").toString();
                    String groupId = sourceAsMap.get("groupId").toString();
                    String userId = sourceAsMap.get("userId").toString();

                    Video video = new Video(id,title,brief,path,cover,publishDate,categoryId,groupId,userId);
                  //处理高亮数据
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (highlightFields.get("brief")!=null){
                        String briefs = highlightFields.get("brief").fragments()[0].toString();
                        video.setBrief(briefs);
                    }
                    if (highlightFields.get("title")!=null){
                        String titles = highlightFields.get("title").fragments()[0].toString();
                        video.setTitle(titles);
                    }

                    //整合数据

                    System.out.println("video = " + video);
                    list.add(video);
                }

                return null;
            }
        });

    }
}
