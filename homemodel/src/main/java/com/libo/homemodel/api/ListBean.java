package com.libo.homemodel.api;

import java.util.List;

/**
 * author : LiBo
 * date : 2023/5/16 15:53
 * description :
 */
public class ListBean {
    public Integer curPage;
    public List<Datas> datas;
    public Integer offset;
    public Boolean over;
    public Integer pageCount;
    public Integer size;
    public Integer total;
    public static class Datas {
        public Boolean adminAdd;
        public String apkLink;
        public Integer audit;
        public String author;
        public Boolean canEdit;
        public Integer chapterId;
        public String chapterName;
        public Boolean collect;
        public Integer courseId;
        public String desc;
        public String descMd;
        public String envelopePic;
        public Boolean fresh;
        public String host;
        public Integer id;
        public Boolean isAdminAdd;
        public String link;
        public String niceDate;
        public String niceShareDate;
        public String origin;
        public String prefix;
        public String projectLink;
        public Long publishTime;
        public Integer realSuperChapterId;
        public Boolean route;
        public Integer selfVisible;
        public Long shareDate;
        public String shareUser;
        public Integer superChapterId;
        public String superChapterName;
        public List<?> tags;
        public String title;
        public Integer type;
        public Integer userId;
        public Integer visible;
        public Integer zan;
    }
}
