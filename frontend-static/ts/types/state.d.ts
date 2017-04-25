interface ArticleState {
	analyzeArticleResponse: AnalyzeArticleResponse | null;
}

interface AppState {
    article: ArticleState;
}