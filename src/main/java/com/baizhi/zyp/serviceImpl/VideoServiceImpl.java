package com.baizhi.zyp.serviceImpl;

import com.baizhi.zyp.annotation.AddCache;
import com.baizhi.zyp.annotation.DelCache;
import com.baizhi.zyp.dao.Videodao;
import com.baizhi.zyp.entity.User;
import com.baizhi.zyp.entity.UserExample;
import com.baizhi.zyp.entity.Video;
import com.baizhi.zyp.entity.VideoExample;
import com.baizhi.zyp.po.VideoPo;
import com.baizhi.zyp.repository.VideoRepository;
import com.baizhi.zyp.service.VideoService;
import com.baizhi.zyp.util.AliyunUtil;
import com.baizhi.zyp.util.InterceptVideoCoveUtil;
import com.baizhi.zyp.util.UUIDUtil;
import com.baizhi.zyp.vo.VideoVo;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/1
 */
@Service
@Transactional
public class VideoServiceImpl implements VideoService {
  @Resource
    Videodao videodao;
  @Resource
    HttpSession session;
  @Resource
    ElasticsearchTemplate elasticsearchTemplate;
  @Resource
    VideoRepository videoRepository;
  @DelCache
    @Override
    public void uploadVideo(MultipartFile path , String id , HttpServletRequest request) {

        /*
        * 视频上传阿里云
        * */
        //获取文件名
        String filename = path.getOriginalFilename();
        String newName= System.currentTimeMillis()+filename;
        String objectName="video/"+newName;
        String dbName="http://yxue-zhao.oss-cn-beijing.aliyuncs.com/video/"+newName;
        AliyunUtil.uploadFileByte(path,objectName);

        /*
        * 截取视频第一帧
        * */
        /**
         * 获取指定视频的帧并保存为图片至指定目录
         * @param filePath 视频存放的地址
         * @param targerFilePath 截图存放的地址
         * @param targetFileName 截图保存的文件名称
         * @return
         * @throws Exception
         */


        String realPath = session.getServletContext().getRealPath("upload/cover");
        //创建一个新文件在realPath路径下
        File file = new File(realPath);
       //如果这个目录下的文件夹不存在
        if (!file.exists()){
          //创建文件夹
            file.mkdirs();
        }
        String[] split = newName.split("\\.");
    /*
    * 1585728783971dc4a
        mp4
    * */

        String interceptname=split[0];
        String targetFileName=interceptname+".jpg";

        String coverPath=realPath+"\\"+targetFileName;
        try {
            InterceptVideoCoveUtil.executeCodecs(dbName,realPath,interceptname);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        *
        * 将图片上传阿里云
        * */
        AliyunUtil.uploadFile("photo/"+targetFileName,coverPath);

        /*
        * 上传完阿里云删除本地文件
        *
        * */
        //完整路径coverPath   到.jpg
        File file1 = new File(coverPath);
        //如果file1是个文件并且file1存在
        if (file1.isFile()&&file1.exists()){
            boolean delete = file1.delete();
            System.out.println("删除："+delete);
        }
        String coverName ="http://yxue-zhao.oss-cn-beijing.aliyuncs.com/photo/"+targetFileName;

        /*
        * 修改文件名
        * */
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo(id);
        Video video = new Video();
        video.setCover(coverName);
        video.setPath(dbName);
        videodao.updateByExampleSelective(video,videoExample);
      //添加索引
      Video video1 = new Video();
      video1.setId(id);
      Video video2 = videodao.selectOne(video1);
      videoRepository.save(video2);
    }
    @AddCache
    @Override
    public HashMap<String, Object> selectVideoByPage(Integer page, Integer rows) {
      VideoExample videoExample =new VideoExample();

        Integer start= (page-1)*rows;
        RowBounds rowBounds = new RowBounds(start,rows);
        //分页数据
        List<Video> videos = videodao.selectByExampleAndRowBounds(videoExample, rowBounds);
        //返回总条数
        Integer records = videodao.selectCount(new Video());

        Integer total =records%rows==0?records/rows:records/rows+1;
        HashMap<String, Object> map = new HashMap<>(16);


        map.put("page",page);
        map.put("rows",videos);
        map.put("records",records);
        map.put("total",total);

        return map;
    }
@DelCache
    @Override
    public String addVideo(Video video) {
        String uuid = UUIDUtil.getUUID();
        video.setCategoryId("1");
        video.setGroupId("1");
        video.setUserId("1");
        video.setId(uuid);
        video.setPublishDate(new Date());
        videodao.insert(video);

        return uuid;
    }
@DelCache
    @Override
    public void deleteVideoById(String id) {
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo(id);
        Video video = videodao.selectOneByExample(videoExample);

        //删除视频
        String path = video.getPath();
        String[] pathsplit = path.split("/");
        //拆分拼接video/1585732589500804d4.mp4
        String pathName =pathsplit[pathsplit.length - 2]+"/"+pathsplit[pathsplit.length - 1];
        System.out.println("视频删除路径"+pathName);
        AliyunUtil.deleteFile(pathName);
        /**
         * 拆分成这样
         *http:
         * 空
         *yxue-zhao.oss-cn-beijing.aliyuncs.com
         *video
         *1585732589500804d4.mp4
        */

        //删除封面
        String cover = video.getCover();
        String[] coversplit = cover.split("/");
        String videoName =coversplit[coversplit.length - 2]+"/"+coversplit[coversplit.length - 1];
        System.out.println("封面删除路径"+videoName);
        AliyunUtil.deleteFile(videoName);

        //删除视频
        videodao.deleteByExample(videoExample);
    Video video1 = new Video();
    video1.setId(id);
    videoRepository.delete(video1);

    }
@DelCache
    @Override
    public String updateVideo(Video video) {
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo(video.getId());
        videodao.updateByExampleSelective(video,videoExample);
        return video.getId();
    }
@AddCache
    @Override
    public List<VideoVo> queryByReleaseTime() {
        List<VideoPo> videoPos = videodao.queryByReleaseTime();
        ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();
        for (VideoPo videoPo : videoPos) {
            String id = videoPo.getId();
            //根据id查询视频点赞数
             Integer likeCount=18;
            //给vo赋值
            VideoVo videoVo = new VideoVo(videoPo.getId(), videoPo.getVTitle(), videoPo.getVCover(), videoPo.getVPath(), videoPo.getVPublishDate(), videoPo.getVBrief(), likeCount, videoPo.getCateName(), videoPo.getHeadImg());
            videoVoArrayList.add(videoVo);
        }
        return videoVoArrayList;
    }

    @Override
    public List<Video> querySearch(String content) {
        //查询条件

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                //指定索引类型
                .withIndices("yingxv")
                //指定类型
                .withTypes("video")
                //搜寻条件
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))

