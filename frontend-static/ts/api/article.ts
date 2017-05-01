import {post} from './_http';

export default {

    analyzeArticle(request) {
        return post(`${__API_URL__}/api/article/analyze`, request);
    },

} as ArticleApi;
