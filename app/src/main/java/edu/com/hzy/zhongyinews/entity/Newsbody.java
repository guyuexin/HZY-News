package edu.com.hzy.zhongyinews.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class Newsbody implements Serializable{
    public List<App> app;
    public String articleTags;
    public List<BoboList> boboList;
    public String body;
    public String digest;
    public String dkeys;
    public String docid;
    public boolean hasNext;
    public List<HuaTi> huati;
    public List<Img> img;
    public List<Link> link;
    public boolean picnews;
    public String ptime;
    public List<Relative_sys> relative_sys;
    public String replyBoard;
    public int replyCount;
    public String shareLink;
    public String source;
    public Sourceinfo sourceinfo;
    public String template;
    public int threadAgainst;
    public int threadVote;
    public String tid;
    public String title;
    public List<TopicList> topiclist;
    public List<TopicListNews> topiclist_news;
    public List<User> users;
    public String voicecomment;
    public List<Vote> votes;
    public List<YdBaike> ydbaike;

    public Newsbody(List<App> app, String articleTags, List<BoboList> boboList, String body, String digest, String dkeys, String docid, boolean hasNext, List<HuaTi> huati, List<Img> img, List<Link> link, boolean picnews, String ptime, List<Relative_sys> relative_sys, String replyBoard, int replyCount, String shareLink, String source, Sourceinfo sourceinfo, String template, int threadAgainst, int threadVote, String tid, String title, List<TopicList> topiclist, List<TopicListNews> topiclist_news, List<User> users, String voicecomment, List<Vote> votes, List<YdBaike> ydbaike) {
        this.app = app;
        this.articleTags = articleTags;
        this.boboList = boboList;
        this.body = body;
        this.digest = digest;
        this.dkeys = dkeys;
        this.docid = docid;
        this.hasNext = hasNext;
        this.huati = huati;
        this.img = img;
        this.link = link;
        this.picnews = picnews;
        this.ptime = ptime;
        this.relative_sys = relative_sys;
        this.replyBoard = replyBoard;
        this.replyCount = replyCount;
        this.shareLink = shareLink;
        this.source = source;
        this.sourceinfo = sourceinfo;
        this.template = template;
        this.threadAgainst = threadAgainst;
        this.threadVote = threadVote;
        this.tid = tid;
        this.title = title;
        this.topiclist = topiclist;
        this.topiclist_news = topiclist_news;
        this.users = users;
        this.voicecomment = voicecomment;
        this.votes = votes;
        this.ydbaike = ydbaike;
    }

    public static class YdBaike {
    }

    public static class Relative_sys {
        public String id, title, source, imgsrc, docId, type, ptime, href;
    }

    public static class App {

    }

    public static class HuaTi {
        public String topicId;
        public String topicName;
    }

    public static class BoboList {
    }

    public static class TopicList {
        public String hasCover, alias, subnum, tname, ename, tid, cid;
    }

    public static class TopicListNews {
        public String hasCover, alias, subnum, tname, ename, tid, cid;
    }

    public static class Img {
        public String ref;
        public String pixel;
        public String alt;
        public String src;
    }

    public static class User {

    }

    public static class Link {

    }

    public static class Vote {

    }


    public static class Sourceinfo {
        public String alias, ename, tname, tid;
    }


}
