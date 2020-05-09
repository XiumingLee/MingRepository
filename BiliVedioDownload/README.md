## 1、B站视频真实地址分析

> 我一直觉得编程语言只是一种工具，重要的是思想🐶。下面先来分析下B站视频的真实地址。

### 1.1 获取视频的信息数据

使用PC通过浏览器随便打开一个B站的视频，右键`检查`或者是按`F12`，查看网页源代码。我们会发现有一个`script`标签内的内容是这样的。

![](https://img-blog.csdnimg.cn/20200509195913920.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1hpdW1pbmdMZWU=,size_16,color_FFFFFF,t_70)

> 嗯，仿佛这就是视频的信息了。下面我们将其复制出来，格式化一下。

```js
window.__playinfo__ = {
    "data": {
        "accept_format": "flv720,flv480,mp4",
        "accept_description": ["高清 720P", "清晰 480P", "流畅 360P"],
        "accept_quality": [64, 32, 16],
        "dash": {
            "video": [{
                "id": 64,
                "baseUrl": "http://cn-sdbz-cu-v-05.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30064.m4s?expires=1589032200&platform=pc&ssig=7TagzkxicmXQCX-eJG1rWw&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=80000000",
                "base_url": "http://cn-sdbz-cu-v-05.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30064.m4s?expires=1589032200&platform=pc&ssig=7TagzkxicmXQCX-eJG1rWw&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=80000000",
                "backupUrl": ["http://cn-sdyt-cu-v-11.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30064.m4s?expires=1589032200&platform=pc&ssig=7TagzkxicmXQCX-eJG1rWw&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=40000000", "http://cn-hbcd2-cu-v-07.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30064.m4s?expires=1589032200&platform=pc&ssig=7TagzkxicmXQCX-eJG1rWw&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=40000000"],
                "backup_url": ["http://cn-sdyt-cu-v-11.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30064.m4s?expires=1589032200&platform=pc&ssig=7TagzkxicmXQCX-eJG1rWw&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=40000000", "http://cn-hbcd2-cu-v-07.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30064.m4s?expires=1589032200&platform=pc&ssig=7TagzkxicmXQCX-eJG1rWw&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=40000000"],
                "bandwidth": 1883922,
                "mimeType": "video/mp4",
                "mime_type": "video/mp4",
                "width": 720,
                "height": 1280,
                "SegmentBase": {"Initialization": "0-974", "indexRange": "975-1162"},
                "segment_base": {"initialization": "0-974", "index_range": "975-1162"},
                "codecid": 7
            },], 
            "audio": [{
                "id": 30280,
                "baseUrl": "http://cn-sdyt-cu-v-05.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30280.m4s?expires=1589032200&platform=pc&ssig=ud9zkd5aAUp7mB4yPjI_LA&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=80000000",
                "base_url": "http://cn-sdyt-cu-v-05.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30280.m4s?expires=1589032200&platform=pc&ssig=ud9zkd5aAUp7mB4yPjI_LA&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=80000000",
                "backupUrl": ["http://cn-hbcd2-cu-v-14.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30280.m4s?expires=1589032200&platform=pc&ssig=ud9zkd5aAUp7mB4yPjI_LA&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=40000000", "http://cn-sdjn2-cu-v-05.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30280.m4s?expires=1589032200&platform=pc&ssig=ud9zkd5aAUp7mB4yPjI_LA&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=40000000"],
                "backup_url": ["http://cn-hbcd2-cu-v-14.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30280.m4s?expires=1589032200&platform=pc&ssig=ud9zkd5aAUp7mB4yPjI_LA&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=40000000", "http://cn-sdjn2-cu-v-05.bilivideo.com/upgcxcode/18/80/187918018/187918018-1-30280.m4s?expires=1589032200&platform=pc&ssig=ud9zkd5aAUp7mB4yPjI_LA&oi=1894210281&trid=4b3d732f515544e49c843d5f2c87f64bu&nfc=1&nfb=maPYqpoel5MI3qOUX6YpRA==&mid=388810686&logo=40000000"],
                "bandwidth": 319474,
                "mimeType": "audio/mp4",
                "mime_type": "audio/mp4",
                "SegmentBase": {"Initialization": "0-919", "indexRange": "920-1107"},
                "segment_base": {"initialization": "0-919", "index_range": "920-1107"},
            },]
        }
    }, "session": "996ecc0413599104d175e5c254e70fb7", "videoFrame": {}
}
```

> 我删除了一些没有的信息，通过上面的信息我们可以得到以下几点信息：
>
> 1. B站的视频是音视频分离的。
> 2. 我们可以从js中获取真实音视频地址。
> 3. 提供了`"高清 720P", "清晰 480P", "流畅 360P"`方式供我们选择。不要问我为什么没有`1080P`的，俺也不知道😢。

**下面我们再来看一下B站自己发送请求的信息。**

![](https://img-blog.csdnimg.cn/20200509201223561.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1hpdW1pbmdMZWU=,size_16,color_FFFFFF,t_70)

> 我们发现每次请求时都携带了，此次请求文件的字节位置信息。

### 1.2 抓取B站视频的思路

> 1. 请求想要下载视频的地址，获取页面html。
> 2. 从页面中解析出视频的基本信息，音视频的url地址等信息。
> 3. 下载音视频文件，发送请求时带上请求的范围(`range`)。(**`注：`本文的实现中，没有使用多线程，直接请求的整个文件**)
> 4. 将下载完成的音视频文件合并成完成的视频文件。

### 1.3 用到的第三方库和软件

1. ##### 第三方软件

> - **`ffmpeg`**：用于合并音视频文件。官方网址：http://ffmpeg.org/。
>   - `ffmpeg`：是处理音视频的利器，感兴趣可以搜索相关资料了解下。本文只要你下载下来，将`ffmpeg`位置写到代码变量里就可以了。

2. ##### Python

> - `requests`：用于发送Http请求
> - `ffmpeg-python`：方便操作`ffmpeg`
> - `HTMLParser`：python自带的HTML解析工具

3. ##### Java

> - `hutool`：一个国人开源的Java工具包。强烈推荐。
>
> - `jsoup`：Java解析HTML的利器

## 2、代码实现

> **`注：`本文中代码下载音视频均采用的单线程的方式，如果使用多线程，一定要计算好每个请求的请求范围，以及下载完成后，合并文件时的顺序。**

### 2.1 Python的实现

#### 解析html

```python
class BiliHTMLParser(HTMLParser):
    """
    继承自HTMLParser。用于解析html
    """
    def __init__(self):
        super().__init__()
        self.isTitle = 0
        self.videoName = ""  # 视频名称
        self.videoInfo = {}  # 视频信息

    def handle_starttag(self, tag, attrs):
        if tag != 'title':
            return
        self.isTitle += 1

    def handle_endtag(self, tag):
        if tag == 'title' and self.isTitle:
            self.isTitle -= 1

    def handle_data(self, data):
        """
        获取当前页面的视频信息
        :param data: tag中的数据
        :return:
        """
        if data and self.isTitle:  # 用于获取视频名称
            self.videoName = data

        if data.startswith('window.__playinfo__='):
            infoStr = data.split('window.__playinfo__=')[-1]  # 截取`window.__playinfo__=`之后的字符串
            self.videoInfo = json.loads(infoStr)  # 字符串转字典dict

```

#### 获取视频信息

```python
def getVideo(videoInfo, videoName):
    """
    :param videoInfo: 视频信息字典dict
    :param videoName: 视频名称
    :return:
    """
    # 获取视频的url和初始的大小范围
    videoBaseUrl = videoInfo['data']['dash']['video'][0]['baseUrl']
    videoBaseRange = videoInfo['data']['dash']['video'][0]['SegmentBase']['Initialization']

    # 获取音频的url和初始的大小范围
    audioBaseUrl = videoInfo['data']['dash']['audio'][0]['baseUrl']
    audioBaseRange = videoInfo['data']['dash']['audio'][0]['SegmentBase']['Initialization']
    # 文件下载
    videoSize = getVideoInfo(videoBaseUrl, videoBaseRange)
    videoFileName = downloadFile(videoBaseUrl, videoSize, "video", videoName)
    audioSize = getVideoInfo(audioBaseUrl, audioBaseRange)
    audioFileName = downloadFile(audioBaseUrl, audioSize, "audio", videoName)
    # 合并文件
    outFilePath = "./%s/%s.mp4" % (videoName, videoName)
    mergeFiles(videoFileName, audioFileName, outFilePath)


def getVideoInfo(baseUrl, range):
    """
    获取视频或音频文件的总大小
    :param baseUrl:
    :param range:
    :return:
    """
    headers = {
        'Referer': videoUrl,
        'Range': 'bytes=%s' % (range),
    }
    videoRes = requests.get(url=baseUrl, headers=headers)
    # 获取视频总大小
    headersInfo = videoRes.headers
    total = headersInfo['Content-Range'].split('/')[-1]
    print('资源的总字节数：%s' % total)
    return total

```

#### 下载音视频

```python
def downloadFile(url, totalSize, type, videoName):
    """
    下载资源
    :param url: 资源url
    :param totalSize: 资源总大小
    :param type: video/audio
    :param videoName: 视频名称
    :return:
    """
    headers = {
        'Referer': videoUrl,
        'Range': "bytes=%s-%s" % (str(0), str(totalSize))
    }
    fileDir = "./%s" % videoName
    if not os.path.exists(fileDir):
        os.mkdir(fileDir)

    fileName = "./%s/%s-%s.mp4" % (videoName, videoName, type)
    if not os.path.exists(fileName):
        res = requests.get(url=url, headers=headers, stream=True)
        print("开始下载：%s" % type)
        data = res.content
        with open(fileName, 'wb') as file_obj:
            file_obj.write(data)
    print("完成%s的下载" % type)
    return fileName
```

#### 合并音视频

```python
def mergeFiles(videoFilePath, audioFilePath, outFilePath):
    """合并音视频"""
    print("开始合并音视频")
    videoFile = ffmpeg.input(videoFilePath)
    audioFile = ffmpeg.input(audioFilePath)
    stream = ffmpeg.output(videoFile, audioFile, outFilePath, vcodec='copy', acodec='copy')
    ffmpeg.run(stream, cmd=ffmpegPath)
    print("合并音视频完成")
```

### 2.2 Java实现

#### 解析Html

```java
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

```

#### 获取视频信息

```java
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
```

#### 下载音视频

```java
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
```

#### 合并视频

```java
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
```

## 3、源码地址

[GitHub源码地址](https://github.com/XiumingLee/MingRepository/tree/master/BiliVedioDownload)


