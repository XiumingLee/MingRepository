package cn.xiuminglee.bili;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xiuming Lee
 * @description
 */
public class BiliVideoDownload {
    /** 视频地址 */
    private static String VIDEO_URL = "https://www.bilibili.com/video/BV1BV411d7CP/";
    /** FFMPEG位置 `windows电脑下是ffmpeg.exe`*/
    private static String FFMPEG_PATH = "/Users/xiuming/Desktop/test/ffmpeg/bin/ffmpeg";

    private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36";

    private static VideoInfo VIDEO_INFO = new VideoInfo();
    private static String SAVE_PATH;

    public static void main(String[] args) {
        htmlParser();
    }



    /** 解析HTML获取相关信息 */
    private static void htmlParser(){
        HttpResponse res = HttpRequest.get(VIDEO_URL).timeout(2000).execute();
        String html = res.body();
        Document document = Jsoup.parse(html);
        Element title = document.getElementsByTag("title").first();
        // 视频名称
        VIDEO_INFO.videoName = title.text();
        // 截取视频信息
        Pattern pattern = Pattern.compile("(?<=<script>window.__playinfo__=).*?(?=</script>)");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            VIDEO_INFO.videoInfo = new JSONObject(matcher.group());
        } else {
            System.err.println("未匹配到视频信息，退出程序！");
            return;
        }
        getVideoInfo();
    }

    /** 解析视频和音频的具体信息 */
    private static void getVideoInfo(){
        // 获取视频的基本信息
        JSONObject videoInfo = VIDEO_INFO.videoInfo;
        JSONArray videoInfoArr = videoInfo.getJSONObject("data").getJSONObject("dash").getJSONArray("video");
        VIDEO_INFO.videoBaseUrl = videoInfoArr.getJSONObject(0).getStr("baseUrl");
        VIDEO_INFO.videoBaseRange = videoInfoArr.getJSONObject(0).getJSONObject("SegmentBase").getStr("Initialization");
        HttpResponse videoRes = HttpRequest.get(VIDEO_INFO.videoBaseUrl)
                .header("Referer", VIDEO_URL)
                .header("Range", "bytes=" + VIDEO_INFO.videoBaseRange)
                .header("User-Agent", USER_AGENT)
                .timeout(2000)
                .execute();
        VIDEO_INFO.videoSize = videoRes.header("Content-Range").split("/")[1];

        // 获取音频基本信息
        JSONArray audioInfoArr = videoInfo.getJSONObject("data").getJSONObject("dash").getJSONArray("audio");
        VIDEO_INFO.audioBaseUrl = audioInfoArr.getJSONObject(0).getStr("baseUrl");
        VIDEO_INFO.audioBaseRange = audioInfoArr.getJSONObject(0).getJSONObject("SegmentBase").getStr("Initialization");
        HttpResponse audioRes = HttpRequest.get(VIDEO_INFO.audioBaseUrl)
                .header("Referer", VIDEO_URL)
                .header("Range", "bytes=" + VIDEO_INFO.audioBaseRange)
                .header("User-Agent", USER_AGENT)
                .timeout(2000)
                .execute();
        VIDEO_INFO.audioSize = audioRes.header("Content-Range").split("/")[1];

        downloadFile();
    }

    /** 下载音视频 */
    private static void downloadFile(){
        // 保存音视频的位置
        SAVE_PATH = "." + File.separator + VIDEO_INFO.videoName;
        File fileDir = new File(SAVE_PATH);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }

        // 下载视频
        File videoFile = new File(SAVE_PATH + File.separator + VIDEO_INFO.videoName + "_video.mp4");
        if (!videoFile.exists()){
            System.out.println("--------------开始下载视频文件--------------");
            HttpResponse videoRes = HttpRequest.get(VIDEO_INFO.videoBaseUrl)
                    .header("Referer", VIDEO_URL)
                    .header("Range", "bytes=0-" + VIDEO_INFO.videoSize)
                    .header("User-Agent", USER_AGENT)
                    .execute();
            videoRes.writeBody(videoFile);
            System.out.println("--------------视频文件下载完成--------------");
        }


        // 下载音频
        File audioFile = new File(SAVE_PATH + File.separator + VIDEO_INFO.videoName + "_audio.mp4");
        if (!audioFile.exists()){
            System.out.println("--------------开始下载音频文件--------------");
            HttpResponse audioRes = HttpRequest.get(VIDEO_INFO.audioBaseUrl)
                    .header("Referer", VIDEO_URL)
                    .header("Range", "bytes=0-" + VIDEO_INFO.audioSize)
                    .header("User-Agent", USER_AGENT)
                    .execute();
            audioRes.writeBody(audioFile);
            System.out.println("--------------音频文件下载完成--------------");
        }

        mergeFiles(videoFile,audioFile);

    }


    private static void mergeFiles(File videoFile,File audioFile){
        System.out.println("--------------开始合并音视频--------------");
        String outFile = SAVE_PATH + File.separator + VIDEO_INFO.videoName + ".mp4";
        List<String> commend = new ArrayList<>();
        commend.add(FFMPEG_PATH);
        commend.add("-i");
        commend.add(videoFile.getAbsolutePath());
        commend.add("-i");
        commend.add(audioFile.getAbsolutePath());
        commend.add("-vcodec");
        commend.add("copy");
        commend.add("-acodec");
        commend.add("copy");
        commend.add(outFile);

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commend);
        try {
            builder.inheritIO().start().waitFor();
            System.out.println("--------------音视频合并完成--------------");
        } catch (InterruptedException | IOException e) {
            System.err.println("音视频合并失败！");
            e.printStackTrace();
        }

    }
}

class VideoInfo{  // 真实项目中不推荐直接使用`public`哦😯
    public String videoName;
    public JSONObject videoInfo;
    public String videoBaseUrl;
    public String audioBaseUrl;
    public String videoBaseRange;
    public String audioBaseRange;
    public String videoSize;
    public String audioSize;
}
