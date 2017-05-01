import articleApi from '../api/article';
import {getAsyncActionCreator} from './_util';

function setAnalysis(analyzeArticleResponse: AnalyzeArticleResponse): SetArticleAnalysisAction {
    return {type: 'SET_ARTICLE_ANALYSIS', analyzeArticleResponse};
}

export function clearArticle(): ClearArticleAction {
    return {type: 'CLEAR_ARTICLE'};
}

export const analyzeArticle = getAsyncActionCreator(articleApi.analyzeArticle, setAnalysis);