                .build();

        //查询
        List<Video> videos = elasticsearchTemplate.queryForList(nativeSearchQuery, Video.class);
      //  videos.forEach(video -> System.out.println(video));
        return videos;
    }
    @Override
    public List<Video> querySearchs(String content) {

        //处理高亮
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>");
        field.postTags("</span>");

        //查询条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                //指定索引类型
                .withIndices("yingxv")
                //指定类型
                .withTypes("video")
                //搜索条件
                .withQuery(QueryBuilders.queryStringQuery(content)
                 .field("title").field("brief"))
               //高亮的处理

                .withHighlightFields(field)

               // .withFields("title","brief")
                //搜寻条件
                .build();

        //高亮查询
        AggregatedPage<Video> videos = elasticsearchTemplate.queryForPage(nativeSearchQuery, Video.class, new SearchResultMapper() {

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

                    String id = sourceAsMap.get("id")!=null?sourceAsMap.containsKey("id")?sourceAsMap.get("id").toString():null:null;



                    String title =sourceAsMap.get("id")!=null?sourceAsMap.containsKey("title")? sourceAsMap.get("title").toString():null:null;
                    String brief = sourceAsMap.get("title")!=null?sourceAsMap.containsKey("brief")?sourceAsMap.get("brief").toString():null:null;
                    String path = sourceAsMap.get("path")!=null?sourceAsMap.containsKey("path")?sourceAsMap.get("path").toString():null:null;
                    String cover = sourceAsMap.get("cover")!=null?sourceAsMap.containsKey("cover")?sourceAsMap.get("cover").toString():null:null;
                    //处理日期操作
                    Date publishDate = null;
                    if (sourceAsMap.get("publishDate")==null){
                        publishDate=null;
                    }else {
                    if (!sourceAsMap.containsKey("publishDate")){
                        publishDate=null;
                    }else {
                        String publishDateStr = sourceAsMap.get("publishDate").toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            publishDate = simpleDateFormat.parse(publishDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    }
                    String categoryId = sourceAsMap.get("categoryId")!=null?sourceAsMap.containsKey("categoryId")?sourceAsMap.get("categoryId").toString():null:null;
                    String groupId =sourceAsMap.get("groupId")!=null? sourceAsMap.containsKey("groupId")?sourceAsMap.get("groupId").toString():null:null;
                    String userId = sourceAsMap.get("userId")!=null?sourceAsMap.containsKey("userId")?sourceAsMap.get("userId").toString():null:null;

                    Video video = new Video(id, title, brief, path, cover, publishDate, categoryId, groupId, userId);
                    //处理高亮数据
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (brief!=null){
                        if (highlightFields.get("brief")!=null){
                            String briefs = highlightFields.get("brief").fragments()[0].toString();
                            video.setBrief(briefs);
                        }
                    }

                    if (title!=null){
                        if (highlightFields.get("title")!=null){
                            String titles = highlightFields.get("title").fragments()[0].toString();
                            video.setTitle(titles);
                        }
                    }

                    //整合数据


                    list.add(video);

                }

                //强转 返回
                return new AggregatedPageImpl<T>((List<T>) list);
            }
        });


        return videos.getContent();
    }
}
