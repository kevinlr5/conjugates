import {createReducer} from './util';

const INITIAL_STATE: ArticleState = {
    analyzeArticleResponse: null,
};

export default createReducer(INITIAL_STATE, {
    CLEAR_ARTICLE: () => ({
        analyzeArticleResponse: null,
    }),
    SET_ARTICLE_ANALYSIS: (state, {analyzeArticleResponse}) => ({analyzeArticleResponse}),
});
