import json
import os
from html.parser import HTMLParser
import requests
import ffmpeg  # pip install ffmpeg-python

# 视频地址
videoUrl = "https://www.bilibili.com/video/BV1BV411d7CP/"

# 指定ffmpeg的位置 `windows电脑下是ffmpeg.exe`
ffmpegPath = '/Users/xiuming/Desktop/test/ffmpeg/bin/ffmpeg'


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


def mergeFiles(videoFilePath, audioFilePath, outFilePath):
    """合并音视频"""
    print("开始合并音视频")
    videoFile = ffmpeg.input(videoFilePath)
    audioFile = ffmpeg.input(audioFilePath)
    stream = ffmpeg.output(videoFile, audioFile, outFilePath, vcodec='copy', acodec='copy')
    ffmpeg.run(stream, cmd=ffmpegPath)
    print("合并音视频完成")



if __name__ == '__main__':
    parser = BiliHTMLParser()
    res = requests.get(videoUrl)
    parser.feed(res.text)
    getVideo(parser.videoInfo, parser.videoName)
